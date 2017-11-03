package com.dreamworld.craic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dreamworld.craic.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

public class DetailContentActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mDisplayFullImage,mDisplayFullImage1;
    TextView mDetailText ;
    Button mShare ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content);
        mDisplayFullImage =(ImageView)findViewById(R.id.detail_download);
        mDetailText = (TextView) findViewById(R.id.detail_head);
        mShare = (Button) findViewById(R.id.detail_share);

       // mDisplayFullImage = (ImageView) findViewById(R.id.detail_main_image);
       // mDisplayFullImage1 =(ImageView) findViewById(R.id.detail_main_image1);
        findExtraIntent();

mShare.setOnClickListener(this);
    }

    private void findExtraIntent() {



        Intent getExtraMain = getIntent();
        String imageUrl = getExtraMain.getStringExtra("imageUrl");
        String imageName = getExtraMain.getStringExtra("imageName");

//        String imageStoreUrlType = getStorage.getStringExtra("callFromDownload");

           mDetailText.setText(imageName);
            Glide.with(this).load(imageUrl).dontAnimate().fitCenter().placeholder(R.drawable.placeholder).into(mDisplayFullImage);




    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.detail_share){

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
    }

//
}
  /*Bundle extras = getIntent().getExtras();
        if(extras != null)
        {

        }*/
// wrong way
//Intent getExtraIntent = new Intent();
//String imageUrl =  getExtraIntent.getBundleExtra("detailImageUrl").toString();

//         Glide.with(this).load(imageUrl).centerCrop().placeholder(R.drawable.placeholder).into(mDisplayFullImage);
// Glide.with(this).load(imageUrl).dontAnimate().fitCenter().placeholder(R.drawable.placeholder).into(mDisplayFullImage);
// Glide.with(this).load(Uri.parse(imageStoreUrl)).dontAnimate().fitCenter().placeholder(R.drawable.placeholder).into(mDisplayFullImage);

// Toast.makeText(getApplicationContext(), "" + imageUrl, Toast.LENGTH_LONG).show();

      /*  Glide
                .with(this)
                .load(imageUrl)
                .asBitmap()
                .transcode(new BitmapSizeTranscoder(), BitmapSizeTranscoder.Size.class)
                .into(new SimpleTarget<BitmapSizeTranscoder.Size>() {
                    @Override
                    public void onResourceReady(BitmapSizeTranscoder.Size resource, GlideAnimation glideAnimation) {
                        Log.wtf("SIZE", String.format(Locale.ROOT, "%dx%d", resource.width, resource.height));
                    }
                })
        ;*/

//class BitmapSizeTranscoder implements ResourceTranscoder<Bitmap, BitmapSizeTranscoder.Size> {
//        @Override
//        public Resource<Size> transcode(Resource<Bitmap> toTranscode) {
//            Bitmap bitmap = toTranscode.get();
//            Size size = new Size();
//            size.width = bitmap.getWidth();
//            size.height = bitmap.getHeight();
//            return new SimpleResource<>(size);
//        }
//
//        @Override
//        public String getId() {
//            return getClass().getName();
//        }
//
//        class Size {
//            int width, height;
//        }
//    }
