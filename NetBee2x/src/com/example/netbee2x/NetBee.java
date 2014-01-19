package com.example.netbee2x;

import java.util.ArrayList;
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
	///< 网络监测自启动【修改配置文件】
	private CheckBox chNetAutoStart_Btn;
	///< 指南针模式【重置布局、更新配置文件】
	private CheckBox chNetCompassModel_Btn;
	///< xml解析器
	private NetBee_XmlTools netXml = null;
	///< 配置信息选项【目前为三项】
	private ArrayList<String> sArrayList = null;
	///< 绑定属性
	private Intent chNetMonitor_Btn_Intent;
	///< 网络监听Service名称
	private static String monitorServiceName = "com.example.netbee2x.NetBee_Monitor";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		///< 初始化布局和组件
		NetBee_InitLayout();
		
		///< 重置布局和组件
		NetBee_ResetLayout();
		
		///< Service Intent
		chNetMonitor_Btn_Intent = new Intent(NetBee.this, NetBee_Monitor.class);
		
		///< 设置监听事件
		NetBee_SetListener();
	}
	
	/**
	 * 程序退出时调用函数，同时更新配置文件
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		///< 程序退出时配置信息写入文件
		netXml.writeXML(sArrayList, "user_choice.xml");
		
		super.onDestroy();
	}
	
	/**
	 * 初始化布局和组件
	 */
	private void NetBee_InitLayout()
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

		///< 是否开机自启动
		chNetAutoStart_Btn = (CheckBox)findViewById(R.id.right_button);
		///< 实例化网络监听按钮和创建一个与之相关Intent对象
		chNetMonitor_Btn = (CheckBox)findViewById(R.id.left_button);
		///< 是否启动指南针模式
		chNetCompassModel_Btn = (CheckBox)findViewById(R.id.right_top_button);
	
		///< 解析用户配置文件【决定按钮的选中状态和监听状态】
		netXml = new NetBee_XmlTools(NetBee.this, R.xml.user_choice);
		///< 更改为从sdcard读取xml配置文件，不过需要对首次进行处理【首次没有配置文件】
		//		netXml = new NetBee_XmlTools(NetBee.this, "user_choice.xml");
		/// 获得解析对象
		sArrayList = netXml.getXmlInf();
	}
	
	/**
	 * 根据配置文件重置组件状态
	 */
	private void NetBee_ResetLayout()
	{
		/*		///< 解析用户配置文件【决定按钮的选中状态和监听状态】
		netXml = new NetBee_XmlTools(NetBee.this, R.xml.user_choice);
		/// 获得解析对象
		sArrayList = netXml.getXmlInf();*/

		///< 根据配置文件修改按钮选中状态
		chNetAutoStart_Btn.setChecked(sArrayList.get(0).equals("yes"));
		///< 根据配置文件修改按钮选中状态
		chNetMonitor_Btn.setChecked(sArrayList.get(1).equals("yes"));
		///< 根据配置文件修改按钮选中状态
		chNetCompassModel_Btn.setChecked(sArrayList.get(3).equals("yes"));
	}
	
	/**
	 * 设置监听事件
	 */
	private void NetBee_SetListener()
	{
		///< 设置网络监听事件
		chNetMonitor_Btn.setOnCheckedChangeListener(chNetMonitor_Btn_listener);
		///< 设置开机启动启动监听事件
		chNetAutoStart_Btn.setOnCheckedChangeListener(chNetAutoStart_Btn_listener);
		///< 设置指南针模式监听事件
		chNetCompassModel_Btn.setOnCheckedChangeListener(chNetCompassModel_Btn_listener);
	}
	
	/**
	 * 定义网络监听事件方法
	 */
	private CompoundButton.OnCheckedChangeListener chNetMonitor_Btn_listener = 
			new CompoundButton.OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) 
		{
			// TODO Auto-generated method stub
			///< 更新配置信息
			sArrayList.remove(1);
			sArrayList.add(1, isChecked + "");
			
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
	 * 定义指南针模式监听事件方法
	 */
	private CompoundButton.OnCheckedChangeListener chNetCompassModel_Btn_listener = 
			new CompoundButton.OnCheckedChangeListener() 
	{			
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
		{
			// TODO Auto-generated method stub
			///< 更新配置信息
			sArrayList.remove(3);
			sArrayList.add(3, isChecked + "");
		}
	};
	
	/**
	 * 定义开机自启动监听事件方法
	 */
	private CompoundButton.OnCheckedChangeListener chNetAutoStart_Btn_listener = 
			new CompoundButton.OnCheckedChangeListener() 
	{			
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
		{
			// TODO Auto-generated method stub
			///< 更新配置信息
			sArrayList.remove(0);
			sArrayList.add(0, isChecked + "");
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
