package com.dreamworld.craic.activity;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.dreamworld.craic.networkcheck.NetworkUtill;

import java.util.HashMap;
import java.util.Map;

public class SuggestionActivity extends AppCompatActivity implements View.OnClickListener {
ImageButton mSendSuggestion ;
EditText mSugName , mSugDetail ;
    TextInputLayout mTIsugname , mTIsugdetail;
    ProgressDialog mPDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        mSugName = (EditText) findViewById(R.id.suggestions_name);
        mSugDetail = (EditText) findViewById(R.id.suggestions_sugg);
        mTIsugname = (TextInputLayout) findViewById(R.id.suggestions_nameti) ;
        mTIsugdetail=(TextInputLayout) findViewById(R.id.suggestions_suggti);
       // mSendSuggestion = (Button) findViewById(R.id.suggestions_send);
        mSendSuggestion = (ImageButton) findViewById(R.id.suggestions_send);
        mSendSuggestion.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String  suggname =     mSugName.getText().toString().trim();
        String suggdetail = mSugDetail.getText().toString().trim();


        if(suggname.isEmpty())
        {
            mTIsugname.setError("Fill your name");
        }else {
            if (suggdetail.isEmpty())
            {
                mTIsugdetail.setError("Fill your suggestion");

            }else{
                int conn3 = NetworkUtill.getConnectivityStatus(getApplicationContext());
                if(conn3 == NetworkUtill.TYPE_WIFI || conn3 == NetworkUtill.TYPE_MOBILE) {
                    sendData();
                }else
                {
                    Toast.makeText(getApplicationContext(),"Please turn on Internet Connection" ,Toast.LENGTH_SHORT).show();

                }
            }}

        }

    private void sendData() {
        final String  suggname2 =     mSugName.getText().toString().trim();
        final String suggdetail2 = mSugDetail.getText().toString().trim();
        mPDialog = new ProgressDialog(this);
        mPDialog.setIndeterminate(true);
        mPDialog.setMessage("Posting data");
        mPDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mPDialog.setCancelable(false);
        mPDialog.show();

        StringRequest sendSuggestion = new StringRequest(Request.Method.POST, Config.SUGGESTION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),""+response.toString(),Toast.LENGTH_SHORT).show();
           mPDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> sendData = new HashMap<>();
                sendData.put("name",suggname2);
                sendData.put("suggestion",suggdetail2);

                return sendData;
            }
        };
        RequestQueue suggRequest = Volley.newRequestQueue(this);
        suggRequest.add(sendSuggestion);
    }
    }



