package com.example.netbee2x;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class NetBee_NetState
{
	private ConnectivityManager conMana = null;
	private NetworkInfo netInf = null;
	
	///< getSystemService是基于上下文的，因此需要一个上下文对象方可获得此系统服务
	public NetBee_NetState(Context context) {
		// TODO Auto-generated constructor stub
		conMana = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		netInf = conMana.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	}
	
	public void updateWifiNetState()
	{
		netInf = conMana.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	}
	
	public State getWifiNetState()
	{
		return netInf.getState();
	}
	
	public boolean ifWifiAvailable()
	{
		return netInf.isAvailable();
	}
	
	public boolean ifWifiConnected()
	{
		return netInf.isConnected();
	}
	
	public boolean ifWifiRoaming()
	{
		return netInf.isRoaming();
	}
}
