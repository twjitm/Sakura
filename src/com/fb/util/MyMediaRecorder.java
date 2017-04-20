package com.fb.util;


import java.io.File;
import java.io.IOException;
 

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
 


public class MyMediaRecorder {
  private final String TAG = "MediaRecord";
  private MediaRecorder mMediaRecorder;
  public static final int MAX_LENGTH = 1000 * 60 * 10;// 最大录音时长1000*60*10;
  private String filePath;
  private Handler handler = new Handler();
  public MyMediaRecorder(Handler handler){
    this.filePath = "/dev/null";
    this.handler=handler;
  }
  public MyMediaRecorder(File file) {
    this.filePath = file.getAbsolutePath();
  }
  private long startTime;
  private long endTime;
  public void startRecord() {
    if (mMediaRecorder == null)
      mMediaRecorder = new MediaRecorder();
    try {
      mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
      mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
       mMediaRecorder.setOutputFile(filePath);
      mMediaRecorder.setMaxDuration(MAX_LENGTH);
      mMediaRecorder.prepare();
      mMediaRecorder.start();
      startTime = System.currentTimeMillis();
      updateMicStatus();
    } catch (IllegalStateException e) {
     
    } catch (IOException e) {
  
    }
  }
 
  /**
   * 停止录音
   *
   */
  public long stopRecord() {
    if (mMediaRecorder == null)
      return 0L;
    endTime = System.currentTimeMillis();
    Log.i("ACTION_END", "endTime" + endTime);
    mMediaRecorder.stop();
    mMediaRecorder.reset();
    mMediaRecorder.release();
    mMediaRecorder = null;
    Log.i("ACTION_LENGTH", "Time" + (endTime - startTime));
    return endTime - startTime;
  }
 

  private Runnable mUpdateMicStatusTimer = new Runnable() {
    public void run() {
      updateMicStatus();
    }
  };
 
  /**
   * 更新话筒状态
   *
   */
  private int BASE = 1;
  private int SPACE = 100;// 间隔取样时间
 
  private void updateMicStatus() {
    if (mMediaRecorder != null) {
      double ratio = (double)mMediaRecorder.getMaxAmplitude() /BASE;
      double db = 0;// 分贝
      if (ratio > 1)
        db = (20 * Math.log10(ratio))*0.7;
      Message message =Message.obtain();
      message.what=0X00;
      message.obj=db;
      handler.sendMessage(message);
      Log.d(TAG,"分贝值："+db);
      handler.postDelayed(mUpdateMicStatusTimer, SPACE);
    }
  }
}