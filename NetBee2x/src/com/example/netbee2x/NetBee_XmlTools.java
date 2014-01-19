package com.example.netbee2x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

public class NetBee_XmlTools 
{
	///< 解析信息
	private ArrayList<String> arrayList = null;
	///< xml解析器
	private XmlPullParser parser = null;
	///< 输入流
	InputStream input = null;
	///< XmlPullParser解析工厂
	XmlPullParserFactory factory = null;

	/**
	 * 资源目录xml文件读取
	 * @param ct
	 * @param xmlId
	 */
	public NetBee_XmlTools(Context ct, int xmlId) 
	{
		// TODO Auto-generated constructor stub
		arrayList = new ArrayList<String>();
		parser = ct.getResources().getXml(xmlId);
	}

	/**
	 * sdcard目录xml文件读取
	 * @param ct
	 * @param fileName
	 */
	public NetBee_XmlTools(Context ct, String fileName) 
	{
		// TODO Auto-generated constructor stub
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			File sdCardDir = Environment.getExternalStorageDirectory();
			File file = new File(sdCardDir, fileName);
			if (!file.exists())
			{
				return;
			}
			try 
			{
				input = new FileInputStream(file);
				factory = XmlPullParserFactory.newInstance();
				parser = factory.newPullParser();  
				parser.setInput(input, "UTF-8");  //设置输入流以及编码
			}
			catch (XmlPullParserException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  	  	 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 采用pull生成XML文件
	 * @param userInf   - 更新用户信息，重新写入配置文件
	 * @param filename  - 更新文件名
	 * @return
	 */
	public boolean writeXML(ArrayList<String> userInf, String filename)
	{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			File sdCardDir = Environment.getExternalStorageDirectory();
			File file = new File(sdCardDir, filename);
			if (file.exists())
			{
				file.delete();
				file = new File(sdCardDir, filename);
			}
			XmlSerializer serializer = Xml.newSerializer();
			FileOutputStream fos = null;
			try {
				// 根据文件对象创建一个文件的输出流对象
				fos = new FileOutputStream(file);
				// 设置输出的流及编码
				serializer.setOutput(fos,"UTF-8");
				// 设置文件的开始
				serializer.startDocument("UTF-8", true);
				// user_choice标签开始
				serializer.startTag(null, "userdata");
				serializer.attribute("null", "createuser", "false");
				//			for(String person:persons)
				{
					// 设置user_choice标签的子标签 appInf
					serializer.startTag(null, "appInf");
					serializer.startTag(null, "start");	
					serializer.text(userInf.get(0));
					serializer.endTag(null, "start");
					serializer.startTag(null, "monitor");	
					serializer.text(userInf.get(1));
					serializer.endTag(null, "monitor");
					serializer.endTag(null, "appInf");
					// 设置user_choice标签的子标签的net
					serializer.startTag(null, "net");
					serializer.startTag(null, "status");	
					serializer.text(userInf.get(2));
					serializer.endTag(null, "status");
					serializer.endTag(null, "net");
					// 设置user_choice标签的子标签的compass
					serializer.startTag(null, "compass");
					serializer.startTag(null, "open");	
					serializer.text(userInf.get(3));
					serializer.endTag(null, "open");
					serializer.endTag(null, "compass");
				}
				serializer.endTag(null, "userdata");
				serializer.endDocument();
				fos.flush();
				fos.close();
				return true;
			} catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public ArrayList<String> getXmlInf()
	{
		//		arrayList.clear();
		if (null == parser)
		{
			return null;
		}

		try {
			///< 产生第一个事件
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT)
			{
				switch (event)
				{
				case XmlPullParser.START_TAG:
					break;
				case XmlPullParser.TEXT:
					arrayList.add(parser.getText());
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				event = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arrayList;
	}
}
