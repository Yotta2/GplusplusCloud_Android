package edu.columbia.gpluspluscloud;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

public class FileTransfer {
	 static int serverResponseCode = 0;
	// final String directory = "/mnt/sdcard/DCIM/Camera/";
	 static String ip = Helper.serverIp;
	// final String fetchDirectory = "/mnt/sdcard/DCIM/Camera/";
	/*
	 * create url according to user_id, path, status and function 
	 */
	public static String requestUrl(String user_id, String path,String status, String function ){
    	path = path.substring(1, path.length());
    	String url = "";
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String timeStamp = dateFormat.format(date);
		System.out.println(timeStamp);
		if(function.equals("upload")){
			url = "http://"+ip+":8080/gppcloudbackend/upload_files?user_id="+user_id+"&path="+path+"&timestamp="+timeStamp+"&status="+status;
		}
		if(function.equals("fetch")){
    	    url = "http://"+ip+":8080/gppcloudbackend/download_files?user_id=Mickey&path="+path;
		}
		if(function.equals("checkstatus")){
			url = "http://"+ip+":8080/gppcloudbackend/get_backup_info?user_id=Mickey&&dir="+path;
		}
    	url = url.replaceAll(" ", "%20");
    	return url; 
    }
	/*
     * upload file
     */
    
