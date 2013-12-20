package edu.columbia.gpluspluscloud;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A Dashboard fragment representing a section of the app, 
 */
public class DashboardSectionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public DashboardSectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_dashboard, container, false);
        Activity currentActivity = getActivity();
        String username = currentActivity.getIntent().getStringExtra("username");
        
        TextView usernameTextView = (TextView) rootView.findViewById(R.id.username_label);       
        usernameTextView.setText("Username:\t\t" + username);
        
        TextView statusTextView = (TextView) rootView.findViewById(R.id.status_label);
        statusTextView.setText("Next backup runs on....!");
      
        
               
        return rootView;
    }
}
