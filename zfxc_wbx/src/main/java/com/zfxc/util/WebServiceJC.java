package com.zfxc.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;



import android.R.string;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.StrictMode;
import android.util.Xml;
import android.widget.TextView;

public class WebServiceJC {
	
	// 定义Web Service的命名空间
    static final String SERVICE_NS = "http://tempuri.org/";
   // 定义Web Service提供服务的URL
   // static final String SERVICE_URL ="http://192.168.2.145/ServiceforBinzhou/Service.asmx";
	//static final String SERVICE_URL ="http://192.168.2.145/xuchangwebservice/Service.asmx";
 	// static final String SERVICE_URL ="http://www.aiot.gov.cn/ServiceforBinzhou/Service.asmx";
	public static  String SERVICE_URL ="";
	public static int timeoutset=15000;
	public static String susername="";
	public static String spassword="";
	public static String scompanycode="";
	
	
	
	
	//方法
	public static int WorkJC(String tracecode){
		// 调用的方法
		String methodName = "checkCode";
		// 创建HttpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht.debug=true;
		// 使用SOAP1.1协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
			SoapEnvelope.VER11);
		
		// 实例化SoapObject对象

		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("code", tracecode);
		
		envelope.bodyOut = soapObject;
		// 设置与.Net提供的Web Service保持较好的兼容性
		envelope.dotNet = true;
		
