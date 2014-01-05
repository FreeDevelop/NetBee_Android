package com.example.netbee2x;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;

public class NetBee extends Activity 
{
	///< 网络监测启动按钮【关联service】
	private CheckBox chNetMonitor_Btn;
	///< 绑定属性
	private Intent chNetMonitor_Btn_Intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.netbee_activity);
		
		///< 实例化网络监听按钮和创建一个与之相关Intent对象
		chNetMonitor_Btn = (CheckBox)findViewById(R.id.left_button);
		chNetMonitor_Btn_Intent = new Intent(NetBee.this, NetBee_Monitor.class);
		
		///< 设置监听事件
		NetBee_SetListener();
	}
	
	/*
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
				startService(chNetMonitor_Btn_Intent);
			}
			else
			{
				stopService(chNetMonitor_Btn_Intent);
			}
		}	
	};

	//	@Override
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//		// Inflate the menu; this adds items to the action bar if it is present.
	//		getMenuInflater().inflate(R.menu.net_bee, menu);
	//		return true;
	//	}

}
