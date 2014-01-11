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
	///< 网络监测启动按钮【关联service】
	private CheckBox chNetMonitor_Btn;
	
	///< 绑定属性
	private Intent chNetMonitor_Btn_Intent;
	///< 网络监听Service名称
	private static String monitorServiceName = "com.example.netbee2x.NetBee_Monitor";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		if (true)
		{
		///< 横竖屏布局切换
		{
			///< 横屏
			if (this.getResources().getConfiguration().orientation == 
					Configuration.ORIENTATION_LANDSCAPE){ 
				setContentView(R.layout.netbee_activity_portrait); 
				Log.i("screen_direction", "LLLLL 横屏....");
			} 
			///< 竖屏
			else if (this.getResources().getConfiguration().orientation ==
					Configuration.ORIENTATION_PORTRAIT) { 
				setContentView(R.layout.netbee_activity);  
				Log.i("screen_direction", "LLLLL 竖屏....");
			}
		}
		}
		
		///< 实例化网络监听按钮和创建一个与之相关Intent对象
		chNetMonitor_Btn = (CheckBox)findViewById(R.id.left_button);
		
		///< Service Intent
		chNetMonitor_Btn_Intent = new Intent(NetBee.this, NetBee_Monitor.class);
		
		///< 设置监听事件
		NetBee_SetListener();
	}
	
	/**
	 * 设置监听事件
	 */
	private void NetBee_SetListener()
	{
		///< 设置网络监听事件
		chNetMonitor_Btn.setOnCheckedChangeListener(chNetMonitor_Btn_listener);
	}
	
	/**
	 * 定义网络监听事件方法
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
				///< 如果服务正在运行，则直接返回;否则再次启动会崩溃，当然启动按钮状态应该写入配置文件...
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
	 * 启动Service前，做个判断，否则容易引起崩溃
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
