package com.dreamworld.craic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamworld.craic.R;
import com.dreamworld.craic.activity.CommentActivity;
import com.dreamworld.craic.configuration.CommentConfiguration;
import com.dreamworld.craic.model.CommentModel;
import com.dreamworld.craic.model.CreateList;
import com.dreamworld.craic.transformshape.CircleTransform;

import java.util.ArrayList;

/**
 * Created by faizan on 28/12/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
CommentModel commentModel ;
    private ArrayList<CommentModel> commentModelArray;
    private Context context;


    public CommentAdapter() {


    }

    public CommentAdapter(ArrayList<CommentModel> commentModels, CommentActivity commentActivity) {
    this.commentModelArray =commentModels ;
    this.context = commentActivity ;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View commentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_recycle_view,parent, false);

        return new ViewHolder(commentView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        String username = commentModelArray.get(position).getUserFullName();
        String userComment = commentModelArray.get(position).getUserComment();


        holder.userNameComment.setText(username +"  "+ userComment );
        holder.userNameComment.getAutoLinkMask();
        holder.userNameComment.setText(commentModelArray.get(position).getUserComment());
        Glide.with(this.context).load(commentModelArray.get(position).getProfilePic()).transform(new CircleTransform(context)).into(holder.profileImage);


    }


    @Override
    public int getItemCount() {
        return commentModelArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameComment ,username ,userCommentTime;
        private ImageView profileImage;


        public ViewHolder(View itemView) {
            super(itemView);
      //      username = (TextView)itemView.findViewById(R.id.comment_);
            userNameComment = (TextView)itemView.findViewById(R.id.comment_username_comment);
            userCommentTime = (TextView)itemView.findViewById(R.id.comment_usercommenttime);
            profileImage = (ImageView)itemView.findViewById(R.id.comment_userimage);
            //userCommentTime

        }
    }
}
