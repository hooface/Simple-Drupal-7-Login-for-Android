package net.iplanet.simpledrupal7login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.iplanet.drupal.v7.Authentication;
import net.iplanet.drupal.v7.Tokens;
import net.iplanet.utils.Logger;

import org.apache.http.HttpResponse;

public class MainActivity extends AppCompatActivity {

    Logger Log = new Logger();
    public String session_name;
    public String session_id;
    public String token;
    public String login;
    private TextView mLogin;
    private TextView mSessionName;
    private TextView mSessionId;
    private TextView mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();

        //region GET SESSION TOKENS
        if (extras != null) {
            session_name  = extras.getString(Tokens.KEY_SESSION_NAME_RESPONSE);
            session_id  = extras.getString(Tokens.KEY_SESSION_ID_RESPONSE);
            token  = extras.getString(Tokens.KEY_TOKEN_RESPONSE);
            login  = extras.getString(Tokens.KEY_LOGIN);
        }

        mLogin = (TextView) findViewById(R.id.txtUser);
        mSessionName = (TextView) findViewById(R.id.mTxtSessionName);
        mSessionId = (TextView) findViewById(R.id.mTxtSessionId);
        mToken = (TextView) findViewById(R.id.mTxtToken);

        mSessionName.setText(session_name);
        mSessionId.setText(session_id);
        mToken.setText(token);
        mLogin.setText(login);
    }

    public void logout_click(View view){
        try {
            new logOutTask().execute();
        }catch (Exception ex) {
            Log.SyslogException(ex);
        }
    }

    private class logOutTask extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... params) {
            try {
                Authentication Auth = new  Authentication();
                HttpResponse response = Auth.doLogout(session_name, session_id, token);
            }catch (Exception ex) {
                Log.SyslogException(ex);
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
