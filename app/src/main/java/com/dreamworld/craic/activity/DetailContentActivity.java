package com.dreamworld.craic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dreamworld.craic.R;

import java.util.Locale;

public class DetailContentActivity extends AppCompatActivity {
    ImageView mDisplayFullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content);

        mDisplayFullImage = (ImageView) findViewById(R.id.detail_main_image);
        findExtraIntent();
    }

    private void findExtraIntent() {
        /*Bundle extras = getIntent().getExtras();
        if(extras != null)
        {

        }*/
        // wrong way
        //Intent getExtraIntent = new Intent();
        //String imageUrl =  getExtraIntent.getBundleExtra("detailImageUrl").toString();
        Intent getExtraIntent = getIntent();
        String imageUrl = getExtraIntent.getStringExtra("detailImageUrl");
//         Glide.with(this).load(imageUrl).centerCrop().placeholder(R.drawable.placeholder).into(mDisplayFullImage);
        Glide.with(this).load(imageUrl).dontAnimate().fitCenter().placeholder(R.drawable.placeholder).into(mDisplayFullImage);

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

    }

    class BitmapSizeTranscoder implements ResourceTranscoder<Bitmap, BitmapSizeTranscoder.Size> {
        @Override
        public Resource<Size> transcode(Resource<Bitmap> toTranscode) {
            Bitmap bitmap = toTranscode.get();
            Size size = new Size();
            size.width = bitmap.getWidth();
            size.height = bitmap.getHeight();
            return new SimpleResource<>(size);
        }

        @Override
        public String getId() {
            return getClass().getName();
        }

        class Size {
            int width, height;
        }
    }
}
