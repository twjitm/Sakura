package com.fb.util;

import android.R.integer;

/**
建议描述
 * @author suggest
 *
 */
public class SuggestUtil {
public String getSuggsetByDb(int degree){
	if (degree<10) {
		return "婴儿可以安心的睡个午觉，小孩可以安心的学习，老人可以看看报纸";
	}if (degree>10&&degree<20) {
		return "听听音乐，舒适的环境下可以学习,可以适当的运动运动";

	}if (degree>20&&degree<30) {
		return "安安静静的看一会书，注意保持休息，防止眼睛疲劳";
	}if (degree>30&&degree<40) {
		return "公园里的环境真好，在这种环境下，忙于工作的人们应该享受这种环境，喝一杯咖啡，接触一天的疲劳";
	}if (degree>40&&degree<50) {
		return "小道里的环境还是不错的，类似这种环境，注意保持良好的心情，可能会打扰别人休息，注意说话的声音";
	}if (degree>50&&degree<60) {
		return "交流中我们应该是不是要注意一下声音的大小，这种环境可能会打扰别人休息，学习.这种环境下不能长时间待着";
	}if (degree>60&&degree<70) {
		return "吵闹的环境，不太适合婴儿，小孩子等弱小的人群，特别是带有孩子的妈妈，应该远离这种环境";
	}if (degree>70&&degree<80) {
		return "声音过大，长时间了可能会损坏人听了，大人小孩子都应该远离这种环境";
	}
	if (degree>80&&degree<90) {
		return "声音过于猛烈，人不适合到这种环境下面生活，远离噪声";
	}if (degree>90&&degree<100) {
		return "我滴天啦，这种环境还是远离吧，可能会让人失去听了能力";
	}
	if (degree>100) {
		return "人无法忍受的，这种环境一般不会出现在人们的生活环境中";
	}
	return "正在获取建议...";

};
}
