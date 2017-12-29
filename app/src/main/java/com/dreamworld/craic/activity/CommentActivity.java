package com.dreamworld.craic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dreamworld.craic.R;
import com.dreamworld.craic.adapters.CommentAdapter;
import com.dreamworld.craic.adapters.PostDataAdapter;
import com.dreamworld.craic.classess.DividerItemDecoration;
import com.dreamworld.craic.configuration.CommentConfiguration;
import com.dreamworld.craic.configuration.Config;
import com.dreamworld.craic.configuration.Constants;
import com.dreamworld.craic.configuration.PostDataConfig;
import com.dreamworld.craic.model.CommentModel;
import com.dreamworld.craic.model.PostDetail;
import com.dreamworld.craic.networkcheck.NetworkUtill;
import com.dreamworld.craic.transformshape.CircleTransform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dreamworld.craic.activity.LoginActivity.savedeatail;
import static com.dreamworld.craic.configuration.CommentConfiguration.userComment;
import static com.dreamworld.craic.configuration.CommentConfiguration.userCommentDate;
import static com.dreamworld.craic.configuration.CommentConfiguration.userName;
import static com.dreamworld.craic.configuration.CommentConfiguration.userProfilePic;
import static com.dreamworld.craic.configuration.PostDataConfig.articleconclution;
import static com.dreamworld.craic.configuration.PostDataConfig.articledescription;
import static com.dreamworld.craic.configuration.PostDataConfig.articlesummary;
import static com.dreamworld.craic.configuration.PostDataConfig.comments;
import static com.dreamworld.craic.configuration.PostDataConfig.date;
import static com.dreamworld.craic.configuration.PostDataConfig.likes;
import static com.dreamworld.craic.configuration.PostDataConfig.mainimageurl;
import static com.dreamworld.craic.configuration.PostDataConfig.post_icon;
import static com.dreamworld.craic.configuration.PostDataConfig.post_id;
import static com.dreamworld.craic.configuration.PostDataConfig.posts;
import static com.dreamworld.craic.configuration.PostDataConfig.subtitle;
import static com.dreamworld.craic.configuration.PostDataConfig.titel;
import static com.dreamworld.craic.configuration.PostDataConfig.viewtype;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView commentRecyclerView;
    RecyclerView.LayoutManager commentLayoutManager;
    CommentConfiguration commentConfiguration;
    ImageView mUserImage;
    EditText mUserComment;
    Button mSendComment;
    private static String postIds;
    private static String mProfilePic;
    private static String profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        intialzeWidgets();
        loadVolleyData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        commentRecyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);

        commentRecyclerView.setLayoutManager(linearLayoutManager);
        commentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        Intent getPostId = getIntent();
        postIds = getPostId.getStringExtra("postid").toString();
        savedeatail = getSharedPreferences(Constants.LoginSharePreferenceName,MODE_PRIVATE);
        profile = savedeatail.getString(Constants.LoginSharePreferenceProfilePic,Constants.posts_profilepic);
        Glide.with(this).load(profile).transform(new CircleTransform(this)).into(mUserImage);
    }

    private void intialzeWidgets() {
        mUserImage = (ImageView) findViewById(R.id.comment_user_image);
        mUserComment = (EditText) findViewById(R.id.comment_user_comment);
        mSendComment = (Button) findViewById(R.id.comment_user_send);

        mSendComment.setText(postIds);
        mSendComment.setOnClickListener(this);
    }

    private void loadVolleyData() {
        StringRequest commentRequest = new StringRequest(Request.Method.POST, Constants.GET_All_COMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseCommentJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> sendData = new HashMap<>();
                sendData.put("postid","1690723");
//                sendData.put("suggestion",suggdetail2);

                return sendData;
            }
        }  ;
        RequestQueue getComment = Volley.newRequestQueue(this);
        getComment.add(commentRequest);


    }

    private void parseCommentJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray commentArrayLenth = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            // mPostDataConfig = new PostDataConfig(array.length());
            commentConfiguration = new CommentConfiguration(commentArrayLenth.length());

            for (int i = 0; i < commentArrayLenth.length(); i++) {
                JSONObject fetchedData = commentArrayLenth.getJSONObject(i);
                userName[i] = getUserName(fetchedData);
                userComment[i] = getUserComment(fetchedData);
                userProfilePic[i] = getProfilePic(fetchedData);
                userCommentDate[i] = getCommentDate(fetchedData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<PostDetail> postModel = new ArrayList<PostDetail>();
        ArrayList<CommentModel> commentModels = new ArrayList<CommentModel>();

        for (int i = 0; i < userName.length; i++) {
            //Model m = new Model();
            CommentModel commentModel = new CommentModel();
            commentModel.setProfilePic(userProfilePic[i]);
            commentModel.setUserComment(userComment[i]);
            commentModel.setUserFullName(userName[i]);
            commentModel.setCommentTime(userCommentDate[i]);
            commentModels.add(commentModel);

        }
        CommentAdapter commentAdapter = new CommentAdapter(commentModels, this);
        commentRecyclerView.setAdapter(commentAdapter);

        if(commentRecyclerView == null)
        {
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.comment_display_image);
            linearLayout.setVisibility(View.VISIBLE);

        }
      /*  PostDataAdapter adapter = new PostDataAdapter(postModel, getActivity());
        mRecyclerView.setAdapter(adapter);
   */

    }

    private String getProfilePic(JSONObject fetchedData) {
        String userPic = null;
        try {
            userPic = fetchedData.getString(Constants.comment_pic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userPic;
    }

    private String getUserName(JSONObject fetchedData) {
        String userName = null;
        try {
            userName = fetchedData.getString(Constants.comment_username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userName;
    }

    private String getUserComment(JSONObject fetchedData) {
        String userComment = null;
        try {
            userComment = fetchedData.getString(Constants.comment_usercomment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userComment;
    }

    private String getCommentDate(JSONObject fetchedData) {
        String commentDate = null;
        try {
            commentDate = fetchedData.getString(Constants.comment_date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentDate;
    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(),profile,Toast.LENGTH_SHORT).show();

        int conn3 = NetworkUtill.getConnectivityStatus(getApplicationContext());
        if (conn3 == NetworkUtill.TYPE_WIFI || conn3 == NetworkUtill.TYPE_MOBILE) {

            if (checkComment()) {
                sendCommentData();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please turn on Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean checkComment() {
    boolean checkComm = true;
        String check = mUserComment.getText().toString();
        if (check.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter Something",Toast.LENGTH_SHORT).show();
            checkComm = false ;
        }
        else if(check.length() > 500)
        {
            Toast.makeText(getApplicationContext(),"Character should be less than 500",Toast.LENGTH_SHORT).show();
            checkComm = false ;


        }
        return  checkComm ;
    }

   private void  sendCommentData()
   {
       final String vComment = mUserComment.getText().toString();
       StringRequest commentRequest = new StringRequest(Request.Method.GET, Constants.POST_COMMENT, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               parseCommentJson(response);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       })
       {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> sendData = new HashMap<>();
               sendData.put("comment",vComment);
               sendData.put("commentuserpic",mProfilePic);
//                sendData.put("suggestion",suggdetail2);

               return sendData;
           }
       }  ;
       RequestQueue getComment = Volley.newRequestQueue(this);
       getComment.add(commentRequest);



   }
}
