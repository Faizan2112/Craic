package com.dreamworld.craic.activity;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.dreamworld.craic.R;
import com.dreamworld.craic.adapters.MyAdapter;
import com.dreamworld.craic.model.CreateList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DisplayDownloadActivity extends AppCompatActivity {

 //   ImageAdapter myImageAdapter;
    private GridLayoutManager mGridLayoutManager1, mGridLayoutManager2, mGridLayoutManager3;
    private ScaleGestureDetector mScaleGestureDetector;
    private List<String> mPhotoUris;
    private    RecyclerView.LayoutManager mCurrentLayoutManager;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_download);

        recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
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

        setUpGridView();
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
          createList.setImage_ID(files[i].getName());
          theimage.add(createList);
      }

  }else
      {
          Toast.makeText(getApplicationContext(),"No imge to disply",Toast.LENGTH_LONG).show();

      }
      return theimage;

  }

}
