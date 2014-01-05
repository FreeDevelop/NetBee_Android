package com.example.netbee2x;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;

public class NetBee extends Activity 
{
	///< ������������ť������service��
	private CheckBox chNetMonitor_Btn;
	///< ������
	private Intent chNetMonitor_Btn_Intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.netbee_activity);
		
		///< ʵ�������������ť�ʹ���һ����֮���Intent����
		chNetMonitor_Btn = (CheckBox)findViewById(R.id.left_button);
		chNetMonitor_Btn_Intent = new Intent(NetBee.this, NetBee_Monitor.class);
		
		///< ���ü����¼�
		NetBee_SetListener();
	}
	
	/*
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
