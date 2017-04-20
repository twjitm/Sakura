package com.fb.util;

public class BewriteUtil {
	/**
	 * 根据噪音大小获取噪音所处的环境
	 * @param degree
	 * @return
	 */
	public  String goBewrite(int degree) {
		if (degree<10) {
			return "安静的环境";
		}if (degree>10&&degree<20) {
			return "钟表的滴答环境";

		}if (degree>20&&degree<30) {
			return "在图书馆环境";
		}if (degree>30&&degree<40) {
			return "公园舒适环境";
		}if (degree>40&&degree<50) {
			return "静谧的小道环境";
		}if (degree>50&&degree<60) {
			return "正常的交流讨论环境";
		}if (degree>60&&degree<70) {
			return "繁忙的街道里的环境";
		}if (degree>70&&degree<80) {
			return "手机铃声响起的环境";
		}
		if (degree>80&&degree<90) {
			return "摇滚音乐的聚会厅环境";
		}if (degree>90&&degree<100) {
			return "刺耳的雷声环境";
		}
		if (degree>100) {
			return "人无法忍受的环境";
		}
		return "正在获取环境...";

	}
	public int getVip(Integer degree) {
		if (degree<10) {
			return 0;
		}if (degree>10&&degree<20) {
			return 1;

		}if (degree>20&&degree<30) {
			return 2;
		}if (degree>30&&degree<40) {
			return 3;
		}if (degree>40&&degree<50) {
			return 4;
		}if (degree>50&&degree<60) {
			return 5;
		}if (degree>60&&degree<70) {
			return 6;
		}if (degree>70&&degree<80) {
			return 7;
		}
		if (degree>80&&degree<90) {
			return 8;
		}if (degree>90&&degree<100) {
			return 9;
		}
		if (degree>100) {
			return 10;
		}
		return 0;
	}
}