   public static int uploadFile(String sourceFileUri) {
           
           
          String fileName = sourceFileUri;
          Log.i("sourceurl", sourceFileUri);
          HttpURLConnection conn = null;
          DataOutputStream dos = null;  
          String lineEnd = "\r\n";
          String twoHyphens = "--";
          String boundary = "*****";
          int bytesRead, bytesAvailable, bufferSize;
          byte[] buffer;
          int maxBufferSize = 1 * 1024 * 1024; 
          File sourceFile = new File(sourceFileUri); 
           
          if (!sourceFile.isFile()) {
               
            //   dialog.dismiss(); 
                
               Log.e("uploadFile", "Source File not exist :"
                                   +sourceFileUri);
                
          /*     runOnUiThread(new Runnable() {
                   public void run() {
                       messageText.setText("Source File not exist :"
                               +"sourceFileUri");
                   }
               }); */
                
               return 0;
            
          }
          else
          {
               try { 
            	   String upLoadServerUri = requestUrl("Mickey",sourceFileUri, "update","upload") ;
                     // open a URL connection to the Servlet
                   FileInputStream fileInputStream = new FileInputStream(sourceFile);
                   URL url = new URL(upLoadServerUri);
                    
                   // Open a HTTP  connection to  the URL
                   conn = (HttpURLConnection) url.openConnection(); 
                   conn.setDoInput(true); // Allow Inputs
                   conn.setDoOutput(true); // Allow Outputs
                   conn.setUseCaches(false); // Don't use a Cached Copy
                   conn.setRequestMethod("POST");
                   conn.setRequestProperty("Connection", "Keep-Alive");
                   conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                   conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                   conn.setRequestProperty("uploaded_file", fileName); 
                    
                   dos = new DataOutputStream(conn.getOutputStream());
          
                   dos.writeBytes(twoHyphens + boundary + lineEnd); 
                
                //   dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
               //            + uploadFileName + "\"" + lineEnd);
                   dos.writeBytes("Content-Disposition: form-data; name=\""
                                       + sourceFileUri + "\"" + lineEnd);
                   dos.writeBytes(lineEnd);
          
                   // create a buffer of  maximum size
                   bytesAvailable = fileInputStream.available(); 
          
                   bufferSize = Math.min(bytesAvailable, maxBufferSize);
                   buffer = new byte[bufferSize];
          
                   // read file and write it into form...
                   bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                      
                   while (bytesRead > 0) {
                        
                     dos.write(buffer, 0, bufferSize);
                     bytesAvailable = fileInputStream.available();
                     bufferSize = Math.min(bytesAvailable, maxBufferSize);
                     bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                      
                    }
          
                   // send multipart form data necesssary after file data...
                   dos.writeBytes(lineEnd);
                   dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
          
                   // Responses from the server (code and message)
                   serverResponseCode = conn.getResponseCode();
                   String serverResponseMessage = conn.getResponseMessage();
                     
                   Log.i("uploadFile", "HTTP Response is : "
                           + serverResponseMessage + ": " + serverResponseCode);
                    
                   if(serverResponseCode == 200){
                        
              /*         runOnUiThread(new Runnable() {
                            public void run() {
                                 
                                String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                              +" http://www.androidexample.com/media/uploads/"
                                              +"";
                                 
                        //        messageText.setText(msg);
                         //       Toast.makeText(UploadToServer.this, "File Upload Complete.", 
                                     //        Toast.LENGTH_SHORT).show();
                            }
                        });  */              
                   }    
                    
                   //close the streams //
                   fileInputStream.close();
                   dos.flush();
                   dos.close();
                     
              } catch (MalformedURLException ex) {
                   
         //         dialog.dismiss();  
                  ex.printStackTrace();
                   
              //    runOnUiThread(new Runnable() {
                //      public void run() {
                  //        messageText.setText("MalformedURLException Exception : check script url.");
                   //       Toast.makeText(FileTransfer.this, "MalformedURLException", 
                    //                                          Toast.LENGTH_SHORT).show();
                  //    }
                  //});
                   
                  Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
              } catch (Exception e) {
                   
       //           dialog.dismiss();  
                  e.printStackTrace();
                   
         //         runOnUiThread(new Runnable() {
         /*             public void run() {
                          messageText.setText("Got Exception : see logcat ");
                          Toast.makeText(UploadToServer.this, "Got Exception : see logcat ", 
                                  Toast.LENGTH_SHORT).show();
                      }
                  });
                  */
                  Log.e("Upload file to server Exception", "Exception : "
                                                   + e.getMessage(), e);  
              }
       //       dialog.dismiss();       
              return serverResponseCode; 
               
           } // End else block 
         } 
 /*
  *    get the files needed to restore
  */
    public static ArrayList<String> checkBackUpStatus(String diretoryToResotre) {
    	ArrayList<String> fileforbackupList = new ArrayList<String>();
        Log.i("enter checkbackup status", "success");
        
         try {       
        	 DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
        	 String checkBackUpstatusUri = requestUrl("Mickey", diretoryToResotre, "", "checkstatus");
        		    
        	 HttpPost httppost = new HttpPost(checkBackUpstatusUri);
        	 Log.i("url:",checkBackUpstatusUri);
        	 httppost.setHeader("Content-type", "application/json");

        	 InputStream inputStream = null;
        	 String result = null;
        	 try {
        	     HttpResponse response = httpclient.execute(httppost);           
        	     HttpEntity entity = response.getEntity();
        	     if (response.getStatusLine().getStatusCode() == 200) {
        	/*    	 runOnUiThread(new Runnable() {
                         public void run() {
                              
                             String msg = "check status finish: \n\n";
                              
                             messageText.setText(msg);
                             Toast.makeText(UploadToServer.this, "check status finished.", 
                                          Toast.LENGTH_SHORT).show();
                         }
                     });   */  
                 }

        	     inputStream = entity.getContent();
        	     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
        	     StringBuilder sb = new StringBuilder();

        	     String line = null;
        	     while ((line = reader.readLine()) != null)
        	     {
        	         sb.append(line + "\n");
        	     }
        	     result = sb.toString();
        	 } catch (Exception e) { 
        	     // Oops
        	 }
        	 finally {
        	     try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        	 }   
        	 JSONObject jObject = new JSONObject(result);
        	 Log.i("aJsonString", jObject.toString());
        	 String user_id = jObject.getString("UserID");
        	 String fileList = jObject.getString("FileList");
        	// JSONObject jObject2 = new JSONObject(fileList);
        	 JSONArray filearray = jObject.getJSONArray("FileList");
        	 //Iterator iterator = filearray.
        	 for(int i = 0; i<filearray.length();i++){
        		 JSONObject jb = filearray.getJSONObject(i);
        		 String filename = jb.getString("File");
        		 fileforbackupList.add(filename);
        	 }
        	 Log.i("user_id",user_id);
        	 Log.i("path",fileList);    
            } catch (Exception e) {
                 
       //         dialog.dismiss();  
                e.printStackTrace();
                 
           /*     runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(UploadToServer.this, "Got Exception : see logcat ", 
                                Toast.LENGTH_SHORT).show();
                    }
                });*/
                Log.e("Upload file to server Exception", "Exception : "+ e.getMessage(), e);  
            }
      //      dialog.dismiss();       
         //   return serverResponseCode; 
            return fileforbackupList;
             
         } // End else block
    /*
     * upload all files under one directory
     */
    public static boolean backUpAllFilesUnderOneDirectory(String directory){
    	File folder = new File(directory);
    	Log.i("backupfiledirectory",directory);
    	File[] listOfFiles = folder.listFiles();
    	String filename;
    	String uploadUri;
    	boolean flag = true;
    	for(int i = 0; i < listOfFiles.length; i++){
    		if(listOfFiles[i].isFile()){
    			filename = listOfFiles[i].getName();
    			Log.i("filename", filename);
    			int responseCode = uploadFile(directory+""+filename);
    			if(responseCode!=200){
    				flag = false;
    				
    			}
    		}
    		
    	}
    	return flag;
    }
    /*
     * fetch all files under one directory
     */
    public static boolean fetchAllFilesUnderOneDirectory(String fetchDirectory){
    	ArrayList<String> fileNames = checkBackUpStatus(fetchDirectory);
    	boolean succeed = true;
    	for(String filename:fileNames){
    		String filePathfileName = fetchDirectory+filename;
    		int responsecode = fetchfile(filePathfileName);
    		//String urlForFetchEachFile = 
    		if(responsecode != 200){
    			succeed = false;
    		}
    		
    	}
    	return succeed;
    }
    public static int fetchfile(String fetchfilename){
    	 Log.i("enter fetch file", "success");
  	     try { 
  	    	
			DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
			String fetchUri = requestUrl("Mickey", fetchfilename, "", "fetch");
  	    	 HttpPost httppost = new HttpPost(fetchUri);
  	    	 // Depends on your web service
  	    	 Log.i("url:",fetchUri);
  	    	 httppost.setHeader("Content-type", "application/json");

  	    	 InputStream inputStream = null;
  	    	 String result = null;
  	    	 try {
  	    	     HttpResponse response = httpclient.execute(httppost);           
  	    	     HttpEntity entity = response.getEntity();
  	    	//   Log.i("responsecode",response.getStatusLine().getStatusCode());
  	    	     if (response.getStatusLine().getStatusCode() == 200) {
  	    	    	// Log.i("responsecode",response.getStatusLine().getStatusCode());
  	    	   /* 	 runOnUiThread(new Runnable() {
  	/*                     public void run() {
  	                          
  	                         String msg = "fetch file finish: \n\n";
  	                          
  	                         messageText.setText(msg);
  	                         Toast.makeText(UploadToServer.this, "fetch file finished.", 
  	                                      Toast.LENGTH_SHORT).show();
  	                     }
  	                 });  */  
  	             }
  	    	   serverResponseCode = response.getStatusLine().getStatusCode();
  	    	     inputStream = entity.getContent();
  	    	     byte[] bytes = IOUtils.toByteArray(inputStream);
  	    	     // json is UTF-8 by default
  	    	     String filedownloadpath = fetchfilename;
  	    	     File filedownload = new File(filedownloadpath);
  	    	     if(!filedownload.exists()){
  	    	    	 filedownload.createNewFile();
  	    	     }
  	    	     FileOutputStream output = new FileOutputStream(filedownload);
  	    	     IOUtils.write(bytes, output);

  	    	     
  	    	 } catch (Exception e) { 
  	    	     // Oops
  	    		 e.printStackTrace();
  	    	 }
  	    	 finally {
  	    	     try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
  	    	 }   
  	 
  	        } catch (Exception e) {
  	             
  	       //     dialog.dismiss();  
  	            e.printStackTrace();
  	             
  	     /*       runOnUiThread(new Runnable() {
  	                public void run() {
  	                    messageText.setText("Got Exception : see logcat ");
  	                    Toast.makeText(UploadToServer.this, "Got Exception : see logcat ", 
  	                            Toast.LENGTH_SHORT).show();
  	                }
  	            });*/
  	            Log.e("Upload file to server Exception", "Exception : "
  	                                             + e.getMessage(), e);  
  	        }
  	    //    dialog.dismiss();       
  	        return serverResponseCode; 
    	
    }
}
