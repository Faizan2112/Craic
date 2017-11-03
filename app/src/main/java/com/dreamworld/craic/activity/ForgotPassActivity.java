package com.dreamworld.craic.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {
TextInputLayout mTEmail ;
EditText mEmail ;
    Button mBEmail;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
    findid();
    
    }

    private void findid() {
    mTEmail = (TextInputLayout) findViewById(R.id.forget_passti);
     mEmail = (EditText) findViewById(R.id.forget_pass);
  mBEmail = (Button)findViewById(R.id.forget_pass_btn);
mBEmail.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        StringRequest sendEmail = new StringRequest(Request.Method.POST, Config.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
            String emailS = mEmail.getText().toString().trim();
                Map<String,String> email = new HashMap<>();
                email.put("email",emailS);

                return email;
            }
        };
        RequestQueue req = Volley.newRequestQueue(this);
        req.add(sendEmail);

    }

}
