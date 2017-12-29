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
import com.dreamworld.craic.interfaces.OnHomeImageViewClick;
import com.dreamworld.craic.interfaces.OnItemClickListener;
import com.dreamworld.craic.model.Model;
import com.dreamworld.craic.model.PostDetail;
import com.dreamworld.craic.transformshape.CircleTransform;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.dreamworld.craic.fragments.AllContentFragment.saveLikeid;

//import static com.dreamworld.craic.MainActivity.saveLikeid;

/**
 * Created by faizan on 15/11/2017.
 */

public class PostDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<PostDetail> dataSet;
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
        ImageView mSharetext, mListentext, mHeadImage,mLikeImage, mUnlike,mComment;


        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.mMaintext = (TextView) itemView.findViewById(R.id.home_main_text);

            this.mComment = (ImageView) itemView.findViewById(R.id.home_comment);
            //   this.textLinearlayout = (LinearLayout) itemView.findViewById(R.id.home_main_text_type);
            this.mSharetext = (ImageView) itemView.findViewById(R.id.home_share);
          //  this.mListentext = (ImageView) itemView.findViewById(R.id.home_listen_text);
            this.mHeadImage = (ImageView) itemView.findViewById(R.id.home_text_head_image);
            this.mLikes = (TextView) itemView.findViewById(R.id.home_likes);
            this.mDate = (TextView) itemView.findViewById(R.id.home_date);
            this.mHeadText = (TextView) itemView.findViewById(R.id.home_head_text_username);
            this.mLikeImage = (ImageView) itemView.findViewById(R.id.home_image_like);
            this.mUnlike = (ImageView) itemView.findViewById(R.id.home_image_unlike);



        }

    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {


        TextView mHeadText, mDate, mLikes;

        ImageView mMainImage, mHeadImage,mComment;
        ImageView mDownloadButton;
        ImageView mShareImage, mLikeImage, mUnlike;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            //  this.txtType = (TextView) itemView.findViewById(R.id.type);
         //   this.mComment = (ImageView) itemView.findViewById(R.id.home_comment);
            this.mMainImage = (ImageView) itemView.findViewById(R.id.home_main_image);
            this.mHeadImage = (ImageView) itemView.findViewById(R.id.home_head_image);
            this.mDownloadButton = (ImageView) itemView.findViewById(R.id.home_main_downloadImage);
            this.mShareImage = (ImageView) itemView.findViewById(R.id.home_share);
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
            this.mGifImage = (ImageView) itemView.findViewById(R.id.home_main_image);
            this.mGifHeadImage = (ImageView) itemView.findViewById(R.id.home_gif_head_image);
            this.mDownloadGif = (ImageView) itemView.findViewById(R.id.home_main_downloadImage);
            this.mShareGif = (ImageView) itemView.findViewById(R.id.home_gif_share);
            // this.fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
       //     this.mLikes = (TextView) itemView.findViewById(R.id.home_likes);
            this.mDate = (TextView) itemView.findViewById(R.id.home_date);
            this.mHeadText = (TextView) itemView.findViewById(R.id.home_head_text);
            this.mLikeImage = (ImageView) itemView.findViewById(R.id.home_image_like);
            this.mUnlike = (ImageView) itemView.findViewById(R.id.home_image_unlike);



        }

    }

    public PostDataAdapter(ArrayList<PostDetail> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();

    }


    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).getViewtype()) {
            case 0:
                return PostDetail.TEXT_TYPE;
            case 1:
                return PostDetail.IMAGE_TYPE;
            case 2:
                return PostDetail.AUDIO_TYPE;
            default:
                return -1;
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case PostDetail.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                return new PostDataAdapter.TextTypeViewHolder(view);
            case PostDetail.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
                return new PostDataAdapter.ImageTypeViewHolder(view);
            case PostDetail.AUDIO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_type, parent, false);
                return new PostDataAdapter.GifTypeViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
        int pos = holder.getAdapterPosition();
        final PostDetail object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.getViewtype()) {
                case PostDetail.TEXT_TYPE:
                    changeDataSet(pos, holder);
                    //   ((TextTypeViewHolder) holder).txtType.setText(object.text);
                    ((PostDataAdapter.TextTypeViewHolder) holder).mMaintext.setText(dataSet.get(listPosition).getArticlesummary());
                    ((PostDataAdapter.TextTypeViewHolder) holder).mHeadText.setText(dataSet.get(listPosition).getTitel());
                    ((PostDataAdapter.TextTypeViewHolder) holder).mDate.setText(dataSet.get(listPosition).getDate());
                    //TextTypeViewHolder) holder).mHeadImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getPost_icon()).transform(new CircleTransform(mContext)).into(((PostDataAdapter.TextTypeViewHolder) holder).mHeadImage);
        //            ((PostDataAdapter.TextTypeViewHolder) holder).mLikes.setText((String.valueOf(dataSet.get(listPosition).getLikes())));

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


               //     ((PostDataAdapter.TextTypeViewHolder) holder).mListentext.setOnClickListener(textTypeListener);
                    ((PostDataAdapter.TextTypeViewHolder) holder).mSharetext.setOnClickListener(textTypeListener);
                    ((PostDataAdapter.TextTypeViewHolder) holder).mLikeImage.setOnClickListener(textTypeListener);
                    ((PostDataAdapter.TextTypeViewHolder) holder).mUnlike.setOnClickListener(textTypeListener);
                    ((TextTypeViewHolder) holder).mComment.setOnClickListener(textTypeListener);

                    break;
                case PostDetail.IMAGE_TYPE:
                    //          ((ImageTypeViewHolder) holder).txtType.setText(object.getUrl());
                    //     ((ImageTypeViewHolder) holder).image.setImageResource(object.data);


                    changeDataSet(pos, holder);



                 //   ((PostDataAdapter.ImageTypeViewHolder) holder).mMainImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getMainimageurl()).fitCenter().placeholder(R.drawable.placeholder).into(((PostDataAdapter.ImageTypeViewHolder) holder).mMainImage);
                  //  ((PostDataAdapter.ImageTypeViewHolder) holder).mHeadImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getPost_icon()).transform(new CircleTransform(mContext)).into(((PostDataAdapter.ImageTypeViewHolder) holder).mHeadImage);
                    ((PostDataAdapter.ImageTypeViewHolder) holder).mHeadText.setText(dataSet.get(listPosition).getTitel());
                    ((PostDataAdapter.ImageTypeViewHolder) holder).mDate.setText(dataSet.get(listPosition).getDate());
                    ((PostDataAdapter.ImageTypeViewHolder) holder).mLikes.setText((String.valueOf(dataSet.get(listPosition).getLikes())));
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
                    ((PostDataAdapter.ImageTypeViewHolder) holder).mDownloadButton.setOnClickListener(imageTypeListener);

                    ((PostDataAdapter.ImageTypeViewHolder) holder).mMainImage.setOnClickListener(imageTypeListener);
                    ((PostDataAdapter.ImageTypeViewHolder) holder).mShareImage.setOnClickListener(imageTypeListener);
                    ((PostDataAdapter.ImageTypeViewHolder) holder).mLikeImage.setOnClickListener(imageTypeListener);
                    ((PostDataAdapter.ImageTypeViewHolder) holder).mUnlike.setOnClickListener(imageTypeListener);
                    break;
                case PostDetail.AUDIO_TYPE:
                    changeDataSet(pos, holder);
                //    ((PostDataAdapter.GifTypeViewHolder) holder).mGifImage.setImageResource(object.);
                    Glide.with(mContext).load(dataSet.get(listPosition).getMainimageurl()).asGif().placeholder(R.drawable.placeholder).centerCrop().into(((PostDataAdapter.GifTypeViewHolder) holder).mGifImage);
                   // ((PostDataAdapter.GifTypeViewHolder) holder).mGifHeadImage.setImageResource(object.data);
                    Glide.with(mContext).load(dataSet.get(listPosition).getMainimageurl()).transform(new CircleTransform(mContext)).into(((PostDataAdapter.GifTypeViewHolder) holder).mGifHeadImage);
                    ((PostDataAdapter.GifTypeViewHolder) holder).mHeadText.setText(dataSet.get(listPosition).getTitel());
                    ((PostDataAdapter.GifTypeViewHolder) holder).mDate.setText(dataSet.get(listPosition).getDate());
                    ((PostDataAdapter.GifTypeViewHolder) holder).mLikes.setText((String.valueOf(dataSet.get(listPosition).getLikes())));

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
                    ((PostDataAdapter.GifTypeViewHolder) holder).mDownloadGif.setOnClickListener(gifTypeListener);
                    ((PostDataAdapter.GifTypeViewHolder) holder).mShareGif.setOnClickListener(gifTypeListener);
                    ((PostDataAdapter.GifTypeViewHolder) holder).mLikeImage.setOnClickListener(gifTypeListener);
                    ((PostDataAdapter.GifTypeViewHolder) holder).mUnlike.setOnClickListener(gifTypeListener);
                    ((PostDataAdapter.GifTypeViewHolder) holder).mGifImage.setOnClickListener(gifTypeListener);




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
        String findImagePostion = dataSet.get(pos).getPost_id();
        if(saveLikeid != null){
            for (int i = 0; i < saveLikeid.size(); i++) {
                //saveLikeid.contains(findImagePostion);

                boolean vl = saveLikeid.contains(String.valueOf(findImagePostion));
                if (vl) {


                    int viewTypeid = holder.getItemViewType();
                    if(viewTypeid == 0) {
                        if (((PostDataAdapter.TextTypeViewHolder) holder).mLikeImage.getVisibility() == View.VISIBLE) {
                            ((PostDataAdapter.TextTypeViewHolder) holder).mLikeImage.setVisibility(View.GONE);

                            if (((PostDataAdapter.TextTypeViewHolder) holder).mLikeImage.getVisibility() == View.GONE) {

                                ((PostDataAdapter.TextTypeViewHolder) holder).mUnlike.setVisibility(View.VISIBLE);

                                break;


                            }


                        }
                    }
                    if(viewTypeid == 1) {
                        if (((PostDataAdapter.ImageTypeViewHolder) holder).mLikeImage.getVisibility() == View.VISIBLE) {
                            ((PostDataAdapter.ImageTypeViewHolder) holder).mLikeImage.setVisibility(View.GONE);

                            if (((PostDataAdapter.ImageTypeViewHolder) holder).mLikeImage.getVisibility() == View.GONE) {

                                ((PostDataAdapter.ImageTypeViewHolder) holder).mUnlike.setVisibility(View.VISIBLE);

                                break;


                            }


                        }
                    }

                    if(viewTypeid == 2) {
                        if (((PostDataAdapter.GifTypeViewHolder) holder).mLikeImage.getVisibility() == View.VISIBLE) {
                            ((PostDataAdapter.GifTypeViewHolder) holder).mLikeImage.setVisibility(View.GONE);

                            if (((PostDataAdapter.GifTypeViewHolder) holder).mLikeImage.getVisibility() == View.GONE) {

                                ((PostDataAdapter.GifTypeViewHolder) holder).mUnlike.setVisibility(View.VISIBLE);

                                break;


                            }


                        }
                    }

                }
            }}
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
