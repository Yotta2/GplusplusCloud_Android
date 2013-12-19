package edu.columbia.gpluspluscloud;

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

				Toast.makeText(getActivity(), result.toString(),
						Toast.LENGTH_LONG).show();
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
    }
}