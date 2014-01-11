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
	///< �ӳ�ʱ�������ʱ����
	private static long NETTASK_DELAY = 0;
	///< ��ʱ�������״̬����Ҫ�Ż�:�û��ڲ�Ƶ�������ⰴť���ص�����£������ʵ��ӳ���
	private static long NETTASK_PERIOID = 3000;
	///< ÿ��5s��timer����timerTaskһ�� ��ǧ�����static�������û����Ƶ��������ֹͣ���������
	private Timer timer = null;
	///< ������Timer����
	private NetMonitorTask timerTask = null;
	///< �����ģ�Ϊ��serviceָ�롾this��
	private Context ctx = null;
	///< ����״̬������
	private NetBee_NetState netState = null;

	/**
	 * ����ʵ�ֵĽӿڣ�Ŀǰδʹ��
	 */
	@Override
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * startService�Ǳ�����
	 */
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) 
	{
		// TODO Auto-generated method stub
		super.onStart(intent, startId);

		///< �������������
		startNetMonitor();
	}

	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		super.onCreate();
		ctx = this;
		timer = new Timer();
		timerTask = new NetMonitorTask();
		netState = new NetBee_NetState(ctx);
	}

	/**
	 * onStartʱ���øú���������timer��ʱִ������
	 */
	private void startNetMonitor()
	{           
		timer.scheduleAtFixedRate(timerTask, NETTASK_DELAY, NETTASK_PERIOID);
	}

	/**
	 * �Զ���timer����
	 */
	private class NetMonitorTask extends TimerTask
	{ 
		public void run() 
		{
			toastHandler.sendEmptyMessage(0);
		}
	}  

	/**
	 * ����Handle���������̵߳�����������̸߳���UI
	 */
	@SuppressLint("HandlerLeak")
	private final Handler toastHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			//Toast.makeText(ctx, "��������.....", Toast.LENGTH_SHORT).show();
			
			///< ��������״̬
			netState.updateWifiNetState();
			
			if (netState.ifWifiAvailable())
			{
				if (netState.ifWifiConnected())
				{
					Toast.makeText(ctx, "�Ѿ�������.....", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(ctx, "�Ͽ�.....", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(ctx, "��ǰ���粻����.....", Toast.LENGTH_SHORT).show();
			}
		}
	};    

	/**
	 * stopService�Ǳ�����
	 */
	@Override
	public void onDestroy() 
	{
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

		//Toast.makeText(this, "ֹͣ���� ...", Toast.LENGTH_SHORT).show();
	}
}
