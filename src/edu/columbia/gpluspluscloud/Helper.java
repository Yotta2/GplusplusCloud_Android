package edu.columbia.gpluspluscloud;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.*;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Telephony.Sms;
import android.util.Log;
import android.widget.*;
//import android.provider.

public class Helper {
	public static String tmpCallLogPath = "/mnt/sdcard/DCIM/tmp_call_log.txt";
	public static String tmpSmsLogPath = "/mnt/sdcard/DCIM/tmp_sms_log.txt";
	public static String picturePath = "/mnt/sdcard/DCIM/Camera/";
	public static String serverIp = "54.197.230.223";
	
	public static final Uri callLogUri = CallLog.Calls.CONTENT_URI;
	public static final Uri uriSms = Uri.parse("content://sms/inbox");
	
	public static final String[] callLogTypes = new String[]{
			 CallLog.Calls.NUMBER, 
			 CallLog.Calls.DURATION, 
			 CallLog.Calls.CACHED_NAME,
			 CallLog.Calls.DATE,
			 CallLog.Calls.TYPE};
	public static final String[] smsLogTypes = new String[]{
		"_id", "address", "date", "body"
		

	   ,"thread_id"

	    

	    ,"type"

	    ,"read"

	    ,"status"

	    ,"service_center"

	    ,"protocol"

	    ,"person"
	    ,"seen"
	};
	/*public static class sms{
		String address;
		String body;
		public static 
	}*/
	public static JSONArray fetchInbox(ContentResolver contentResolver){
		 	JSONArray sms = new JSONArray();
	        Cursor cursor = contentResolver.query(uriSms, smsLogTypes,null,null,null);
	        
	        /*cursor.moveToFirst();
	        while  (cursor.moveToNext())
	        {
	        		JSONObject jo = new JSONObject();
	               String address = cursor.getString(1);
	               String body = cursor.getString(3);
	               try {
					jo.put("address", address);
					jo.put("body", body);
					jo.put(name, value)
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	               
	               System.out.println("======&gt; Mobile number =&gt; "+address);
	               System.out.println("=====&gt; SMS Text =&gt; "+body);
	 
	               sms.put(jo);
	        }*/
	        if (cursor != null) {
				 while (cursor.moveToNext()) {
					 try{
						 JSONObject obj = new JSONObject();
						 for (int i=0; i<smsLogTypes.length; i++) {
							 String value = cursor.getString(cursor.getColumnIndex(smsLogTypes[i]));
							 obj.put(smsLogTypes[i], value);
						 }				 
						 sms.put(obj);
						
					 } catch (JSONException e) {
							// TODO Auto-generated catch block
						 e.printStackTrace();
					 }
					 
				 }
			 }
			 		 
	        return sms;
	 
	    }
	
	/*
	 * get an sms from 
	 */
	// get json object from content provider
	public static JSONArray getContentFromProvider(ContentResolver contentResolver, Uri uri, String[] valueTypes) {
		 Cursor cursor = contentResolver.query(
                 uri, null, null, null, null);
		 JSONArray array = new JSONArray();
		 
		 if (cursor != null) {
			 while (cursor.moveToNext()) {
				 try{
					 JSONObject obj = new JSONObject();
					 for (int i=0; i<valueTypes.length; i++) {
						 String value = cursor.getString(cursor.getColumnIndex(valueTypes[i]));
						 obj.put(valueTypes[i], value);
					 }				 
					 array.put(obj);
					
				 } catch (JSONException e) {
						// TODO Auto-generated catch block
					 e.printStackTrace();
				 }
				 
			 }
		 }
		 		 
		return array;
	}
	
	// Insert json array content according to Uri
	public static void insertJSONArrayContent(ContentResolver contentResolver, Uri uri, JSONArray array) throws JSONException {
		Log.i("uri",uri.toString());
		for (int i=0; i<array.length(); i++) {
			//Log.i("i",array.getJSONObject(i).toString());
			insertJSONObjectContent(contentResolver, uri, array.getJSONObject(i));
		}
	}
	
	// Insert json object content according to Uri
	public static void insertJSONObjectContent(ContentResolver contentResolver, Uri uri, JSONObject obj) throws JSONException {
		ContentValues values = new ContentValues();
		Iterator<String> it = (Iterator<String>) obj.keys();
		while(it.hasNext()) {
			String key = it.next();
			values.put(key, obj.getString(key));
		}
		contentResolver.insert(uri, values);	
	}
	
}
