package com.dreamworld.craic;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dreamworld.craic.activity.DetailContentActivity;
import com.dreamworld.craic.activity.DisplayDownloadActivity;
import com.dreamworld.craic.adapters.MultiViewAdapter;
import com.dreamworld.craic.broadcastreciever.NetworkChangeRecierver;
import com.dreamworld.craic.classess.DividerItemDecoration;
import com.dreamworld.craic.classess.DownloadTask;
import com.dreamworld.craic.configuration.Config;
import com.dreamworld.craic.interfaces.OnHomeImageViewClick;
import com.dreamworld.craic.model.Model;
import com.dreamworld.craic.networkcheck.NetworkUtill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static com.dreamworld.craic.R.id.recyclerView;
import static com.dreamworld.craic.configuration.Config.names;
import static com.dreamworld.craic.configuration.Config.urls;
import static com.dreamworld.craic.configuration.Config.viewtype;

//import static com.dreamworld.craic.networkcheck.NetworkUtill.getConnectivityStatusString;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    BroadcastReceiver mBroadcastReceiver;
    private RecyclerView mRecyclerView;
    private static  boolean isNetConnected = false;
    private Config mConfigFile;
    TextToSpeech mListenText;

    private RecyclerView.Adapter mRecyclerViewAdapter;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBroadcastReceiver = new NetworkChangeRecierver();
        //    getConnectivityStatusString(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        mRecyclerView = (RecyclerView) findViewById(recyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        verifyStoragePermissions(this);
        mListenText = new TextToSpeech(this, this);


    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void useVolley() {
        StringRequest sr = new StringRequest(Request.Method.GET, Config.GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseJson(response);
                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue lRequestQueue = Volley.newRequestQueue(this);
        lRequestQueue.add(sr);


    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter networkFilter = new IntentFilter();
        networkFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        networkFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        registerReceiver(mBroadcastReceiver, networkFilter);
        checkStatus();
    }

    private void checkStatus() {
        int conn = NetworkUtill.getConnectivityStatus(this);
        if (conn == NetworkUtill.TYPE_WIFI || conn == NetworkUtill.TYPE_MOBILE) {
            if(isNetConnected){
            isNetConnected = true;
            useVolley();
            }
        } else {
            Intent downloadIntent = new Intent(MainActivity.this, DisplayDownloadActivity.class);
            startActivity(downloadIntent);
            finish();
        }

    }

    private void fetchdata() {
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isNetConnected)
        {
            isNetConnected = true ;

        }

    }

    @Override
    protected void onPause() {
        unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //  unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            mConfigFile = new Config(array.length());

            for (int i = 0; i <= array.length(); i++) {
                JSONObject fetchedData = array.getJSONObject(i);
                viewtype[i] = getViewType(fetchedData);
                names[i] = getName(fetchedData);
                urls[i] = getURL(fetchedData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Model> models = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            Model m = new Model();
            m.setName(names[i]);
            m.setUrl(urls[i]);
            m.setType(viewtype[i]);
            models.add(m);

        }
        MultiViewAdapter adapter = new MultiViewAdapter(models, this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnHomeImageViewClick(new OnHomeImageViewClick() {


            @Override
            public void onItemClick(View fullScreenImage, Model itemPostion) {

                final int randomNo = new Random().nextInt(61) + 20;
                //  int listenerType = itemPostion.getType();
                int adapterIds = fullScreenImage.getId();
                String getItemPosition = itemPostion.getUrl();
                String getName = itemPostion.getName();
                int getModelType = itemPostion.getType();

                switch (adapterIds) {
                    case R.id.home_main_downloadImage:
                        if (getModelType == Model.IMAGE_TYPE) {
                            Toast.makeText(MainActivity.this, itemPostion.getUrl(), Toast.LENGTH_LONG).show();
                            String name_ = "images" + randomNo;
                            File direct = new File(Environment.getExternalStorageDirectory() + "/Download/craic", name_);

                            if (!direct.exists()) {
                                File wallpaperDirectory = new File("/sdcard/Download/craic/", name_);
                                wallpaperDirectory.getParentFile().mkdirs();
                            }
                            new DownloadTask(MainActivity.this, direct, "downloading").execute(itemPostion.getUrl());

                        }

                        break;
                    case R.id.home_main_image:

                    {

                        if (getModelType == Model.IMAGE_TYPE) {

                            String imageUrl = itemPostion.getUrl();
                            Intent sendImage = new Intent(MainActivity.this, DetailContentActivity.class);
                            //  sendImage.putExtra(imageUrl, "detailImageUrl");
                            sendImage.putExtra("detailImageUrl", imageUrl);
                            startActivity(sendImage);
                            Toast.makeText(getApplicationContext(), "" + getItemPosition + "" + getName + "" + getModelType, Toast.LENGTH_LONG).show();

                        }

                    }

                    break;
                    case R.id.home_main_gif_image:
                        if (getModelType == Model.IMAGE_TYPE) {
                            Toast.makeText(MainActivity.this, itemPostion.getUrl(), Toast.LENGTH_LONG).show();
                            String name_ = "gifs" + randomNo + ".gif";
                            File direct = new File(Environment.getExternalStorageDirectory() + "/Download/craic", name_);

                            if (!direct.exists()) {
                                File wallpaperDirectory = new File("/sdcard/Download/craic/", name_);
                                wallpaperDirectory.getParentFile().mkdirs();
                            }
                            new DownloadTask(MainActivity.this, direct, "downloading").execute(itemPostion.getUrl());

                        }
                        break;

                    case R.id.home_listen_text:

                        speakOut(getName);

                        break;

                    case R.id.home_share_text:
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getName);
     //                   startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
                         startActivity(Intent.createChooser(sharingIntent,"Share to"));
                        break;

                }


            }
        });
   /*     adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Model item) {


             //   final TextToSpeech finalListenText = listenText;

                final int randomNo = new Random().nextInt(61) + 20;
                int listenerType = item.getType();



                if (listenerType == 1) {
                    Toast.makeText(MainActivity.this, item.getUrl(), Toast.LENGTH_LONG).show();
                    String name_ = "images" + randomNo;
                    File direct = new File(Environment.getExternalStorageDirectory() + "/Download/craic", name_);

                    if (!direct.exists()) {
                        File wallpaperDirectory = new File("/sdcard/Download/craic/", name_);
                        wallpaperDirectory.getParentFile().mkdirs();
                    }
                    new DownloadTask(MainActivity.this, direct, "downloading").execute(item.getUrl());
                } else if (listenerType == 0) {

                    listenText=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if(status == TextToSpeech.SUCCESS) {
                                listenText.setLanguage(Locale.UK);
                            }
                            else {
                                Log.e("TTS", "Initialization failed");
                            }
                        }
                    });
                    Toast.makeText(MainActivity.this, item.getName(), Toast.LENGTH_LONG).show();
                    listenText.speak(item.getName(), TextToSpeech.QUEUE_FLUSH, null);



                } else if (listenerType == 2) {
                    Toast.makeText(MainActivity.this, item.getUrl(), Toast.LENGTH_LONG).show();
                    String name_ = "gifs" + randomNo +".gif";
                    File direct = new File(Environment.getExternalStorageDirectory() + "/Download/craic", name_);

                    if (!direct.exists()) {
                        File wallpaperDirectory = new File("/sdcard/Download/craic/", name_);
                        wallpaperDirectory.getParentFile().mkdirs();
                    }
                    new DownloadTask(MainActivity.this, direct, "downloading").execute(item.getUrl());

                }
            }

        });

*/


    }

    private void speakOut(String text) {
      //  String text = getText.getText().toString();

        mListenText.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }

    private int getViewType(JSONObject fetchedData) {
        int viewtype = 0;
        try {
            viewtype = fetchedData.getInt("viewtype");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return viewtype;
    }

    private String getName(JSONObject fetchedData) {
        String name = null;
        try {
            name = fetchedData.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getURL(JSONObject fetchedData) {
        String url = null;
        try {
            url = fetchedData.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = mListenText.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                //getTextVoice.setEnabled(true);
               // speakOut(S);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
}
