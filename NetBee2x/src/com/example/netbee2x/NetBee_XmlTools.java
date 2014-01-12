package com.example.netbee2x;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

public class NetBee_XmlTools 
{
	///< 解析信息
	private ArrayList<String> arrayList = null;
    ///< xml解析器
	private XmlPullParser parser = null;
	
	public NetBee_XmlTools(Context ct, int xmlId) 
	{
		// TODO Auto-generated constructor stub
		arrayList = new ArrayList<String>();
        parser = ct.getResources().getXml(xmlId);
	}
	
	public ArrayList<String> getXmlInf()
	{
//		arrayList.clear();
		
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
