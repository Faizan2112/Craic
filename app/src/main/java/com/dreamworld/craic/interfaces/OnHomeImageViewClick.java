package com.dreamworld.craic.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dreamworld.craic.model.CreateList;
import com.dreamworld.craic.model.Model;
import com.dreamworld.craic.model.PostDetail;

import java.util.concurrent.ExecutionException;

/**
 * Created by faizan on 23/08/2017.
 */

 public interface OnHomeImageViewClick {
    void onItemClick(RecyclerView.ViewHolder mv , View fullScreenImage , PostDetail itemPostion) throws ExecutionException, InterruptedException;


}