		try
		{
			// 调用Web Service
			ht.call(SERVICE_NS + methodName, envelope);
			/* 与下面的一行语句结果是一样的
			 * Object object=envelope.getResponse();
			if (object != null)*/
			if (envelope.getResponse() != null)
			{
				// 获取服务器响应返回的SOAP消息
				SoapObject result = (SoapObject) envelope.bodyIn;
			//下面两行会出现java.lang.ClassCastException: org.ksoap2.serialization.SoapPrimitive错误，但是数据可以添加至数据库
			//SoapObject detail = (SoapObject) result.getProperty(methodName+ "Result");
			//return parseAddResult(detail);
			String detail =  result.getProperty(methodName+ "Result").toString();
	        System.out.println("追溯码查询结果==========="+detail);
			int flag=Integer.parseInt(detail);
			// 解析服务器响应的SOAP消息。
		   return flag;
	          
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (XmlPullParserException e)
		{
			e.printStackTrace();
		}
		return 0;
		
		
	}
	
	//方法
		public static String WorkJC(String methodName,HashMap<String ,String > parms){
			 setNetWorkCondition();
			// 调用的方法
		
			// 创建HttpTransportSE传输对象
			HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
			ht.debug=true;
			// 使用SOAP1.1协议创建Envelop对象
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
			
			// 实例化SoapObject对象

			SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
			
			Iterator<String> iterator = parms.keySet().iterator();
			while(iterator.hasNext()) {
				
				String parmname=iterator.next().toString();
				String parmvalue=parms.get(parmname);
				soapObject.addProperty(parmname, parmvalue);
			
			}
		
			
			envelope.bodyOut = soapObject;
			// 设置与.Net提供的Web Service保持较好的兼容性
			envelope.dotNet = true;
			
			try
			{
				// 调用Web Service
				ht.call(SERVICE_NS + methodName, envelope);
			
				if (envelope.getResponse() != null)
				{
					// 获取服务器响应返回的SOAP消息
					SoapObject result = (SoapObject) envelope.bodyIn;
			
				String detail =  result.getProperty(methodName+ "Result").toString();
		        System.out.println("追溯码查询结果==========="+detail);
			
			   return detail;
		          
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (XmlPullParserException e)
			{
				e.printStackTrace();
			}
			return "";
			
			
		}
//根据用户名密码获取企业的基本信息
	public static DataTable getCompanyInformation(String username,String password){
		
//		WebServiceJC.SERVICE_URL="";
		
		// 调用的方法
		String methodName = "getUserXml";
		// 创建HttpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht=new HttpTransportSE(SERVICE_URL, timeoutset);
		ht.debug=true;
		// 使用SOAP1.1协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
			SoapEnvelope.VER11);
		
		// 实例化SoapObject对象

		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("uname", username);
		soapObject.addProperty("upass", password);
		envelope.bodyOut = soapObject;
		// 设置与.Net提供的Web Service保持较好的兼容性
		envelope.dotNet = true;
		
		try
		{
			// 调用Web Service
			ht.call(SERVICE_NS + methodName, envelope);
			/* 与下面的一行语句结果是一样的
			 * Object object=envelope.getResponse();
			if (object != null)*/
			if (envelope.getResponse() != null)
			{
				// 获取服务器响应返回的SOAP消息
				SoapObject result = (SoapObject) envelope.bodyIn;
			//下面两行会出现java.lang.ClassCastException: org.ksoap2.serialization.SoapPrimitive错误，但是数据可以添加至数据库
			//SoapObject detail = (SoapObject) result.getProperty(methodName+ "Result");
			//return parseAddResult(detail);
			String detail =  result.getProperty(methodName+ "Result").toString();
	        System.out.println("==========="+detail);
			//int flag=Integer.parseInt(detail);
			// 解析服务器响应的SOAP消息。
			
			DataTable dt= getChannelList(detail);
		   return dt;
	          
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
			return null;
		}
		catch (XmlPullParserException e)
		{
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return null;
		
	}
	
	//根据企业号获取地块信息
	public static  DataTable getPlot(String companyNumber){
		// 调用的方法
				String methodName = "getplot";
				// 创建HttpTransportSE传输对象
				HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
				ht=new HttpTransportSE(SERVICE_URL, timeoutset);
				ht.debug=true;
				// 使用SOAP1.1协议创建Envelop对象
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
				
				// 实例化SoapObject对象

				SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
				soapObject.addProperty("companynumber", companyNumber);				
				envelope.bodyOut = soapObject;
				// 设置与.Net提供的Web Service保持较好的兼容性
				envelope.dotNet = true;
				
				try
				{
				
					// 调用Web Service
					ht.call(SERVICE_NS + methodName, envelope);
					/* 与下面的一行语句结果是一样的
					 * Object object=envelope.getResponse();
					if (object != null)*/
					if (envelope.getResponse() != null)
					{
						// 获取服务器响应返回的SOAP消息
						SoapObject result = (SoapObject) envelope.bodyIn;
					//下面两行会出现java.lang.ClassCastException: org.ksoap2.serialization.SoapPrimitive错误，但是数据可以添加至数据库
					//SoapObject detail = (SoapObject) result.getProperty(methodName+ "Result");
					//return parseAddResult(detail);
					String detail =  result.getProperty(methodName+ "Result").toString();
			        System.out.println("==========="+detail);
					//int flag=Integer.parseInt(detail);
					// 解析服务器响应的SOAP消息。
					
					DataTable dt= getChannelList(detail);
				   return dt;
			          
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
					return null;
				}
				catch (XmlPullParserException e)
				{
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		return null;
	}
	//根据企业号获取信息
	public static  DataTable getInfoByCompanyNumber(String companyNumber,String smethodName){
			// 调用的方法
					String methodName =smethodName;
					// 创建HttpTransportSE传输对象
					HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
					ht=new HttpTransportSE(SERVICE_URL, timeoutset);
					ht.debug=true;
					// 使用SOAP1.1协议创建Envelop对象
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
					
					// 实例化SoapObject对象

					SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
					soapObject.addProperty("companynumber", companyNumber);				
					envelope.bodyOut = soapObject;
					// 设置与.Net提供的Web Service保持较好的兼容性
					envelope.dotNet = true;
					
					try
					{
						// 调用Web Service
						ht.call(SERVICE_NS + methodName, envelope);
						/* 与下面的一行语句结果是一样的
						 * Object object=envelope.getResponse();
						if (object != null)*/
						if (envelope.getResponse() != null)
						{
							// 获取服务器响应返回的SOAP消息
							SoapObject result = (SoapObject) envelope.bodyIn;
						//下面两行会出现java.lang.ClassCastException: org.ksoap2.serialization.SoapPrimitive错误，但是数据可以添加至数据库
						//SoapObject detail = (SoapObject) result.getProperty(methodName+ "Result");
						//return parseAddResult(detail);
						String detail =  result.getProperty(methodName+ "Result").toString();
				        System.out.println("==========="+detail);
						//int flag=Integer.parseInt(detail);
						// 解析服务器响应的SOAP消息。
						
						DataTable dt= getChannelList(detail);
					   return dt;
				          
						}
					}
					catch (IOException e)
					{
						e.printStackTrace();
						return null;
					}
					catch (XmlPullParserException e)
					{
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			return null;
		}
	//上传
	public static Object callWebServiceMethod(String methodname,HashMap<String, String>params){
		
		
		// 创建HttpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht=new HttpTransportSE(SERVICE_URL, timeoutset);
		ht.debug=true;
		// 使用SOAP1.1协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
			SoapEnvelope.VER11);
		
	
		// 实例化SoapObject对象

		SoapObject soapObject = new SoapObject(SERVICE_NS, methodname);
		
		for(Entry<String, String> entry:params.entrySet())
		  {
			soapObject.addProperty(entry.getKey(), entry.getValue());
		  }
		//soapObject.addProperty("code", tracecode);
		
		envelope.bodyOut = soapObject;
		// 设置与.Net提供的Web Service保持较好的兼容性
		envelope.dotNet = true;
		
		try
		{
			// 调用Web Service
			ht.call(SERVICE_NS + methodname, envelope);
			/* 与下面的一行语句结果是一样的
			 * Object object=envelope.getResponse();
			if (object != null)*/
			if (envelope.getResponse() != null)
			{
				// 获取服务器响应返回的SOAP消息
				SoapObject result = (SoapObject) envelope.bodyIn;
			//下面两行会出现java.lang.ClassCastException: org.ksoap2.serialization.SoapPrimitive错误，但是数据可以添加至数据库
			//SoapObject detail = (SoapObject) result.getProperty(methodName+ "Result"); 
			//return parseAddResult(detail);
			String detail =  result.getProperty(methodname+ "Result").toString();
	        System.out.println("追溯码查询结果==========="+detail);
		//	int flag=Integer.parseInt(detail);
			// 解析服务器响应的SOAP消息。
		   return detail;
	          
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (XmlPullParserException e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}
	
public static Object callWebServiceMethodbyBytes(String methodname,HashMap<String, Object>params){
		
		
		// 创建HttpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht=new HttpTransportSE(SERVICE_URL, timeoutset);
		ht.debug=true;
		// 使用SOAP1.1协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
			SoapEnvelope.VER11);
		
	
		// 实例化SoapObject对象

		SoapObject soapObject = new SoapObject(SERVICE_NS, methodname);
		
		for(Entry<String, Object> entry:params.entrySet())
		  {
			soapObject.addProperty(entry.getKey(), entry.getValue());
		  }
		//soapObject.addProperty("code", tracecode);
		
		envelope.bodyOut = soapObject;
		// 设置与.Net提供的Web Service保持较好的兼容性
		envelope.dotNet = true;
		
		try
		{
			// 调用Web Service
			ht.call(SERVICE_NS + methodname, envelope);
			/* 与下面的一行语句结果是一样的
			 * Object object=envelope.getResponse();
			if (object != null)*/
			if (envelope.getResponse() != null)
			{
				// 获取服务器响应返回的SOAP消息
				SoapObject result = (SoapObject) envelope.bodyIn;
			//下面两行会出现java.lang.ClassCastException: org.ksoap2.serialization.SoapPrimitive错误，但是数据可以添加至数据库
			//SoapObject detail = (SoapObject) result.getProperty(methodName+ "Result"); 
			//return parseAddResult(detail);
			String detail =  result.getProperty(methodname+ "Result").toString();
	        System.out.println("追溯码查询结果==========="+detail);
		//	int flag=Integer.parseInt(detail);
			// 解析服务器响应的SOAP消息。
		   return detail;
	          
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (XmlPullParserException e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}
	private static List<String> parseResult(SoapObject detail)
	{
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < detail.getPropertyCount(); i++)
		{
			// 解析出每个省份
		//result.add(detail.getProperty(i).toString().split(",")[0]);
		System.out.println("detail.getProperty("+i+").toString()======="+detail.getProperty(i).toString());	
		result.add(detail.getProperty(i).toString());

		}
		return result;
	}
	
	private static int parseAddResult(SoapObject detail)
	{
	    System.out.println("添加记录的返回值="+detail.getProperty(0).toString());
		int addFlag=Integer.parseInt(detail.getProperty(0).toString());
		return addFlag;
	}
	
	class ComPlotBean{
		
		 String CoRegNo;
		 public String getCoRegNo() {
			return CoRegNo;
		}
		public void setCoRegNo(String coRegNo) {
			CoRegNo = coRegNo;
		}
		public String getPlotStr() {
			return PlotStr;
		}
		public void setPlotStr(String plotStr) {
			PlotStr = plotStr;
		}
		String PlotStr;
		 
	}
	
	public  static DataTable getChannelList(String xmlString) throws ParserConfigurationException, SAXException, IOException
	     {
		DataTable dataTable=new DataTable();
		String xml="";
		xml="<NewDataSet> <Table> <sProductname>生菜</sProductname> <sproducttype>绿叶蔬菜</sproducttype> <sproductid>0001</sproductid> </Table> <Table> <sProductname>大白菜</sProductname> <sproducttype>绿叶蔬菜</sproducttype> <sproductid>0002</sproductid> </Table></NewDataSet>";
		
		xml=xmlString;
		xml=xml.replace("\n", "");
		 ByteArrayInputStream tInputStringStream = null;
		  try
		  {
		  if (xml != null && !xml.trim().equals("")) {
		   tInputStringStream = new ByteArrayInputStream(xml.getBytes());
		  }
		  }
		  catch (Exception e) {
		   // TODO: handle exception
		   
		   return null;
		  }
		  XmlPullParser parser = Xml.newPullParser();
		  try {
			  String aa1="";
		   parser.setInput(tInputStringStream, "UTF-8");
		   int eventType = parser.getEventType();
		   List<DataColumn> c=null;
		   while (eventType != XmlPullParser.END_DOCUMENT) {
			
			   //System.out.println("a");
		    switch (eventType) {
		    case XmlPullParser.START_DOCUMENT:// 文档开端事务，可以进行数据初始化处理
		    	 
		     break;
		    case XmlPullParser.START_TAG:// 开端元素事务
		
		    
		     try{
		    	   String name = parser.getName();
		
		    	 
		    	   
		   if(name.equalsIgnoreCase("table")){//行的开始
		    	c=new ArrayList<DataColumn>();
		    	
		    }
		    	 if(!name.equalsIgnoreCase("dataset")&&!name.equalsIgnoreCase("table")){
		    		 aa1=parser.nextText();
		    		 
		    		 DataColumn dc=new DataColumn(name, aa1);
			    	 //System.out.println("name:"+name+",value:"+aa1);
		    		 c.add(dc);
			    	 if (parser.getEventType() != XmlPullParser.END_TAG) {  
			             parser.nextTag();  
			    	 }
		    	 }
		    	 
		     }catch (Exception es){
		    	  
		     }
		     break;
		    case XmlPullParser.END_TAG:// 停止元素事务
		     if (parser.getName().equalsIgnoreCase("table")) {
		    	 dataTable.add(new DataRow(c));
		      c=null;
		     }
		     break;
		    }
		    eventType = parser.next();
		   }
		   tInputStringStream.close();
		   // return persons;
		  } catch (XmlPullParserException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  
		  return dataTable;
		 }
	
	public  static DataTable getChannelListzfxc(String xmlString) throws ParserConfigurationException, SAXException, IOException
    {
	DataTable dataTable=new DataTable();
	String xml="";
	xml="<NewDataSet> <Table> <sProductname>生菜</sProductname> <sproducttype>绿叶蔬菜</sproducttype> <sproductid>0001</sproductid> </Table> <Table> <sProductname>大白菜</sProductname> <sproducttype>绿叶蔬菜</sproducttype> <sproductid>0002</sproductid> </Table></NewDataSet>";
	
	xml=xmlString;
	xml=xml.replace("\n", "");
	 ByteArrayInputStream tInputStringStream = null;
	  try
	  {
	  if (xml != null && !xml.trim().equals("")) {
	   tInputStringStream = new ByteArrayInputStream(xml.getBytes());
	  }
	  }
	  catch (Exception e) {
	   // TODO: handle exception
	   
	   return null;
	  }
	  XmlPullParser parser = Xml.newPullParser();
	  try {
		  String aa1="";
	   parser.setInput(tInputStringStream, "UTF-8");
	   int eventType = parser.getEventType();
	   List<DataColumn> c=null;
	   while (eventType != XmlPullParser.END_DOCUMENT) {
		
		   //System.out.println("a");
	    switch (eventType) {
	    case XmlPullParser.START_DOCUMENT:// 文档开端事务，可以进行数据初始化处理
	    	 
	     break;
	    case XmlPullParser.START_TAG:// 开端元素事务
	
	    
	     try{
	    	   String name = parser.getName();
	
	    	 
	    	   
	   if(name.equalsIgnoreCase("resulttable")){//行的开始
	    	c=new ArrayList<DataColumn>();
	    	
	    }
	    	 if(!name.equalsIgnoreCase("dataset")&&!name.equalsIgnoreCase("resulttable")){
	    		 aa1=parser.nextText();
	    		 
	    		 DataColumn dc=new DataColumn(name, aa1);
		    	 //System.out.println("name:"+name+",value:"+aa1);
	    		 c.add(dc);
		    	 if (parser.getEventType() != XmlPullParser.END_TAG) {  
		             parser.nextTag();  
		    	 }
	    	 }
	    	 
	     }catch (Exception es){
	    	  
	     }
	     break;
	    case XmlPullParser.END_TAG:// 停止元素事务
	     if (parser.getName().equalsIgnoreCase("resulttable")) {
	    	 dataTable.add(new DataRow(c));
	      c=null;
	     }
	     break;
	    }
	    eventType = parser.next();
	   }
	   tInputStringStream.close();
	   // return persons;
	  } catch (XmlPullParserException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	  
	  return dataTable;
	 }
	
	@SuppressLint("NewApi")
	public static void setNetWorkCondition(){
		String strVer=android.os.Build.VERSION.RELEASE.toString();
		strVer=strVer.substring(0,3).trim();
		float fv=Float.valueOf(strVer);
		if(fv>2.3)
		{
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads()
		.detectDiskWrites()
		.detectNetwork() // 这里可以替换为detectAll() 就包括了磁盘读写和网络I/O
		.penaltyLog() //打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
		.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		.detectLeakedSqlLiteObjects() //探测SQLite数据库操作
		.penaltyLog() //打印logcat
		.penaltyDeath()
		.build()); 
		}
		
	}
	//设置内外网
	public static void setUrl(String webtype,  MyDbHelper myDBHelper){
		Cursor c =null;
		try{
		
	

			myDBHelper.open();
		String sql="select * from "+MyDbInfo.getTableNames()[3];
		 c = myDBHelper.select(sql);
		c.moveToFirst();
			CharSequence[] list = new CharSequence[c.getCount()];
			
			if(list.length>0){
				
			if(webtype.equals("inner")){
				WebServiceJC.SERVICE_URL=c.getString(0);
				
			}else{
				WebServiceJC.SERVICE_URL=c.getString(1);
				
			}
				
			
			}
			
			c.close();
			myDBHelper.close();
}catch(Exception e){
		e.printStackTrace();
	}finally{
		
		try{
			c.close();
			myDBHelper.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	}
	public interface TBwebservicecfgChema{
		//_id INTEGER PRIMARY KEY AUTOINCREMENT, susername VARCHAR,spassword VARCHAR,time1 VARCHAR,time2 VARCHAR,time3 VARCHAR,scompanycode VARCHAR
		String TABLE_NAME = "tbwebservicecfg";          //Table Name
		String SINNERWS = "sinnerws";                    //ID
		String SOUTTERWS = "soutterws";        
		String TIME1 = "time1";       
		String TIME2 = "time2"; 
	
		
	//	sinnerws VARCHAR,soutterws VARCHAR,time1 VARCHAR,time2 VARCHAR
		
	}
}
