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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamworld.craic.R;
import com.dreamworld.craic.interfaces.OnItemClickListener;
import com.dreamworld.craic.model.Model;
import com.dreamworld.craic.transformshape.CircleTransform;
import com.dreamworld.craic.interfaces.OnHomeImageViewClick;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.dreamworld.craic.MainActivity.imagePos;
import static com.dreamworld.craic.MainActivity.saveLikeid;

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
    public static Set<String> demo;
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


        TextView mHeadText, mDate, mLikes, mMaintext;
        LinearLayout textLinearlayout;
        ImageView mSharetext, mListentext, mHeadImage,mLikeImage, mUnlike;


        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.mMaintext = (TextView) itemView.findViewById(R.id.home_main_text);
            this.textLinearlayout = (LinearLayout) itemView.findViewById(R.id.home_main_text_type);
            this.mSharetext = (ImageView) itemView.findViewById(R.id.home_share_text);
            this.mListentext = (ImageView) itemView.findViewById(R.id.home_listen_text);
            this.mHeadImage = (ImageView) itemView.findViewById(R.id.home_text_head_image);
            this.mLikes = (TextView) itemView.findViewById(R.id.home_likes);
            this.mDate = (TextView) itemView.findViewById(R.id.home_date);
            this.mHeadText = (TextView) itemView.findViewById(R.id.home_head_text);
            this.mLikeImage = (ImageView) itemView.findViewById(R.id.home_image_like);
            this.mUnlike = (ImageView) itemView.findViewById(R.id.home_image_unlike);



        }

    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {


        TextView mHeadText, mDate, mLikes;

        ImageView mMainImage, mHeadImage;
        ImageView mDownloadButton;
        ImageView mShareImage, mLikeImage, mUnlike;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            //  this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.mMainImage = (ImageView) itemView.findViewById(R.id.home_main_image);
            this.mHeadImage = (ImageView) itemView.findViewById(R.id.home_head_image);
            this.mDownloadButton = (ImageView) itemView.findViewById(R.id.home_main_downloadImage);
            this.mShareImage = (ImageView) itemView.findViewById(R.id.home_image_share);
            this.mLikeImage = (ImageView) itemView.findViewById(R.id.home_image_like);
            this.mUnlike = (ImageView) itemView.findViewById(R.id.home_image_unlike);
            this.mLikes = (TextView) itemView.findViewById(R.id.home_likes);
            this.mDate = (TextView) itemView.findViewById(R.id.home_date);
            this.mHeadText = (TextView) itemView.findViewById(R.id.home_head_text);

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


        TextView mHeadText, mDate, mLikes, txtType;

        ImageView mGifImage, mGifHeadImage, mDownloadGif, mShareGif ,mLikeImage, mUnlike;
        FloatingActionButton fab;

        public GifTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.mGifImage = (ImageView) itemView.findViewById(R.id.home_main_gif_image);
            this.mGifHeadImage = (ImageView) itemView.findViewById(R.id.home_gif_head_image);
            this.mDownloadGif = (ImageView) itemView.findViewById(R.id.home_download_gif);
            this.mShareGif = (ImageView) itemView.findViewById(R.id.home_share_gif);
            this.fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
            this.mLikes = (TextView) itemView.findViewById(R.id.home_likes);
            this.mDate = (TextView) itemView.findViewById(R.id.home_date);
            this.mHeadText = (TextView) itemView.findViewById(R.id.home_head_text);
            this.mLikeImage = (ImageView) itemView.findViewById(R.id.home_image_like);
            this.mUnlike = (ImageView) itemView.findViewById(R.id.home_image_unlike);



        }

    }

    public MultiViewAdapter(ArrayList<Model> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();

    }


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
        int pos = holder.getAdapterPosition();
        final Model object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case Model.TEXT_TYPE:
                    changeDataSet(pos, holder);
                    //   ((TextTypeViewHolder) holder).txtType.setText(object.text);
                    ((TextTypeViewHolder) holder).mMaintext.setText(dataSet.get(listPosition).getName());
                    ((TextTypeViewHolder) holder).mHeadText.setText(dataSet.get(listPosition).getHeadTitel());
                    ((TextTypeViewHolder) holder).mDate.setText(dataSet.get(listPosition).getDate());
                    //TextTypeViewHolder) holder).mHeadImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getHeadImage()).transform(new CircleTransform(mContext)).into(((TextTypeViewHolder) holder).mHeadImage);
                    ((TextTypeViewHolder) holder).mLikes.setText((String.valueOf(dataSet.get(listPosition).getLikes())));

                    View.OnClickListener textTypeListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                onHomeImageViewClick.onItemClick(holder, v, object);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };


                    ((TextTypeViewHolder) holder).mListentext.setOnClickListener(textTypeListener);
                    ((TextTypeViewHolder) holder).mSharetext.setOnClickListener(textTypeListener);
                    ((TextTypeViewHolder) holder).mLikeImage.setOnClickListener(textTypeListener);
                    ((TextTypeViewHolder) holder).mUnlike.setOnClickListener(textTypeListener);

                    break;
                case Model.IMAGE_TYPE:
                    //          ((ImageTypeViewHolder) holder).txtType.setText(object.getUrl());
                    //     ((ImageTypeViewHolder) holder).image.setImageResource(object.data);


                    changeDataSet(pos, holder);



                    ((ImageTypeViewHolder) holder).mMainImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getUrl()).fitCenter().placeholder(R.drawable.placeholder).into(((ImageTypeViewHolder) holder).mMainImage);
                    ((ImageTypeViewHolder) holder).mHeadImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getHeadImage()).transform(new CircleTransform(mContext)).into(((ImageTypeViewHolder) holder).mHeadImage);
                     ((ImageTypeViewHolder) holder).mHeadText.setText(dataSet.get(listPosition).getHeadTitel());
                    ((ImageTypeViewHolder) holder).mDate.setText(dataSet.get(listPosition).getDate());
                   ((ImageTypeViewHolder) holder).mLikes.setText((String.valueOf(dataSet.get(listPosition).getLikes())));
                    //     private OnItemClickListener onItemClickListener;
                    View.OnClickListener imageTypeListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //    onItemClickListener.onItemClick(object);

                            try {
                                onHomeImageViewClick.onItemClick(holder, v, object);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ((ImageTypeViewHolder) holder).mDownloadButton.setOnClickListener(imageTypeListener);

                    ((ImageTypeViewHolder) holder).mMainImage.setOnClickListener(imageTypeListener);
                    ((ImageTypeViewHolder) holder).mShareImage.setOnClickListener(imageTypeListener);
                    ((ImageTypeViewHolder) holder).mLikeImage.setOnClickListener(imageTypeListener);
                    ((ImageTypeViewHolder) holder).mUnlike.setOnClickListener(imageTypeListener);
                    break;
                case Model.AUDIO_TYPE:
                    changeDataSet(pos, holder);
                    ((GifTypeViewHolder) holder).mGifImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getUrl()).asGif().placeholder(R.drawable.placeholder).centerCrop().into(((GifTypeViewHolder) holder).mGifImage);
                    ((GifTypeViewHolder) holder).mGifHeadImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getHeadImage()).transform(new CircleTransform(mContext)).into(((GifTypeViewHolder) holder).mGifHeadImage);
                    ((GifTypeViewHolder) holder).mHeadText.setText(dataSet.get(listPosition).getHeadTitel());
                    ((GifTypeViewHolder) holder).mDate.setText(dataSet.get(listPosition).getDate());
                   ((GifTypeViewHolder) holder).mLikes.setText((String.valueOf(dataSet.get(listPosition).getLikes())));

                    View.OnClickListener gifTypeListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                onHomeImageViewClick.onItemClick(holder, v, object);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ((GifTypeViewHolder) holder).mDownloadGif.setOnClickListener(gifTypeListener);
                    ((GifTypeViewHolder) holder).mShareGif.setOnClickListener(gifTypeListener);
                    ((GifTypeViewHolder) holder).mLikeImage.setOnClickListener(gifTypeListener);
                    ((GifTypeViewHolder) holder).mUnlike.setOnClickListener(gifTypeListener);




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

    private void changeDataSet(int pos, RecyclerView.ViewHolder holder) {
        int findImagePostion = dataSet.get(pos).getImageId();

        for (int i = 0; i < saveLikeid.size(); i++) {
            //saveLikeid.contains(findImagePostion);

            boolean vl = saveLikeid.contains(String.valueOf(findImagePostion));
            if (vl) {


            int viewTypeid = holder.getItemViewType();
                if(viewTypeid == 0) {
                    if (((TextTypeViewHolder) holder).mLikeImage.getVisibility() == View.VISIBLE) {
                        ((TextTypeViewHolder) holder).mLikeImage.setVisibility(View.GONE);

                        if (((TextTypeViewHolder) holder).mLikeImage.getVisibility() == View.GONE) {

                            ((TextTypeViewHolder) holder).mUnlike.setVisibility(View.VISIBLE);

                            break;


                        }


                    }
                }
                if(viewTypeid == 1) {
                    if (((ImageTypeViewHolder) holder).mLikeImage.getVisibility() == View.VISIBLE) {
                        ((ImageTypeViewHolder) holder).mLikeImage.setVisibility(View.GONE);

                        if (((ImageTypeViewHolder) holder).mLikeImage.getVisibility() == View.GONE) {

                            ((ImageTypeViewHolder) holder).mUnlike.setVisibility(View.VISIBLE);

                            break;


                        }


                    }
                }

                if(viewTypeid == 2) {
                    if (((GifTypeViewHolder) holder).mLikeImage.getVisibility() == View.VISIBLE) {
                        ((GifTypeViewHolder) holder).mLikeImage.setVisibility(View.GONE);

                        if (((GifTypeViewHolder) holder).mLikeImage.getVisibility() == View.GONE) {

                            ((GifTypeViewHolder) holder).mUnlike.setVisibility(View.VISIBLE);

                            break;


                        }


                    }
                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


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