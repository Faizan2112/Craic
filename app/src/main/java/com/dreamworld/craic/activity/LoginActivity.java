package com.dreamworld.craic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dreamworld.craic.R;
import com.dreamworld.craic.configuration.Config;
import com.dreamworld.craic.configuration.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mEmail, mPassword;
    TextInputLayout mTEmail, mTPassword;
    Button mRegisterActivity, mLogin;
    TextView mForgotPass;
    private String email;
    private String password;
    public static SharedPreferences savedeatail;
    public static boolean loggedin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findids();
        checkSharePreference();
    }

    private void checkSharePreference() {

        savedeatail = getSharedPreferences(Constants.LoginSharePreferenceName, MODE_PRIVATE);
        String shareEmail = savedeatail.getString(Constants.LoginSharePreferenceEmail, email);
        boolean islogin = savedeatail.getBoolean(Constants.LoginSharePreferenceLogValue, loggedin);
        if (islogin) {
            Intent gotoMain = new Intent(getApplicationContext(), HomeActivity.class);
            gotoMain.putExtra(Constants.LoginSharePreferenceEmail, shareEmail);
            startActivity(gotoMain);
            finish();

        }

    }


    private void findids() {

        mEmail = (EditText) findViewById(R.id.login_email);
        mPassword = (EditText) findViewById(R.id.login_pass);
        mTEmail = (TextInputLayout) findViewById(R.id.login_emailti);
        mTPassword = (TextInputLayout) findViewById(R.id.login_passti);
        mRegisterActivity = (Button) findViewById(R.id.login_sign_up);
        mLogin = (Button) findViewById(R.id.login_login);
        mForgotPass = (TextView) findViewById(R.id.login_forgetpass);

        mRegisterActivity.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mForgotPass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int loginId = v.getId();
        switch (loginId) {
            case R.id.login_login:
                performLogin();

                break;

            case R.id.login_sign_up:
                Intent toSignUp = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(toSignUp);

                break;

            case R.id.login_forgetpass:
                Intent toForget = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(toForget);

                break;


        }
    }

    private void performLogin() {
        final StringRequest loginUser = new StringRequest(Request.Method.POST, Config.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    storeDetails(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                email = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                Map<String, String> register = new HashMap<>();
                register.put("email", email);
                register.put("password", password);

                return register;
            }
        };

        RequestQueue sendLdata = Volley.newRequestQueue(this);
        sendLdata.add(loginUser);
    }

    private void storeDetails(String response) throws JSONException {
        JSONObject loginData = new JSONObject(response);
        JSONArray loginArray = loginData.getJSONArray(Constants.keyresultData);
        JSONObject getlog = loginArray.getJSONObject(0);
        String mEmail = getlog.getString(Constants.LoginSharePreferenceemailkey);
        int mVerified = getlog.getInt(Constants.keyverified);
        String mFriendlist = getlog.getString(Constants.keyfriendlist);
        String mLikeList = getlog.getString(Constants.keylikelist);
        String mPosts = getlog.getString(Constants.keyposts);
        String mStatus = getlog.getString(Constants.keystatus);
        String mProfilePic = getlog.getString(Constants.keyprofilepic);
        String mFirstName = getlog.getString(Constants.keyfirstname);
        String mLastname = getlog.getString(Constants.keylastname);


        // String mPostIcon = getlog.getString(Constants.keypost_icon);
        //      mEmail = loginArray.getJSONObject(0);
        if (mStatus.equals(Constants.keyresult)) {
            loggedin = true;
            savedeatail = getSharedPreferences(Constants.LoginSharePreferenceName, MODE_PRIVATE);
            SharedPreferences.Editor editlogin = savedeatail.edit();
            editlogin.putString(Constants.LoginSharePreferenceEmail, mEmail);
            editlogin.putInt(Constants.LoginSharePreferenceverified, mVerified);
            editlogin.putString(Constants.LoginSharePreferencefriendlist, mFriendlist);
            editlogin.putString(Constants.LoginSharePreferencelikelist, mLikeList);
            editlogin.putString(Constants.LoginSharePreferenceposts, mPosts);
            editlogin.putString(Constants.LoginSharePreferenceProfilePic,mProfilePic);
            editlogin.putString(Constants.LoginSharePreferenceFirstname,mFirstName);
            editlogin.putString(Constants.LoginSharePreferenceLastname,mLastname);

            //      editlogin.putString(Constants.LoginSharePreferenceposts_icon, mPostIcon);
            editlogin.putBoolean(Constants.LoginSharePreferenceLogValue, loggedin);
            editlogin.commit();

            Intent gotoMain = new Intent(LoginActivity.this, HomeActivity.class);
            //   gotoMain.putExtra("email",shareEmail);
            startActivity(gotoMain);
            finish();


        }

    }


}
