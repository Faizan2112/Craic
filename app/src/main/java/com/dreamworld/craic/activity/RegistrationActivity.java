package com.dreamworld.craic.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mEmail, mPassword, mCheckPassword;
    TextInputLayout mTEmail, mTPassword, mTCheckPassword;
    Button mRegister;
    private String email;
    private String password;
    private String cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findId();


    }

    private void findId() {
        mEmail = (EditText) findViewById(R.id.reg_email);
        mPassword = (EditText) findViewById(R.id.reg_pass);
        mCheckPassword = (EditText) findViewById(R.id.reg_con_pass);
        mRegister = (Button) findViewById(R.id.reg_send);
        mTEmail = (TextInputLayout) findViewById(R.id.reg_emailti);
        mTPassword = (TextInputLayout) findViewById(R.id.reg_passti);
        mTCheckPassword = (TextInputLayout) findViewById(R.id.reg_con_passti);


        mRegister.setOnClickListener(this);

    }

    private boolean validate() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
       // return pattern.matcher(email).matches();

        boolean isValid = true;

         email = mEmail.getText().toString();
         password = mPassword.getText().toString();
        cpassword = mCheckPassword.getText().toString();

        if (email.isEmpty() )
        {
            mTEmail.setError("email can't be empty");
            mTEmail.clearFocus();
            isValid = false ;
        }else if(!pattern.matcher(email).matches())
        {
            mTEmail.setError("enter valid email ");
            isValid = false;

        }
        else
        {
            mTEmail.setError(null);
            mTEmail.clearFocus();

        }

        if(password.isEmpty() )
        {
            mTPassword.setError("password can't be empty");
            isValid = false ;

        }
        else
        {
            mTPassword.setError(null);
            mTPassword.clearFocus();

        }

        if(cpassword.isEmpty() )
        {

            mTCheckPassword.setError(" confirm password can't be empty");
            isValid = false ;
        }
        else
        {
            mTCheckPassword.setError(null);
            mTCheckPassword.clearFocus();

        }

         if(!password.equals(cpassword))
        {
            mTCheckPassword.setError("password do not match");
            isValid = false ;

        }  else
         {
             mTCheckPassword.setError(null);
             mTCheckPassword.clearFocus();

         }



    return isValid ;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.reg_send:

                sendData();

               break;


        }
    }

    private void sendData() {

        if(validate())
        {
            makeVolleyCall();

        }

    }

    private void makeVolleyCall() {
        final StringRequest registerUser = new StringRequest(Request.Method.POST, Config.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                gotoLogin(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> register = new HashMap<>();
                register.put("email",email);
                register.put("password",password);

                return register;
            }
        };

        RequestQueue sendRdata = Volley.newRequestQueue(this);
        sendRdata.add(registerUser);
    }

    private void gotoLogin(String response) {
    if(response.equals("success"))
    {
        Intent gotoLogin = new Intent(RegistrationActivity.this ,LoginActivity.class);
        startActivity(gotoLogin);
        finish();

    }

    }
}


