package com.dreamworld.craic.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamworld.craic.MainActivity;
import com.dreamworld.craic.R;
import com.dreamworld.craic.adapters.MyAdapter;
import com.dreamworld.craic.broadcastreciever.NetworkChangeRecierver;
import com.dreamworld.craic.model.CreateList;
import com.dreamworld.craic.networkcheck.NetworkUtill;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.dreamworld.craic.MainActivity.isNetConnected;
import static com.dreamworld.craic.networkcheck.NetworkUtill.TYPE_NOT_CONNECTED;
import static com.dreamworld.craic.networkcheck.NetworkUtill.getConnectivityStatusString;

public class DisplayDownloadActivity extends AppCompatActivity {

 //   ImageAdapter myImageAdapter;
    private GridLayoutManager mGridLayoutManager1, mGridLayoutManager2, mGridLayoutManager3;
    private ScaleGestureDetector mScaleGestureDetector;
    private List<String> mPhotoUris;
    private    RecyclerView.LayoutManager mCurrentLayoutManager;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    BroadcastReceiver mBroadcastReceiver;
    CoordinatorLayout mCoordinatorLayout ;
    private boolean internetConnected=true;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private Snackbar snackbar , snackbarlost;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_download);
        mBroadcastReceiver = new NetworkChangeRecierver();
        recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinate_layout);
        recyclerView.setHasFixedSize(true);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CreateList> createLists = prepareData();
        adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);
       /* GridView gridview = (GridView) findViewById(R.id.gridview);
        myImageAdapter = new ImageAdapter(this);
        gridview.setAdapter(myImageAdapter);

        setUpGallery();
       */
 //isNetConnected = false;
        setUpGridView();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        showSnackbar();
}

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }
    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            setSnackbarMessage(status,false);
        }
    };
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }
    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
    private void setSnackbarMessage(String status,boolean showBar) {
        String internetStatus="";
        if(status.equalsIgnoreCase("Wifi enabled")||status.equalsIgnoreCase("Mobile data enabled")){
            internetStatus="Internet Connected";
        }else {
            internetStatus="Lost Internet Connection";
        }
        snackbar = Snackbar
                .make(mCoordinatorLayout, internetStatus, Snackbar.LENGTH_INDEFINITE)
                .setAction("Home", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        snackbarlost = Snackbar
                .make(mCoordinatorLayout, internetStatus, Snackbar.LENGTH_INDEFINITE)
                .setAction("x", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbarlost.dismiss();             }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if(internetStatus.equalsIgnoreCase("Lost Internet Connection")){
            if(internetConnected){
                snackbarlost.show();
                internetConnected=false;
            }
        }else{
            if(!internetConnected){
                internetConnected=true;
                snackbar.show();
            }
        }
    }

    private void checkStatus() {
        int conn = NetworkUtill.getConnectivityStatus(this);
        if (conn == NetworkUtill.TYPE_WIFI || conn == NetworkUtill.TYPE_MOBILE) {
            Toast.makeText(getApplicationContext(),"net conneced",Toast.LENGTH_LONG);
          showSnackbar();

        }

    }
    private void showSnackbar() {

                Snackbar snackbar =Snackbar.make(mCoordinatorLayout,"Goo online",Snackbar.LENGTH_INDEFINITE).setAction("Home", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                });

            snackbar.setActionTextColor(Color.CYAN);
               View subView= snackbar.getView();
                TextView tv = (TextView) subView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.BLUE);
                snackbar.show();
                //useVolley();
            }





    private void setUpGridView() {

        if (recyclerView != null) {
            mGridLayoutManager1 = new GridLayoutManager(this, 1);
            mGridLayoutManager2 = new GridLayoutManager(this, 2);
            mGridLayoutManager3 = new GridLayoutManager(this, 3);

            //initialize photo uris list
            mPhotoUris = new ArrayList<>();

            //initialize adapter
//            ImageAdapter i = new ImageAdapter(this);

            //set layout manager
            mCurrentLayoutManager = mGridLayoutManager2;
            recyclerView.setLayoutManager(mGridLayoutManager2);

            //set adapter
            recyclerView.setAdapter(adapter);

            //set scale gesture detector
            mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    if (detector.getCurrentSpan() > 200 && detector.getTimeDelta() > 200) {
                        if (detector.getCurrentSpan() - detector.getPreviousSpan() < -1) {
                            if (mCurrentLayoutManager == mGridLayoutManager1) {
                                mCurrentLayoutManager = mGridLayoutManager2;
                                recyclerView.setLayoutManager(mGridLayoutManager2);
                                return true;
                            } else if (mCurrentLayoutManager == mGridLayoutManager2) {
                                mCurrentLayoutManager = mGridLayoutManager3;
                                recyclerView.setLayoutManager(mGridLayoutManager3);
                                return true;
                            }
                        } else if (detector.getCurrentSpan() - detector.getPreviousSpan() > 1) {
                            if (mCurrentLayoutManager == mGridLayoutManager3) {
                                mCurrentLayoutManager = mGridLayoutManager2;
                                recyclerView.setLayoutManager(mGridLayoutManager2);
                                return true;
                            } else if (mCurrentLayoutManager == mGridLayoutManager2) {
                                mCurrentLayoutManager = mGridLayoutManager1;
                                recyclerView.setLayoutManager(mGridLayoutManager1);
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });

            //set touch listener on recycler view
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mScaleGestureDetector.onTouchEvent(event);
                    return false;
                }



            });


        }

    }


  /*  private void setUpGallery() {
        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();

        String targetPath = ExternalStorageDirectoryPath + "/Download/followme/";

        Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
        File targetDirector = new File(targetPath);
        Uri imageUri = Uri.fromFile(targetDirector);

        File[] files = targetDirector.listFiles();
        for (File file : files) {
            myImageAdapter.add(file.getAbsolutePath());

        }

    }
*/
    private ArrayList<CreateList> prepareData(){
        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();

        String targetPath = ExternalStorageDirectoryPath + "/Download/craic/";

        Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
        File targetDirector = new File(targetPath);
        Uri imageUri = Uri.fromFile(targetDirector);

        File[] files = targetDirector.listFiles();

        ArrayList<CreateList> theimage = new ArrayList<>();

  if(files != null) {
      for (int i = 0; i < files.length; i++) {
          CreateList createList = new CreateList();
          //    createList.setImage_title(image_titles[i]);
          createList.setImage_ID("file:///storage/emulated/0/Download/craic/"+files[i].getName());
          theimage.add(createList);
      }

  }else
      {
          Toast.makeText(getApplicationContext(),"No imge to disply",Toast.LENGTH_LONG).show();

      }
      return theimage;

  }

}
