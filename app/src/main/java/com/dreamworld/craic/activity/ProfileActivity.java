package com.dreamworld.craic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dreamworld.craic.R;
import com.dreamworld.craic.adapters.PostDataAdapter;
import com.dreamworld.craic.classess.DividerItemDecoration;
import com.dreamworld.craic.configuration.Config;
import com.dreamworld.craic.configuration.Constants;
import com.dreamworld.craic.configuration.PostDataConfig;
import com.dreamworld.craic.model.PostDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dreamworld.craic.activity.LoginActivity.savedeatail;
import static com.dreamworld.craic.activity.LoginActivity.loggedin;
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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private PostDataConfig mPostDataConfig;
    ImageView mProfilepic;
    Button mEditProfile;
    private static String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mRecyclerView = (RecyclerView) findViewById(R.id.home_all_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle(firstName+"  khan");
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setNestedScrollingEnabled(false);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile_menu_about:
                        Intent i = new Intent(getApplicationContext(), DisplayDownloadActivity.class);
                        startActivity(i);
                        //  Toast.makeText(getApplicationContext(), "downlod", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.profile_menu_logout:
                        clearSharedPreferences();
                        //   Toast.makeText(getApplicationContext(), "feed_btm_my_share", Toast.LENGTH_LONG).show();
                        break;

                }
                return false;
            }
        });

        initializeView();
        useVolley();
        getSharedata();

    }

    private void getSharedata() {
    savedeatail = getSharedPreferences(Constants.LoginSharePreferenceName,MODE_PRIVATE);
        String profile = savedeatail.getString(Constants.LoginSharePreferenceProfilePic,Constants.posts_profilepic);
        firstName = savedeatail.getString(Constants.LoginSharePreferenceProfilePic,Constants.posts_firstname);
        Glide.with(this).load(profile).placeholder(R.drawable.placeholder).fitCenter().into(mProfilepic);

    }

    private void clearSharedPreferences() {
        loggedin = false;
        savedeatail = getSharedPreferences(Constants.LoginSharePreferenceName, MODE_PRIVATE);
        SharedPreferences.Editor editlogOot = savedeatail.edit();
        editlogOot.remove(Constants.LoginSharePreferenceEmail);
        editlogOot.putBoolean(Constants.LoginSharePreferenceLogValue, loggedin);
        editlogOot.apply();


        Intent logOut = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(logOut);
        finish();


    }

    private void initializeView() {
        mProfilepic = (ImageView) findViewById(R.id.profile_mainimage);
        mEditProfile = (Button) findViewById(R.id.profile_edit);
        mEditProfile.setOnClickListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                useVolley();
            }
        });

    }

    private void useVolley() {
        StringRequest sr = new StringRequest(Request.Method.GET, Constants.GET_All_Post_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseJson(response);
               // Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue lRequestQueue = Volley.newRequestQueue(this);
        lRequestQueue.add(sr);
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            // mConfigFile = new Config(array.length());
            mPostDataConfig = new PostDataConfig(array.length());
            for (int i = 0; i < array.length(); i++) {
                JSONObject fetchedData = array.getJSONObject(i);
                posts[i] = getPosts(fetchedData);
                post_icon[i] = getPost_icon(fetchedData);
                date[i] = getDate(fetchedData);
                titel[i] = getTitel(fetchedData);
                post_id[i] = getPost_id(fetchedData);
                subtitle[i] = getSubtitle(fetchedData);
                mainimageurl[i] = getMainimageurl(fetchedData);
                articlesummary[i] = getArticlesummary(fetchedData);
                articledescription[i] = getarticleDescription(fetchedData);
                articleconclution[i] = getArticleconclution(fetchedData);
                likes[i] = getLikes(fetchedData);
                comments[i] = getComments(fetchedData);
                viewtype[i] = getViewType(fetchedData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<PostDetail> postModel = new ArrayList<PostDetail>();
        for (int i = 0; i < posts.length; i++) {
            //Model m = new Model();
            PostDetail m = new PostDetail();
            m.setPosts(posts[i]);
            m.setPost_icon(post_icon[i]);
            m.setDate(date[i]);
            m.setTitel(titel[i]);
            m.setPost_id(post_id[i]);
            m.setSubtitle(subtitle[i]);
            m.setMainimageurl(mainimageurl[i]);
            m.setArticlesummary(articlesummary[i]);
            m.setArticledescription(articledescription[i]);
            m.setArticleconclution(articleconclution[i]);
            m.setLikes(likes[i]);
            m.setComments(comments[i]);
            m.setViewtype(viewtype[i]);


            postModel.add(m);

        }
        PostDataAdapter adapter = new PostDataAdapter(postModel, this);
        mRecyclerView.setAdapter(adapter);

    }

    private String getPosts(JSONObject fetchedData) {
        String posts = null;
        try {
            posts = fetchedData.getString(Constants.posts_posts);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;


    }

    private int getViewType(JSONObject fetchedData) {
        int viewtype = 0;
        try {
            viewtype = fetchedData.getInt(Constants.posts_viewtype);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return viewtype;
    }

    private String getPost_icon(JSONObject fetchedData) {

        String post_icon = null;
        try {
            post_icon = fetchedData.getString(Constants.posts_post_icon);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post_icon;
    }

    private String getTitel(JSONObject fetchedData) {
        String titel = null;
        try {
            titel = fetchedData.getString(Constants.posts_titel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return titel;
    }

    private String getPost_id(JSONObject fetchedData) {
        String post_id = null;
        try {
            post_id = fetchedData.getString(Constants.posts_post_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post_id;
    }

    private String getSubtitle(JSONObject fetchedData) {
        String subtitle = null;
        try {
            subtitle = fetchedData.getString(Constants.posts_subtitle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subtitle;

    }


    private String getMainimageurl(JSONObject fetchedData) {
        String mainimageurl = null;
        try {
            mainimageurl = fetchedData.getString(Constants.posts_mainimageurl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainimageurl;
    }

    private String getDate(JSONObject fetchedData) {
        String date = null;
        try {
            date = fetchedData.getString("date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return date;
    }

    private int getLikes(JSONObject fetchedData) {
        int likes = 0;
        try {
            likes = fetchedData.getInt(Constants.posts_likes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return likes;
    }

    private int getComments(JSONObject fetchedData) {
        int comments = 0;
        try {
            comments = fetchedData.getInt(Constants.posts_comments);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comments;
    }

    private String getArticlesummary(JSONObject fetchedData) {
        String articlesummary = null;
        try {
            articlesummary = fetchedData.getString(Constants.posts_articlesummary);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articlesummary;
    }

    private String getarticleDescription(JSONObject fetchedData) {
        String articledescription = null;
        try {
            articledescription = fetchedData.getString(Constants.posts_articledescription);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articledescription;
    }

    private String getArticleconclution(JSONObject fetchedData) {
        String articleconclution = null;
        try {
            articleconclution = fetchedData.getString(Constants.posts_articleconclution);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articleconclution;
    }

    @Override
    public void onClick(View v) {
        Intent editProfile = new Intent(getApplicationContext(),EditProfileActivity.class);
        startActivity(editProfile);
    }
}
