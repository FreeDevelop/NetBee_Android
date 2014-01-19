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
	///< ������Ϣ
	private ArrayList<String> arrayList = null;
	///< xml������
	private XmlPullParser parser = null;
	///< ������
	InputStream input = null;
	///< XmlPullParser��������
	XmlPullParserFactory factory = null;

	/**
	 * ��ԴĿ¼xml�ļ���ȡ
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
	 * sdcardĿ¼xml�ļ���ȡ
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
				parser.setInput(input, "UTF-8");  //�����������Լ�����
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
	 * ����pull����XML�ļ�
	 * @param userInf   - �����û���Ϣ������д�������ļ�
	 * @param filename  - �����ļ���
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
				// �����ļ����󴴽�һ���ļ������������
				fos = new FileOutputStream(file);
				// �����������������
				serializer.setOutput(fos,"UTF-8");
				// �����ļ��Ŀ�ʼ
				serializer.startDocument("UTF-8", true);
				// user_choice��ǩ��ʼ
				serializer.startTag(null, "userdata");
				serializer.attribute("null", "createuser", "false");
				//			for(String person:persons)
				{
					// ����user_choice��ǩ���ӱ�ǩ appInf
					serializer.startTag(null, "appInf");
					serializer.startTag(null, "start");	
					serializer.text(userInf.get(0));
					serializer.endTag(null, "start");
					serializer.startTag(null, "monitor");	
					serializer.text(userInf.get(1));
					serializer.endTag(null, "monitor");
					serializer.endTag(null, "appInf");
					// ����user_choice��ǩ���ӱ�ǩ��net
					serializer.startTag(null, "net");
					serializer.startTag(null, "status");	
					serializer.text(userInf.get(2));
					serializer.endTag(null, "status");
					serializer.endTag(null, "net");
					// ����user_choice��ǩ���ӱ�ǩ��compass
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
			///< ������һ���¼�
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
