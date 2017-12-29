package com.dreamworld.craic.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.dreamworld.craic.model.PostDetail;
import com.dreamworld.craic.networkcheck.NetworkUtill;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog mPDialog;

    TextInputLayout mTTitel, mTSubTitel, mTSummary, mTDescription, mTConcution;
    EditText mTitle, mSubtitle, mSummary, mDescription, mConclution;
    RadioGroup mScope;
    Button mSend;
    SharedPreferences globalLogindeatail;
    boolean checkData;
    private int pri;
    private int pub;
    private RadioButton radioButton;
    private static String mPrivacyData;
    private boolean radbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        initializeview();
        //  checkValiddata();
    }

    private boolean checkValiddata() {
        String title = mTitle.getText().toString();

        String summary = mSummary.getText().toString();
        String description = mDescription.getText().toString();
//        String conclution = mConclution.getText().toString();

        checkData = true;

        if (title.isEmpty()) {
            mTTitel.setError("This field is required");
            checkData = false;
        } else if (title.length() > 25) {

            mTTitel.setError("Caracter exeeded by 25");
            checkData = false;
        } else {
            mTTitel.setError(null);
            mTTitel.clearFocus();

        }
        if (summary.isEmpty()) {
            mTSummary.setError("This field is required");
            checkData = false;
        } else if (summary.length() > 100) {

            mTSummary.setError("Caracter exeeded by 100");
            checkData = false;
        } else {
            mTSummary.setError(null);
            mTSummary.clearFocus();

        }
        if (description.isEmpty()) {
            mTDescription.setError("This field is required");
            checkData = false;
        } else if (description.length() > 300) {

            mTDescription.setError("Caracter exeeded by 300");
            checkData = false;
        } else {
            mTDescription.setError(null);
            mTDescription.clearFocus();

        }
    /*    if (conclution.isEmpty()) {
            mTConcution.setError("This field is required");
            checkData = false;
        } else if (conclution.length() > 50) {

            mTConcution.setError("CHaracter exeeded by 50");
            checkData = false;
        } else {
            mTConcution.setError(null);
            mTConcution.clearFocus();

        }
*/
        if (radbtn == false)
        {
            checkData = false ;
            // hurray at-least on radio button is checked.
        }/*
        else
        {
            checkData = false ;
           // radioButton.setError("select any one field");
            // pls select at-least one radio button.. since id is -1 means no button is check
        }*/



              /*  switch (checkedId)

            {
                case R.id.upload_private:
                    pri = 0;
                    break;

                case R.id.upload_public:
                    pub = 1 ;
                    break;
            }
            checkData = false ;
            */



        return checkData;
    }


    private void initializeview() {
        mTTitel = (TextInputLayout) findViewById(R.id.upload_titleti);
       // mTSubTitel = (TextInputLayout) findViewById(R.id.upload_subtitleti);
        mTSummary = (TextInputLayout) findViewById(R.id.upload_summaryti);
        mTDescription = (TextInputLayout) findViewById(R.id.upload_descriptionti);
      //  mTConcution = (TextInputLayout) findViewById(R.id.upload_conclutionti);

        mTitle = (EditText) findViewById(R.id.upload_title);

        //mSubtitle = (EditText) findViewById(R.id.upload_subtitle);

        mSummary = (EditText) findViewById(R.id.upload_summary);

        mDescription = (EditText) findViewById(R.id.upload_description);

        //mConclution = (EditText) findViewById(R.id.upload_conclution);
        mScope = (RadioGroup)findViewById(R.id.upload_radio_group);
        mScope.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);

               // Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                 if(radioButton.isChecked())
                 {
                     Toast.makeText(getApplicationContext(), radioButton.getText()+"is checked", Toast.LENGTH_SHORT).show();
                     mPrivacyData = radioButton.getText().toString() ;
                     radbtn = true ;

                 }
                 else
                 {
                     radbtn = false ;

                 }

            }
        });

        /*  mScope.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                checkData = false ;
                radioButton = (RadioButton) findViewById(checkedId);

                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                if(radioButton.isChecked()) {

                    mPrivacyData = radioButton.getText().toString();

                }
                else
                {
                    radioButton.setError("select one item");


                }});*/

                mSend = (Button) findViewById(R.id.upload_art);
        mSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int conn3 = NetworkUtill.getConnectivityStatus(getApplicationContext());
        if (conn3 == NetworkUtill.TYPE_WIFI || conn3 == NetworkUtill.TYPE_MOBILE) {

            if (checkValiddata()) {
                sendData();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please turn on Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }

    private void sendData() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        final String mdate = day + "/" + month + "/" + year;
        Random r = new Random();
        int num = r.nextInt(1000000000 - 2 + 1) + 2;

        globalLogindeatail = getSharedPreferences(Constants.LoginSharePreferenceName, MODE_PRIVATE);
        final String posts_id = String.valueOf(num);
        final String posts_icon = globalLogindeatail.getString(Constants.LoginSharePreferenceProfilePic, Constants.posts_profilepic);
        final String date = mdate;
        final String titel = mTitle.getText().toString();
       // final String subtitle = mSubtitle.getText().toString();
        final String summary = mSummary.getText().toString();
        final String description = mDescription.getText().toString();
      //
        //  final String conclution = mConclution.getText().toString();
        final int viewtype = PostDetail.TEXT_TYPE;
        final String posts = globalLogindeatail.getString(Constants.LoginSharePreferenceposts, Constants.posts_posts);
        mPDialog = new ProgressDialog(this);
        mPDialog.setIndeterminate(true);
        mPDialog.setMessage("Posting data");
        mPDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mPDialog.setCancelable(false);
        mPDialog.show();

        StringRequest sendSuggestion = new StringRequest(Request.Method.POST, Constants.Post_data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "" + response.toString(), Toast.LENGTH_SHORT).show();
                mPDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> sendData = new HashMap<>();
                sendData.put(Constants.posts_posts, posts);
                sendData.put(Constants.posts_profilepic, posts_icon);
                sendData.put(Constants.posts_date, date);
                sendData.put(Constants.posts_titel, titel);
       //         sendData.put(Constants.posts_subtitle, subtitle);
                sendData.put(Constants.posts_post_id, posts_id);
                sendData.put(Constants.posts_articlesummary, summary);
                sendData.put(Constants.posts_articledescription, description);
          //      sendData.put(Constants.posts_articleconclution, conclution);
                sendData.put(Constants.posts_privacy,mPrivacyData);
                // sendData.put(Constants.posts_posts,posts);
                sendData.put(Constants.posts_viewtype, String.valueOf(viewtype));
                return sendData;
            }
        };
        RequestQueue suggRequest = Volley.newRequestQueue(this);
        suggRequest.add(sendSuggestion);
    }

}

