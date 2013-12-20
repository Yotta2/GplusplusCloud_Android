package edu.columbia.gpluspluscloud;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

public class Helper {
	public static String tmpCallLogPath = "/mnt/sdcard/tmp/tmp_call_log.txt";
	public static String picturePath = "/mnt/sdcard/DCIM/Camera/";
	public static String serverIp = "54.197.230.223";
	
	public static final Uri callLogUri = CallLog.Calls.CONTENT_URI;
	
	public static final String[] callLogTypes = new String[]{
			 CallLog.Calls.NUMBER, 
			 CallLog.Calls.DURATION, 
			 CallLog.Calls.CACHED_NAME,
			 CallLog.Calls.DATE,
			 CallLog.Calls.TYPE};
	
	
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
		for (int i=0; i<array.length(); i++) {
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
