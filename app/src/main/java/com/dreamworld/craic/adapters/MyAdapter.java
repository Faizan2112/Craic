package com.dreamworld.craic.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dreamworld.craic.R;
import com.dreamworld.craic.interfaces.OnItemClickListener;
import com.dreamworld.craic.model.CreateList;


import java.io.File;
import java.util.ArrayList;

/**
 * Created by faizan on 26/07/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ProgressBar progressBar = null;
    private ArrayList<CreateList> galleryList;
    private Context context;


    public MyAdapter(Context context, ArrayList<CreateList> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {

       // File url = galleryList.get(i).getImage_ID();
        //final Uri imageUri = Uri.fromFile(url);
        //Glide.with(context).load(imageUri).into(viewHolder.img);

//     // viewHolder.title.setText(galleryList.get(i).getImage_title());
//      //  viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//     //   viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));
//   //    Uri imageUri = Uri .fromFile(galleryList.get(i).getImage_ID());
//        String mImageUrlString = "https://static.pexels.com/photos/17679/pexels-photo.jpg";
//
//        Glide.with(context).
//                load(mImageUrlString)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        progressBar.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        progressBar.setVisibility(View.GONE);
//                        return false;
//                    }
//                })
//                .crossFade(1000)
//                .error(R.drawable.share)
//                .into(viewHolder.img);
//
      /*  Glide.with(context)
                // .load(Uri.parse("/storage/emulated/0/Download/followme/foldername77.png"))
                // .load("http://www.planwallpaper.com/wallpaper-hd#static/images/b807c2282ab0a491bd5c5c1051c6d312_rP0kQjJ.jpg")
                .load(mImageUrlString)
                .centerCrop() // Image scale type
                .crossFade()
                .override(800,500) // Resize image
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.share)
                .into(viewHolder.img);
*/
      Glide.with(context).load(Uri.parse(galleryList.get(i).getImage_ID())).placeholder(R.drawable.placeholder).centerCrop().into(viewHolder.img);
      //  Glide.with(context).load(new File(galleryList.get(i).getImage_ID()).getAbsoluteFile()).placeholder(R.drawable.placeholder).centerCrop().into(viewHolder.img);

        //  Glide.with(context).load(Uri.parse("/storage/emulated/0/Download/followme/foldername77.png")).placeholder(R.drawable.placeholder).centerCrop().into(viewHolder.img);

        /* viewHolder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });*/

    }


    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;

        public ViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar_cyclic);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}
