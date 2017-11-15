package com.dreamworld.craic.fragments;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dreamworld.craic.MainActivity;
import com.dreamworld.craic.R;
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
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static com.dreamworld.craic.MainActivity.deleteLikeid;
import static com.dreamworld.craic.MainActivity.saveLikeid;
import static com.dreamworld.craic.R.id.recyclerView;
import static com.dreamworld.craic.activity.HomeActivity.isNetConnected;
import static com.dreamworld.craic.configuration.Config.SHARED_PREF_NAME;
import static com.dreamworld.craic.configuration.Config.UPDATELIKE_URL;
import static com.dreamworld.craic.configuration.Config.UPDATEUNLIKE_URL;
import static com.dreamworld.craic.configuration.Config.currentImage;
import static com.dreamworld.craic.configuration.Config.date;
import static com.dreamworld.craic.configuration.Config.headImage;
import static com.dreamworld.craic.configuration.Config.headTitel;
import static com.dreamworld.craic.configuration.Config.likes;
import static com.dreamworld.craic.configuration.Config.names;
import static com.dreamworld.craic.configuration.Config.urls;
import static com.dreamworld.craic.configuration.Config.viewtype;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllContentFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    BroadcastReceiver mBroadcastReceiver;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static Set<String> saveLikeid;
    public static Set<String> deleteLikeid ;
    public static ArrayList<String> imagePos= new ArrayList<>();
    private static SharedPreferences addImageId;
    private RecyclerView mRecyclerView;
    private Config mConfigFile;
    private int conn;

    public AllContentFragment() {
        // Required empty public constructor
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBroadcastReceiver = new NetworkChangeRecierver();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_content, container, false);
        initializeView(v);
        retriveSharedKey();
        verifyStoragePermissions(this);



        return v;

    }

    private void verifyStoragePermissions(AllContentFragment allContentFragment) {
        int writePermission = ActivityCompat.checkSelfPermission(allContentFragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(allContentFragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    allContentFragment.getActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        IntentFilter networkFilter = new IntentFilter();
        networkFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        networkFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        getActivity().registerReceiver(mBroadcastReceiver, networkFilter);
        checkStatus();
    }

    private void checkStatus() {

        conn = NetworkUtill.getConnectivityStatus(getActivity());
        if (conn == NetworkUtill.TYPE_WIFI || conn == NetworkUtill.TYPE_MOBILE) {
            if (!isNetConnected) {
                isNetConnected = true;
                useVolley();
            }
        } else {
            Intent downloadIntent = new Intent(getContext().getApplicationContext(), DisplayDownloadActivity.class);
            startActivity(downloadIntent);
            //finish();
        }

    }



    @Override
    public void onResume() {
        super.onResume();
        if (isNetConnected) {
            isNetConnected = true;
            useVolley();

        }

    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        isNetConnected = true;
    }

    @Override
    public void onDestroy() {
        //  unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
        isNetConnected = false;

    }
    private void retriveSharedKey() {

        if(null == saveLikeid)
        {
            saveLikeid = new HashSet<>();
        }

        addImageId = getActivity().getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        saveLikeid = addImageId.getStringSet("ImageIds",saveLikeid);

        //  Iterator<String> it ;





    }


    private void initializeView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.main_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                useVolley();
            }
        });
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

    }

    private void useVolley() {
        StringRequest sr = new StringRequest(Request.Method.GET, Config.GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseJson(response);
                //  Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext().getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue lRequestQueue = Volley.newRequestQueue(getActivity());
        lRequestQueue.add(sr);
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            mConfigFile = new Config(array.length());

            for (int i = 0; i < array.length(); i++) {
                JSONObject fetchedData = array.getJSONObject(i);
                viewtype[i] = getViewType(fetchedData);
                names[i] = getName(fetchedData);
                urls[i] = getURL(fetchedData);
                currentImage[i] = getImageId(fetchedData);
                headImage[i] = getHeadImage(fetchedData);
                date[i] = getDate(fetchedData);
                likes[i] = getLikes(fetchedData);
                headTitel[i] = getHeadTitel(fetchedData) ;
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
            m.setImageId(currentImage[i]);
            m.setHeadImage(headImage[i]);
            m.setHeadTitel(headTitel[i]);
            m.setDate(date[i]);
            m.setLikes(likes[i]);
            models.add(m);

        }
        MultiViewAdapter adapter = new MultiViewAdapter(models, getActivity());
        mRecyclerView.setAdapter(adapter);
        adapter.setOnHomeImageViewClick(new OnHomeImageViewClick() {


            @Override
            public void onItemClick(RecyclerView.ViewHolder ho, View fullScreenImage, Model itemPostion) {

                final int randomNo = new Random().nextInt(61) + 20;
                //  int listenerType = itemPostion.getType();
                int adapterIds = fullScreenImage.getId();
                String getItemPosition = itemPostion.getUrl();
                String getName = itemPostion.getName();
                int getModelType = itemPostion.getType();

                switch (adapterIds) {
                    case R.id.home_main_downloadImage:
                        //
                        // Toast.makeText(MainActivity.this, itemPostion.getUrl(), Toast.LENGTH_LONG).show();
                        String name_ = "images" + randomNo;
                        File direct = new File(Environment.getExternalStorageDirectory() + "/Download/craic", name_);

                        if (!direct.exists()) {
                            File wallpaperDirectory = new File("/sdcard/Download/craic/", name_);
                            wallpaperDirectory.getParentFile().mkdirs();
                        }
                        new DownloadTask(getActivity(), direct, "downloading").execute(itemPostion.getUrl());

                        //  }

                        break;
                    case R.id.home_main_image:

                    {

                        //

                        String imageUrl = itemPostion.getUrl();
                        String imageName = itemPostion.getHeadTitel();

                        Intent sendImage = new Intent(getActivity(), DetailContentActivity.class);
                        //  sendImage.putExtra(imageUrl, "detailImageUrl");
                        sendImage.putExtra("imageUrl", imageUrl);
                        sendImage.putExtra("imageName", imageName);
                        startActivity(sendImage);
                        //     Toast.makeText(getApplicationContext(), "" + getItemPosition + "" + getName + "" + getModelType, Toast.LENGTH_LONG).show();

                    }

                    // }
                    break;
                    case R.id.home_image_like:

                        String imageId =  String.valueOf(itemPostion.getImageId());
                        //      Toast.makeText(getApplicationContext(), "" + imageId, Toast.LENGTH_LONG).show();
                        int conn2 = NetworkUtill.getConnectivityStatus(getActivity().getApplicationContext());
                        if(conn2 == NetworkUtill.TYPE_WIFI || conn2 == NetworkUtill.TYPE_MOBILE) {
                            updateLike(ho, itemPostion);
                        }else
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"Please turn on Internet Connection" ,Toast.LENGTH_SHORT).show();

                        }

                        break;

                    case R.id.home_image_unlike:



                        int conn3 = NetworkUtill.getConnectivityStatus(getActivity().getApplicationContext());
                        if(conn3 == NetworkUtill.TYPE_WIFI || conn3 == NetworkUtill.TYPE_MOBILE) {
                            updateunlike(ho,itemPostion);
                        }else
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"Please turn on Internet Connection" ,Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case R.id.home_gif_share:
                        String text = "Download this app Craic";
                        ImageView content = (ImageView) ho.itemView.findViewById(R.id.home_main_image);
                        content.setDrawingCacheEnabled(true);
                        Bitmap bitmap = Bitmap.createBitmap(content.getDrawingCache());
                        File cachePath = new File("/storage/emulated/0/Download/Craic/image.jpg");
                        try {
                            cachePath.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(cachePath);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                            ostream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        Intent share = new Intent(Intent.ACTION_SEND);
                        Uri phototUri = Uri.parse("/storage/emulated/0/Download/Craic/image.jpg");
                        share.setData(phototUri);
                        share.setType("image/*");
                        share.putExtra(Intent.EXTRA_TEXT, text);
                        share.putExtra(Intent.EXTRA_STREAM, phototUri);
                        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        fullScreenImage.getContext().startActivity(Intent.createChooser(share, "Share via"));

                        break;
//                    case R.id.home_main_image:
//                        if (getModelType == Model.IMAGE_TYPE) {
//                            Toast.makeText(MainActivity.this, itemPostion.getUrl(), Toast.LENGTH_LONG).show();
//                            String name_ = "gifs" + randomNo + ".gif";
//                            File direct = new File(Environment.getExternalStorageDirectory() + "/Download/craic", name_);
//
//                            if (!direct.exists()) {
//                                File wallpaperDirectory = new File("/sdcard/Download/craic/", name_);
//                                wallpaperDirectory.getParentFile().mkdirs();
//                            }
//                            new DownloadTask(MainActivity.this, direct, "downloading").execute(itemPostion.getUrl());
//
//                        }
//                        break;

                    case R.id.home_listen_text:

                       // speakOut(getName);

                        break;

                    case R.id.home_share_text:
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getName);
                        //                   startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
                        startActivity(Intent.createChooser(sharingIntent, "Share to"));
                        break;

                }


            }
        });



    }
    private void updateunlike(final RecyclerView.ViewHolder ho, final Model itemPostion) {

        StringRequest updateunLike = new StringRequest(Request.Method.POST, UPDATEUNLIKE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
                parseLike(response,ho);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(), "" + error.getMessage() , Toast.LENGTH_LONG).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imageId =  String.valueOf(itemPostion.getImageId());
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("id",imageId );

                return params;
            }
        };


        RequestQueue lRequestQueue = Volley.newRequestQueue(getActivity());
        lRequestQueue.add(updateunLike);

        ImageView countunLike = (ImageView)ho.itemView.findViewById(R.id.home_image_unlike);
        if(countunLike.getVisibility()==View.VISIBLE) {
            countunLike.setVisibility(View.GONE);

            if(countunLike.getVisibility()==View.GONE)
            {
                ImageView showlike = (ImageView) ho.itemView.findViewById(R.id.home_image_like);
                showlike.setVisibility(View.VISIBLE);
                boolean del = saveLikeid.remove(String.valueOf(itemPostion.getImageId()));
                if(del) {

                    addImageId = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = addImageId.edit();
                    editor.clear();
                    editor.putStringSet("ImageIds", saveLikeid);
                    editor.apply();
                }
            }

            if(null==deleteLikeid)
            {
                deleteLikeid = new HashSet<>();

            }
            //  deleteLikeid.clear();
            //   String iddd= String.valueOf(itemPostion.getImageId());
            //   deleteLikeid.add(String.valueOf(itemPostion.getImageId()));
            //   boolean del = saveLikeid.removeAll(deleteLikeid);

//
//  SharedPreferences removeImageid = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//                if(removeImageid.contains(String.valueOf(itemPostion.getImageId()))
//                        )
//                {
//                    removeImageid.edit().remove(String.valueOf(itemPostion.getImageId())).apply();
//
//                }
                /*Editor editRemoveImageid = removeImageid.edit();
                editRemoveImageid.remove(String.valueOf(itemPostion.getImageId()));
                editRemoveImageid.apply();
*/


        }



    }

    private void updateLike(final RecyclerView.ViewHolder ho, final Model itemPostion) {
        // saveLikeid ;
        StringRequest updateLike = new StringRequest(Request.Method.POST, UPDATELIKE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
                parseLike(response,ho);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(), "" + error.getMessage() , Toast.LENGTH_LONG).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imageId =  String.valueOf(itemPostion.getImageId());
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("id",imageId );

                return params;
            }
        };


        RequestQueue lRequestQueue = Volley.newRequestQueue(getActivity());
        lRequestQueue.add(updateLike);

        ImageView countLike = (ImageView)ho.itemView.findViewById(R.id.home_image_like);
        if(countLike.getVisibility()==View.VISIBLE) {
            countLike.setVisibility(View.GONE);

            if(countLike.getVisibility()==View.GONE)
            {
                ImageView showUnlike = (ImageView) ho.itemView.findViewById(R.id.home_image_unlike);
                showUnlike.setVisibility(View.VISIBLE);

                if(null==saveLikeid)
                {
                    saveLikeid = new HashSet<>();

                }
                saveLikeid.add(String.valueOf(itemPostion.getImageId()));

                addImageId = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= addImageId.edit();
                editor.putStringSet("ImageIds",saveLikeid);
                editor.apply();



            }


        }


    }

    private void parseLike(String response, RecyclerView.ViewHolder ho) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject likeValuePos = array.getJSONObject(0);
            String likeValue = likeValuePos.getString("likes");
            //  mConfigFile = new Config(array.length());
            TextView likeUpdate = (TextView) ho.itemView.findViewById(R.id.home_likes);
            likeUpdate.setText(likeValue);

            // JSONObject fetchedData = array.getJSONObject("likes");}
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void speakOut(String text) {
        //  String text = getText.getText().toString();

     //   mListenText.speak(text, TextToSpeech.QUEUE_FLUSH, null);

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
    private int getImageId(JSONObject fetchedData) {

        int currentImage = 0;
        try {
            currentImage = fetchedData.getInt("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currentImage;
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
    private String getHeadImage(JSONObject fetchedData) {
        String headImage = null;
        try {
            headImage = fetchedData.getString("thumbnail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headImage;
    }
    private String getHeadTitel(JSONObject fetchedData) {
        String headImage = null;
        try {
            headImage = fetchedData.getString("headdetail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headImage;

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
            likes = fetchedData.getInt("likes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return likes;
    }
}
