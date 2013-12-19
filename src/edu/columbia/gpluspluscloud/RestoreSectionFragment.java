package edu.columbia.gpluspluscloud;

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

				Toast.makeText(getActivity(), result.toString(),
						Toast.LENGTH_LONG).show();
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
    }
}
