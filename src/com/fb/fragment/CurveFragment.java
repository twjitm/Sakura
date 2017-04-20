package com.fb.fragment;

import java.util.Timer;

import org.achartengine.GraphicalView;

import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.example.blaudios.R;
import com.fb.activity.MainActivity;
import com.fb.base.BaseFragment;
import com.fb.util.BewriteUtil;
import com.fb.util.FbChartline;
import com.fb.util.MyMediaRecorder;
import com.fb.util.SuggestUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * 
 * @author 曲线页面
 *
 */
public class CurveFragment extends BaseFragment implements OnClickListener{
	@ViewInject(R.id.left_temperature_curve)
	private LinearLayout left_temperature_curve;
	@ViewInject(R.id.title_textview)
	private TextView title_textview;
	@ViewInject(R.id.text_bewrite)
	private TextView text_bewrite;//环境描述
	@ViewInject(R.id.text_suggest)
	private TextView text_suggest;//对应环境下面的建议
	private GraphicalView  mView;
	private FbChartline mService;
	//private MyAudioRecord audioRecordDemo ;
	private MyMediaRecorder media;
	@ViewInject(R.id.lsschj)
	private TextView lsschj;
	@ViewInject(R.id.text_vip)//噪音等级
	private TextView text_vip;

	@ViewInject(R.id.text_decibel)
	private TextView text_decibel;
	@ViewInject(R.id.needle)
	private ImageView needleView;  //指针图片
	@ViewInject(R.id.bt_start)
	private Button    bt_start;

	private float     degree = 0.0f;  //记录指针旋转
	private boolean   is_start=true;
	@Override
	public void initData() {
		bt_start.setOnClickListener(this);
		//audioRecordDemo =new MyAudioRecord(handler);
		media=new MyMediaRecorder(handler);
	}

	@Override
	public View initView() {
		view=View.inflate(context, R.layout.fragment_main, null);
		ViewUtils.inject(this,view);
		title_textview.setText("噪音测试仪");
		bt_start.setText("开始测试");
		setChartLineView();
		return view;
	}
	@SuppressWarnings("deprecation")
	private void setChartLineView() {
		mService=new FbChartline(context);
		mService.setXYMultipleSeriesDataset("分贝图");
		mService.setXYMultipleSeriesRenderer(100, 100, "分贝图", "时间", "分贝数值",
				Color.BLACK, Color.BLACK, Color.RED, Color.BLACK);
		mView = mService.getGraphicalView();
		left_temperature_curve.addView(mView, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));		
	}
	private int t = 0;
	@SuppressLint("HandlerLeak")
	BewriteUtil bewriteUtil=new BewriteUtil();
	SuggestUtil suggestUtil=new SuggestUtil();
	
	Handler handler=new Handler(){

		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0X00:
				if ("-Infinity".equals( msg.obj.toString())) {
					degree=0f;
				}else {
					degree =(Float.parseFloat( msg.obj.toString()));//获取到的值
				}
				
				mService.updateChart(t, degree);
				t+=5;
				Log.i("传递过来的参数", msg.obj.toString());
				//degree =(Float)msg.obj;
				//t+=1;
				String subString="";
				//数据采集格式化
				if (degree>100) {
					subString=String.valueOf(degree).substring(0, 3);
				}
				if (degree<10) {
					subString=String.valueOf(degree).substring(0, 1);
					
				}if (degree>10&&degree<100) {
					subString=String.valueOf(degree).substring(0, 2);
				} 
				if (degree<0) {
					subString="0";
				}
				text_decibel.setText(subString+" dB");
				if (degree>60) {
					text_decibel.setTextColor(Color.RED);
					text_bewrite.setTextColor(Color.RED);
					text_suggest.setTextColor(Color.RED);
				}else {
					text_suggest.setTextColor(Color.BLACK);
					text_bewrite.setTextColor(Color.BLACK);
					text_decibel.setTextColor(Color.WHITE);
				}
              
				Integer integer=Integer.parseInt(subString);
				
				String bweString=bewriteUtil.goBewrite(integer);
				int vip=bewriteUtil.getVip(integer);
				text_vip.setText("噪音:"+vip+"等级");
				String suggString= suggestUtil.getSuggsetByDb(integer);
			text_suggest.setText(suggString);
				text_bewrite.setText(bweString);
				RotateAnimation animation = new RotateAnimation(degree, 
						degree, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setDuration(1000);
				animation.setFillAfter(true);
				needleView.startAnimation(animation);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public  void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_start:
			if (is_start) {
				bt_start.setText("停止测试");
				
				media.startRecord();
				is_start=false;
			}else {
				bt_start.setText("开始测试");
				media.stopRecord();
			is_start=true;
			}
			break;

		default: 
			break;
		}
	}
}
