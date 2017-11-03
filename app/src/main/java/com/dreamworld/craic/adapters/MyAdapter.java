package com.dreamworld.craic.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamworld.craic.R;
import com.dreamworld.craic.interfaces.OnDownloadActivity;
import com.dreamworld.craic.interfaces.OnHomeImageViewClick;
import com.dreamworld.craic.model.CreateList;
import com.dreamworld.craic.model.Model;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by faizan on 26/07/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ProgressBar progressBar = null;
    private ArrayList<CreateList> galleryList;
    private Context context;

    public static boolean multiSelect = false;
    private ArrayList<Integer> selectedItems = new ArrayList<Integer>();
    private OnDownloadActivity onDownloadActivity;

    public OnDownloadActivity getOnDownloadActivity() {
        return onDownloadActivity;
    }

    public void setOnDownloadActivity(OnDownloadActivity onDownloadActivity) {
        this.onDownloadActivity = onDownloadActivity;
    }

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
        final CreateList object = galleryList.get(i);
        String il = galleryList.get(i).getImage_ID();
        Glide.with(context).load(Uri.parse(galleryList.get(i).getImage_ID())).placeholder(R.drawable.placeholder).dontAnimate().fitCenter().into(viewHolder.img);
      //  viewHolder.update(i);

        View.OnClickListener imageGridTypeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    onItemClickListener.onItemClick(object);

                onDownloadActivity.onItemClick(v,object);
            }
        };

        viewHolder.img.setOnClickListener(imageGridTypeListener);
    }


    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // LinearLayout linearLayout;
        private TextView title;
        private ImageView img;
        LinearLayout linearLayout;


        public ViewHolder(View view) {
            super(view);
            //  progressBar = (ProgressBar) view.findViewById(R.id.progressBar_cyclic);

            title = (TextView) view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
            linearLayout = (LinearLayout) view.findViewById(R.id.download_gall);

        }

        void selectItem(Integer item) {
            if (multiSelect) {
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item);
                    linearLayout.setBackgroundColor(Color.WHITE);
                } else {
                    selectedItems.add(item);
                    linearLayout.setBackgroundColor(Color.CYAN);
                }
            }
        }

        void update(final Integer value) {
            //  title.setText(value + "");
            if (selectedItems.contains(value)) {
                linearLayout.setBackgroundColor(Color.LTGRAY);
            } else {
                linearLayout.setBackgroundColor(Color.WHITE);
            }

            this.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((AppCompatActivity) view.getContext()).startSupportActionMode(actionModeCallbacks);
                    selectItem(value);
                    return true;
                }
            });
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectItem(value);
                }
            });
        }

    }

    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            multiSelect = true;
            menu.add("Delete");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            File appvc = new File(Environment.getExternalStorageDirectory()
//                    .getAbsolutePath(), fileName);
//
//            if (appvc.isDirectory()) {
//                String[] children = appvc.list();
//                for (int i = 0; i < children.length; i++) {
//                    new File(appvc, children[i]).delete();
//                }
//            }
//
            // ArrayList<String> rr = new ArrayList<String>();
            for (Integer intItem : selectedItems) {
                //      rr.add("intItem");
                 Toast.makeText(context, "" + galleryList.get(intItem).getImage_ID(), Toast.LENGTH_SHORT).show();
              //  galleryList.remove(galleryList.get(intItem).getImage_ID());
            String s =galleryList.get(intItem).getImage_ID();
                shareFiles(s);
                //DeleteFile(s);
            }
            notifyDataSetChanged();
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            multiSelect = false;
            selectedItems.clear();
            notifyDataSetChanged();
        }
    };

    private void shareFiles(String s) {
    ArrayList<Uri> uriImage = new ArrayList<Uri>();

        Uri parseUri=null;
       for(int i = 0 ; i<=selectedItems.size();i++ )
       {
          parseUri =Uri.parse(s);
           uriImage.add(parseUri);
       }
     //   Uri phototUri = Uri.parse(s);
        Intent share = new Intent(Intent.ACTION_SEND_MULTIPLE);

        //share.setData(phototUri);
        share.setType("*/*");
         share.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uriImage);
        share.setPackage("com.whatsapp");

       share.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
         context.startActivity(Intent.createChooser(share,"Share via"));

    }

    public void DeleteFile(String fileName) {
        File appvc = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), fileName);

        if (appvc.isDirectory()) {
            String[] children = appvc.list();
            for (int i = 0; i < children.length; i++) {
                new File(appvc, children[i]).delete();
            }
        }
    }

}

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

//  Glide.with(context).load(new File(galleryList.get(i).getImage_ID()).getAbsoluteFile()).placeholder(R.drawable.placeholder).centerCrop().into(viewHolder.img);

//  Glide.with(context).load(Uri.parse("/storage/emulated/0/Download/followme/foldername77.png")).placeholder(R.drawable.placeholder).centerCrop().into(viewHolder.img);

        /* viewHolder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });*/

