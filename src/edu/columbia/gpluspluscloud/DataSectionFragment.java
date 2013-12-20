package edu.columbia.gpluspluscloud;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A Data fragment representing a section of the app,
 */
public class DataSectionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    TextView messageText;
    private CheckBox callLogCheckBox, pictureCheckBox;
    private Button backupButton;
    private View rootView;
    
    public DataSectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_data, container, false);
        
        TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
        //dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        dummyTextView.setText("");
        addListenerOnButton();
        return rootView;
    }
    
	public void addListenerOnButton() {

		callLogCheckBox = (CheckBox) rootView.findViewById(R.id.call_log_data);
		pictureCheckBox = (CheckBox) rootView.findViewById(R.id.picture_data);
		backupButton = (Button) rootView.findViewById(R.id.backup_button);
		backupButton.setOnClickListener(new OnClickListener() {

			// Run when button is clicked
			@Override
			public void onClick(View v) {

				StringBuffer result = new StringBuffer();
				result.append("callLogCheckBox check : ").append(
						callLogCheckBox.isChecked());
				result.append("\n pictureCheckBox check :").append(
						pictureCheckBox.isChecked());

				TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		        dummyTextView.setText("Status: backing up!");
		        backup();
		        dummyTextView.setText("Status: backup finished!");
			}
		});

	}
    
    public void backup() {
    	TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
        dummyTextView.setText("Status: ***");
        // TODO
        
        // backup call log
        if (callLogCheckBox.isChecked()) {
        	backupCallLog();
        }
        if (pictureCheckBox.isChecked()) {
        	backupPicture();
        }
        
    }
    
    public void backupPicture() {
    	
    	new Thread(new Runnable() {
    		
            public void run() {
            //	Dialog dialog = ProgressDialog.show(getActivity(), "", "Uploading file...", true);                 	
            	boolean flag = FileTransfer.backUpAllFilesUnderOneDirectory(Helper.picturePath);
            	if(flag==false){
            		//messageText.setText("Got Exception : see logcat ");
            		getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Got Exception : see logcat ", 
                                    Toast.LENGTH_SHORT).show();
                        }
                    });      
                    
            	}
                                          
            }
          }).start();     
}
    	
    	
    
    
    
    public void writeToTmpFile(String path, String content) throws IOException {
    		File file = new File(path); 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();	
    }
    
    public void backupCallLog() {
    	// get json array of call log
    	 JSONArray callArray = Helper.getContentFromProvider(getActivity().getContentResolver(), Helper.callLogUri, Helper.callLogTypes);
         
    	 // write to tmp file
    	 try {
			writeToTmpFile(Helper.tmpCallLogPath, callArray.toString());
			// backup() ...
			new Thread(new Runnable() {
	            public void run() {
	                                              	
	            	int responsecode = 
	            			FileTransfer.uploadFile(Helper.tmpCallLogPath);;
	            	if(responsecode!=200){
	            		//messageText.setText("Got Exception : see logcat ");
	            		getActivity().runOnUiThread(new Runnable() {
	                        public void run() {
	                            Toast.makeText(getActivity(), "Got Exception : see logcat ", 
	                                    Toast.LENGTH_SHORT).show();
	                        }
	                    });      
	                    
	            	}
	            	else{
	            		getActivity().runOnUiThread(new Runnable() {
	                        public void run() {
	                            Toast.makeText(getActivity(), "Call Log back up Succeed ", 
	                                    Toast.LENGTH_SHORT).show();
	                        }
	                    });  
	            	}
	                                          
	            }
	          }).start();    
			
			
			// delete tmp file
			new File(Helper.tmpCallLogPath).delete();		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    }
}