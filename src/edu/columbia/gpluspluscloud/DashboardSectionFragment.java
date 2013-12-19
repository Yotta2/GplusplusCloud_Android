package edu.columbia.gpluspluscloud;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Dashboard!");
        
        TextView statusTextView = (TextView) rootView.findViewById(R.id.status_label);
        statusTextView.setText("Next backup runs on....!");
        
        return rootView;
    }
}
