package edu.columbia.gpluspluscloud;


import java.util.Arrays;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import edu.columbia.gpluspluscloud.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class LoginActivity extends Activity {
	private TextView usernameText;
	private LoginButton authButton;
	private UiLifecycleHelper uiHelper;
	private GraphUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		 usernameText = (TextView) findViewById(R.id.username_text);	
		 usernameText.setText(null);
		 authButton = (LoginButton) findViewById(R.id.login_button);
		 
		// set permission list
		authButton.setReadPermissions(Arrays.asList("basic_info"));
		authButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                LoginActivity.this.user = user;
                
                tryJumpToActivity();                
            }
        });
		
		
	}
	
	private void tryJumpToActivity() {
		Session session = Session.getActiveSession();
		boolean enableButtons = (session != null && session.isOpened());
		if (enableButtons && user != null) {
			usernameText.setText("Hello," + user.getFirstName());
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.putExtra("username", user.getFirstName());
			intent.putExtra("userId", user.getId());
			intent.putExtra("session", session);
			startActivity(intent);
		} else {
			usernameText.setText(null);
		}
		
	}
	
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
           // onSessionStateChange(session, state, exception);
        }
    };
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	
	
}
