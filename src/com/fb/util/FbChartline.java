package com.fb.util;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;

public class FbChartline {

	private GraphicalView            mGraphicalView;
	private XYMultipleSeriesDataset  multipleSeriesDataset; // 数据集容器
	private XYMultipleSeriesRenderer multipleSeriesRenderer;// 渲染器容器
	        MultipleCategorySeries   dataset;
	        DefaultRenderer          renderer;
	private XYSeries                 mSeries;               // 单条曲线数据集
	private XYSeriesRenderer         mRenderer,mRenderer2;  // 单条曲线渲染器
	private Context                  context;

	
	public FbChartline(Context context) {
		this.context = context;
	}

	/**
	 * 获取图表
	 * 
	 * @return
	 */
	public GraphicalView getGraphicalView() {
		mGraphicalView = ChartFactory.getCubeLineChartView(context,multipleSeriesDataset, multipleSeriesRenderer, 0.3f);//折现图

		return mGraphicalView;
	}
	/**
	 * 获取数据集，及坐xy标的集合
	 * 
	 * @param curveTitle
	 */
	public void setXYMultipleSeriesDataset(String curveTitle) {
		multipleSeriesDataset = new XYMultipleSeriesDataset();
		mSeries = new XYSeries(curveTitle);
		
		multipleSeriesDataset.addSeries(mSeries);
	}
	/**
	 * 获取渲染器
	 * 
	 * @param maxX
	 *            x轴最大值
	 * @param maxY
	 *            y轴最大值
	 * @param chartTitle
	 *            曲线的标题
	 * @param xTitle
	 *            x轴标题
	 * @param yTitle
	 *            y轴标题
	 * @param axeColor
	 *            坐标轴颜色
	 * @param labelColor
	 *            标题颜色
	 * @param curveColor
	 *            曲线颜色
	 * @param gridColor
	 *            网格颜色
	 */
	public void setXYMultipleSeriesRenderer(double maxX, double maxY,
			String chartTitle, String xTitle, String yTitle, int axeColor,
			int labelColor, int curveColor, int gridColor) {
		multipleSeriesRenderer = new XYMultipleSeriesRenderer();//坐标轴
		if (chartTitle != null) {
			multipleSeriesRenderer.setChartTitle(chartTitle);
		}
		multipleSeriesRenderer.setXTitle(xTitle);
		multipleSeriesRenderer.setYTitle(yTitle);
		multipleSeriesRenderer.setRange(new double[]{0, maxX, 0, maxY });//xy轴的范围
		multipleSeriesRenderer.setLabelsColor(labelColor);
		multipleSeriesRenderer.setXLabels(10);//X刻度度量值
		multipleSeriesRenderer.setYLabels(10);//Y刻度度量值
		//multipleSeriesRenderer.setYAxisMin(10);起点
		multipleSeriesRenderer.setMargins( new int [] {20, 30, 15, 0}); // 图形 4 边距
		multipleSeriesRenderer.setXLabelsColor(Color.BLACK);//横坐标上面刻度值的颜色
		multipleSeriesRenderer.setYLabelsColor(0,Color.BLACK);//纵坐标上面刻度值的颜色
		multipleSeriesRenderer.setXLabelsAlign(Align.RIGHT);
		multipleSeriesRenderer.setYLabelsAlign(Align.RIGHT);
		multipleSeriesRenderer.setAxisTitleTextSize(20);
		multipleSeriesRenderer.setAxesColor(Color.BLACK);
		multipleSeriesRenderer.setChartTitleTextSize(20);
		multipleSeriesRenderer.setLabelsTextSize(20);
		multipleSeriesRenderer.setLegendTextSize(20);
		multipleSeriesRenderer.setPointSize(0f);//曲线描点尺寸
		multipleSeriesRenderer.setShowLegend(true);//是否显示视图
		//multipleSeriesRenderer.setMargins(new int[] { 20, 30, 15, 20 });
		//multipleSeriesRenderer.setShowGrid(true);
		multipleSeriesRenderer.setZoomEnabled(true, false);
		multipleSeriesRenderer.setAxesColor(axeColor);
		multipleSeriesRenderer.setGridColor(gridColor);
		multipleSeriesRenderer.setBackgroundColor(Color.WHITE);//背景色
		multipleSeriesRenderer.setMarginsColor(Color.WHITE);//边距背景色，默认背景色为黑色，这里修改为白色
		multipleSeriesRenderer.setBarWidth(5f);//柱状图的宽度
		mRenderer = new XYSeriesRenderer();
			mRenderer.setColor(curveColor);
		mRenderer.setPointStyle(PointStyle.X);//描点风格，可以为圆点，方形点等等
		multipleSeriesRenderer.addSeriesRenderer(mRenderer);//在坐标上描点
	}

	/**
	 * 根据新加的数据，更新曲线，只能运行在主线程
	 * 
	 * @param x
	 *            新加点的x坐标
	 * @param y
	 *            新加点的y坐标
	 */

//	public void updateChart(double x, double y) {
//		mSeries.add(x, y);
//		mGraphicalView.repaint();//此处也可以调用invalidate()
//	}

	/**
	 * 添加新的数据，多组，更新曲线，只能运行在主线程
	 * @param xList
	 * @param yList
	 */
	public void updateChart(List<Double> xList, List<Double> yList) {
		for (int i = 0; i < xList.size(); i++) {
			mSeries.add(xList.get(i), yList.get(i));
		}
		mGraphicalView.repaint();//此处也可以调用invalidate()
	}
	
	int[] xv = new int[100];
	int[] yv = new int[100];
	private int gCount;
	public void updateChart(int addX ,double addY){
        //移除数据集中旧的点集  
		multipleSeriesDataset.removeSeries(mSeries);  
		   //判断当前点集中到底有多少点，因为屏幕总共只能容纳100个，所以当点数超过100时，长度永远是100  
        int length = mSeries.getItemCount();  
        if (length > 100) {  
            length = 100;  
        }  
        gCount++;
        if (gCount < 100)
        {//100个点以内的，直接添加进去就行
        	mSeries.add(length+1, addY);
            Log.e("ddd", "gCount:"+gCount+" series.getItemCount:"+mSeries.getItemCount());
        }
        else
        {   //超过100，要去除第一个点，整体左移一个，在100点处添加新的点
            //将旧的点集中x和y的数值取出来放入backup中，并且将x的值减1，造成曲线向左平移的效果  
            for (int i = 0; i < length-1; i++) {  
                xv[i] = ((int) mSeries.getX(i+1))-1;  
                yv[i] = (int) mSeries.getY(i+1);  
            }  
            //点集先清空，为了做成新的点集而准备  
            mSeries.clear();  
            //将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中  
            //这里可以试验一下把顺序颠倒过来是什么效果，即先运行循环体，再添加新产生的点  
            for (int k = 0; k < length-1; k++) {  
            	mSeries.add(xv[k], yv[k]);  
            }  
            mSeries.add(100, addY);//在100点处添加新的点
        }
        //在数据集中添加新的点集  
        multipleSeriesDataset.addSeries(mSeries);  
          
        //视图更新，没有这一步，曲线不会呈现动态  
        //如果在非UI主线程中，需要调用postInvalidate()，具体参考api  
        mGraphicalView.invalidate();  
	};
}
