package com.dreamworld.craic.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamworld.craic.R;
import com.dreamworld.craic.interfaces.OnItemClickListener;
import com.dreamworld.craic.model.Model;
import com.dreamworld.craic.transformshape.CircleTransform;
import com.dreamworld.craic.interfaces.OnHomeImageViewClick;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by faizan on 31/07/2017.
 */

public class MultiViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Model> dataSet;
    Context mContext;
    int total_types;
    int lastPosition = -1;
    MediaPlayer mPlayer;
    List<Model> models;
    private boolean fabStateVolume = false;
    private OnItemClickListener onItemClickListener;
    private OnHomeImageViewClick onHomeImageViewClick;

    public OnHomeImageViewClick getOnHomeImageViewClick() {
        return onHomeImageViewClick;
    }

    public void setOnHomeImageViewClick(OnHomeImageViewClick onHomeImageViewClick) {
        this.onHomeImageViewClick = onHomeImageViewClick;
    }

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {


        TextView txtType;
        LinearLayout textLinearlayout;
        ImageView mSharetext, mListentext;


        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.home_main_text);
            this.textLinearlayout = (LinearLayout) itemView.findViewById(R.id.home_main_text_type);
            this.mSharetext = (ImageView) itemView.findViewById(R.id.home_share_text);
            this.mListentext = (ImageView) itemView.findViewById(R.id.home_listen_text);
        }

    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {


        TextView txtType;
        ImageView mMainImage, mHeadImage;
        ImageView mDownloadButton;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            //  this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.mMainImage = (ImageView) itemView.findViewById(R.id.home_main_image);
            this.mHeadImage = (ImageView) itemView.findViewById(R.id.home_head_image);
            this.mDownloadButton = (ImageView) itemView.findViewById(R.id.home_main_downloadImage);

            //mDownloadButton.setOnClickListener(this);
        }


    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class AudioTypeViewHolder extends RecyclerView.ViewHolder {


        TextView txtType;
        FloatingActionButton fab;

        public AudioTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.fab = (FloatingActionButton) itemView.findViewById(R.id.fab);

        }

    }

    public static class GifTypeViewHolder extends RecyclerView.ViewHolder {


        TextView txtType;
        ImageView mGifImage, mGifHeadImage, mDownloadGif, mShareGif;
        FloatingActionButton fab;

        public GifTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.mGifImage = (ImageView) itemView.findViewById(R.id.home_main_gif_image);
            this.mGifHeadImage = (ImageView) itemView.findViewById(R.id.home_gif_head_image);
            this.mDownloadGif = (ImageView) itemView.findViewById(R.id.home_download_gif);
            this.mShareGif = (ImageView) itemView.findViewById(R.id.home_share_gif);
            this.fab = (FloatingActionButton) itemView.findViewById(R.id.fab);

        }

    }

    public MultiViewAdapter(ArrayList<Model> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();

    }

    /*public MultiViewAdapter(Context context, String[] name , String[] urls , int [] viewtypes)
    {
        this.mContext = context;
        int check = urls.length -1;
        models = new ArrayList<Model>();
        for(int i = 0 ;i<check ;i++)
        {
            Model datamodel = new Model();
            datamodel.setName(name[i]);
            datamodel.setUrl(urls[i]);
            datamodel.setType(viewtypes[i]);
            models.add(datamodel);

        }


    }
*/


    /*@Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return Model.TEXT_TYPE;
            case 1:
                return Model.IMAGE_TYPE;
            case 2:
                return Model.AUDIO_TYPE;
            default:
                return -1;
        }


    }*/
    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return Model.TEXT_TYPE;
            case 1:
                return Model.IMAGE_TYPE;
            case 2:
                return Model.AUDIO_TYPE;
            default:
                return -1;
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Model.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                return new TextTypeViewHolder(view);
            case Model.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
                return new ImageTypeViewHolder(view);
            case Model.AUDIO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_type, parent, false);
                return new GifTypeViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final Model object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case Model.TEXT_TYPE:
                    //   ((TextTypeViewHolder) holder).txtType.setText(object.text);
                    ((TextTypeViewHolder) holder).txtType.setText(dataSet.get(listPosition).getName());
                    View.OnClickListener textTypeListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onHomeImageViewClick.onItemClick(v,object);
                        }
                    };


                     ((TextTypeViewHolder) holder).mListentext.setOnClickListener(textTypeListener);
                    ((TextTypeViewHolder) holder).mSharetext.setOnClickListener(textTypeListener);

                    break;
                case Model.IMAGE_TYPE:
                    //          ((ImageTypeViewHolder) holder).txtType.setText(object.getUrl());
                    //     ((ImageTypeViewHolder) holder).image.setImageResource(object.data);
                    ((ImageTypeViewHolder) holder).mMainImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getUrl()).centerCrop().placeholder(R.drawable.placeholder).into(((ImageTypeViewHolder) holder).mMainImage);
                    ((ImageTypeViewHolder) holder).mHeadImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getUrl()).transform(new CircleTransform(mContext)).into(((ImageTypeViewHolder) holder).mHeadImage);


                    //     private OnItemClickListener onItemClickListener;
                    View.OnClickListener imageTypeListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        //    onItemClickListener.onItemClick(object);

                            onHomeImageViewClick.onItemClick(v,object);
                        }
                    };
                    ((ImageTypeViewHolder) holder).mDownloadButton.setOnClickListener(imageTypeListener);

                    ((ImageTypeViewHolder) holder).mMainImage.setOnClickListener(imageTypeListener);
                    break;
                case Model.AUDIO_TYPE:
                    ((GifTypeViewHolder) holder).mGifImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getUrl()).asGif().placeholder(R.drawable.placeholder).centerCrop().into(((GifTypeViewHolder) holder).mGifImage);
                    ((GifTypeViewHolder) holder).mGifHeadImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getUrl()).transform(new CircleTransform(mContext)).into(((GifTypeViewHolder) holder).mGifHeadImage);
                    View.OnClickListener gifTypeListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onHomeImageViewClick.onItemClick(v,object);
                        }
                    };
                    ((GifTypeViewHolder) holder).mDownloadGif.setOnClickListener(gifTypeListener);
                    ((GifTypeViewHolder) holder).mShareGif.setOnClickListener(gifTypeListener);
/*
                    ((AudioTypeViewHolder) holder).txtType.setText(object.text);


                    ((AudioTypeViewHolder) holder).fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (fabStateVolume) {
                                if (mPlayer.isPlaying()) {
                                    mPlayer.stop();

                                }
                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.volume);
                                fabStateVolume = false;

                            } else {
                                mPlayer = MediaPlayer.create(mContext, R.raw.sound);
                                mPlayer.setLooping(true);
                                mPlayer.start();
                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.mute);
                                fabStateVolume = true;

                            }
                        }
                    });
*/


                    break;
            }
        }

        if (listPosition > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.bottom_from_up);
            holder.itemView.startAnimation(animation);
            lastPosition = listPosition;
        } else {

            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.up_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition = listPosition;
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}

