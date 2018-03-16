package com.example.a74021.lab3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 74021 on 2017/10/29.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;//储存list_item的子view
    private View mConvertView;//存储list_item
    public ViewHolder(Context context, View itemView, ViewGroup parent){
        super(itemView);
        mConvertView = itemView;
        mViews=new SparseArray<View>();
    }
    public static ViewHolder get(Context context,ViewGroup parent,int layoutId)
    {
        View itemView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder holder=new ViewHolder(context,itemView,parent);
        return holder;
    }
    public <T extends View> T getView(int viewId)
    {
        View view=mViews.get(viewId);
        if(view==null)
        {
            //创建view
            view=mConvertView.findViewById(viewId);
            //将view存入mView
            mViews.put(viewId,view);
        }
        return (T)view;
    }



}

