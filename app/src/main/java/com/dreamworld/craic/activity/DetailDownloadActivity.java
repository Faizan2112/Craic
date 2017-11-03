package com.dreamworld.craic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamworld.craic.R;

import java.io.File;
import java.io.FileOutputStream;

public class DetailDownloadActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mDisplayFullImage,mDisplayFullImage1;
    TextView mDetailText ,mDetailPath , mDetailSize ;
    Button mShare ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_download);
        mDisplayFullImage =(ImageView)findViewById(R.id.detail_download);
        mDetailText = (TextView) findViewById(R.id.detail_head);
        mDetailPath = (TextView) findViewById(R.id.detail_file_path);
        mDetailSize = (TextView) findViewById(R.id.detail_file_size);
        mShare = (Button) findViewById(R.id.detail_share);

        // mDisplayFullImage = (ImageView) findViewById(R.id.detail_main_image);
        // mDisplayFullImage1 =(ImageView) findViewById(R.id.detail_main_image1);
        findExtraIntent();

        mShare.setOnClickListener(this);
    }

    private void findExtraIntent() {



        Intent getExtraMain = getIntent();
        String imageUrl = getExtraMain.getStringExtra("getItemPosition");

        File file = new File(imageUrl);
       // Uri uri = Uri.fromFile(file);
        String imagePath = getExtraMain.getStringExtra("fileDetail");
        String imageSpace = getExtraMain.getStringExtra("fileSpace");
        String filePath = getExtraMain.getStringExtra("filePath");



//        String imageStoreUrlType = getStorage.getStringExtra("callFromDownload");

        mDetailText.setText(imagePath);
        mDetailSize.setText(imageSpace);
        mDetailPath.setText(filePath);
        Glide.with(getApplicationContext()).load(Uri.parse(imageUrl)).dontAnimate().fitCenter().placeholder(R.drawable.placeholder).into(mDisplayFullImage);




    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.detail_share) {

            String text = "Download this app Craic";
            ImageView content = (ImageView) findViewById(R.id.detail_download);
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
            startActivity(Intent.createChooser(share, "Share via"));


        }
    }}
