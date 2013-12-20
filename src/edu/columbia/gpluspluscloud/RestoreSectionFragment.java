package edu.columbia.gpluspluscloud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class RestoreSectionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    private CheckBox callLogCheckBox, pictureCheckBox;
    private Button restoreButton;
    private View rootView;
    
    public RestoreSectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_restore, container, false);
        		
        TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
        //dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        dummyTextView.setText("");
        addListenerOnButton();
        return rootView;
    }
    
	public void addListenerOnButton() {

		callLogCheckBox = (CheckBox) rootView.findViewById(R.id.call_log_data);
		pictureCheckBox = (CheckBox) rootView.findViewById(R.id.picture_data);
		restoreButton = (Button) rootView.findViewById(R.id.restore_button);
		restoreButton.setOnClickListener(new OnClickListener() {

			// Run when button is clicked
			@Override
			public void onClick(View v) {

				StringBuffer result = new StringBuffer();
				result.append("callLogCheckBox check : ").append(
						callLogCheckBox.isChecked());
				result.append("\n pictureCheckBox check :").append(
						pictureCheckBox.isChecked());

				TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		        dummyTextView.setText("Status: restoring!");
		        restore();
		        //dummyTextView.setText("Status: restore finished!");
			}
		});

	}
    
    public void restore() {
    	TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
        dummyTextView.setText("Status: ***");
        // TODO
        // restore call log
        if (callLogCheckBox.isChecked()) {
        	restoreCallLog();
        }
        if (pictureCheckBox.isChecked()) {
        	restorePicture();
        }
    }
    
    public void restoreCallLog() {
    	// restore and save to tmp file
    	// caiyarestore(Helper.tmpCallLogPath);
    	new Thread(new Runnable() {
            public void run() {
                                              	
            	String callLogFileName = Helper.tmpCallLogPath;
            	int responsecode = FileTransfer.fetchfile(callLogFileName);
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
                            Toast.makeText(getActivity(), "Restore Call Log succeed : congratulations", 
                                    Toast.LENGTH_SHORT).show();
                        }
                    });      
            	}
                                          
            }
          }).start();     
    
    	
    	// read from tmp file
    	String callLogContent = null;
		try {
			callLogContent = readFromTmpFile(Helper.tmpCallLogPath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	// insert call log to system
    	try {
			Helper.insertJSONArrayContent(getActivity().getContentResolver(), Helper.callLogUri, new JSONArray(callLogContent));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public String readFromTmpFile(String path) throws IOException {
    	File file = new File(path);
    	FileReader fr = new FileReader(file.getAbsoluteFile());
    	BufferedReader br = new BufferedReader(fr);
		String result = br.readLine();
		br.close();	
    	
    	file.delete();
    	
    	return result;
    }
    
    
    public void restorePicture() {
    	new Thread(new Runnable() {
            public void run() {
                                              	
            	String directory = Helper.picturePath;
            	boolean succeed = FileTransfer.fetchAllFilesUnderOneDirectory(directory);
            	if(succeed==false){
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
                            Toast.makeText(getActivity(), "Restore Picture succeed : congratulations", 
                                    Toast.LENGTH_SHORT).show();
                        }
                    });      
            	}
                                          
            }
          }).start();     
    	
    }
}
