package com.example.netbee2x;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
//import android.view.Menu;
import android.content.Intent;
import android.content.res.Configuration;

public class NetBee extends Activity 
{
	///< ������������ť������service��
	private CheckBox chNetMonitor_Btn;
	
	///< ������
	private Intent chNetMonitor_Btn_Intent;
	///< �������Service����
	private static String monitorServiceName = "com.example.netbee2x.NetBee_Monitor";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		if (true)
		{
		///< �����������л�
		{
			///< ����
			if (this.getResources().getConfiguration().orientation == 
					Configuration.ORIENTATION_LANDSCAPE){ 
				setContentView(R.layout.netbee_activity_portrait); 
				Log.i("screen_direction", "LLLLL ����....");
			} 
			///< ����
			else if (this.getResources().getConfiguration().orientation ==
					Configuration.ORIENTATION_PORTRAIT) { 
				setContentView(R.layout.netbee_activity);  
				Log.i("screen_direction", "LLLLL ����....");
			}
		}
		}
		
		///< ʵ�������������ť�ʹ���һ����֮���Intent����
		chNetMonitor_Btn = (CheckBox)findViewById(R.id.left_button);
		
		///< Service Intent
		chNetMonitor_Btn_Intent = new Intent(NetBee.this, NetBee_Monitor.class);
		
		///< ���ü����¼�
		NetBee_SetListener();
	}
	
	/**
	 * ���ü����¼�
	 */
	private void NetBee_SetListener()
	{
		///< ������������¼�
		chNetMonitor_Btn.setOnCheckedChangeListener(chNetMonitor_Btn_listener);
	}
	
	/**
	 * ������������¼�����
	 */
	private CompoundButton.OnCheckedChangeListener chNetMonitor_Btn_listener = 
			new CompoundButton.OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked)
			{
				///< ��������������У���ֱ�ӷ���;�����ٴ��������������Ȼ������ť״̬Ӧ��д�������ļ�...
				if (isServiceRunning(NetBee.this, monitorServiceName))
				{
					return;
				}
				startService(chNetMonitor_Btn_Intent);
			}
			else
			{
				stopService(chNetMonitor_Btn_Intent);
			}
		}	
	};
	
	/**
	 * ����Serviceǰ�������жϣ����������������
	 * @param mContext
	 * @param className
	 * @return
	 */
	public boolean isServiceRunning(Context mContext, String className) 
	{
		int i;
        ActivityManager activityManager = (ActivityManager)
        mContext.getSystemService(Context.ACTIVITY_SERVICE); 
        List<ActivityManager.RunningServiceInfo> serviceList = 
        		activityManager.getRunningServices(30);
        
        for (i = 0; i < serviceList.size(); ++i) 
        {
            if (serviceList.get(i).service.getClassName().equals(className)) 
            {
                return true;
            }
        }
        return false;
    }

	//	@Override
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//		// Inflate the menu; this adds items to the action bar if it is present.
	//		getMenuInflater().inflate(R.menu.net_bee, menu);
	//		return true;
	//	}

}
