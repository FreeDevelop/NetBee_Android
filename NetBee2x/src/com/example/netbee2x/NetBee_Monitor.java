package com.example.netbee2x;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class NetBee_Monitor extends Service
{
	///< 延迟时间和任务定时周期
	private static long NETTASK_DELAY = 0;
	private static long NETTASK_PERIOID = 5000;
	///< 每隔5s钟timer启动timerTask一次 【千万别用static，否则用户如果频繁启动，停止，会崩溃】
	private Timer timer;
	///< 网络监测Timer任务
	private NetMonitorTask timerTask;
	///< 上下文，为该service指针【this】
	private Context ctx;

	/**
	 * 必须实现的接口，目前未使用
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * startService是被调用
	 */
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);

		Toast.makeText(getApplicationContext(), "启动服务",
				Toast.LENGTH_SHORT).show();
		///< 启动网络监测服务
		startNetMonitor();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ctx = this;
		timer = new Timer();
		timerTask = new NetMonitorTask();
	}

	/**
	 * onStart时调用该函数，利用timer定时执行任务
	 */
	private void startNetMonitor()
	{           
		timer.scheduleAtFixedRate(timerTask, NETTASK_DELAY, NETTASK_PERIOID);
	}

	/**
	 * 自定义timer任务
	 */
	private class NetMonitorTask extends TimerTask
	{ 
		public void run() 
		{
			toastHandler.sendEmptyMessage(0);
		}
	}  

	/**
	 * 创建Handle，接受子线程的任务，配合主线程更新UI
	 */
	@SuppressLint("HandlerLeak")
	private final Handler toastHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			Toast.makeText(ctx, "MONITOR.....", Toast.LENGTH_SHORT).show();
		}
	};    

	/**
	 * stopService是被调用
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != timer)
		{
			timer.cancel();
		}
		if (null != timerTask)
		{
			timerTask.cancel();
		}

		Toast.makeText(this, "停止服务 ...", Toast.LENGTH_SHORT).show();
	}
}
