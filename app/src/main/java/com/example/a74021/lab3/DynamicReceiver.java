package com.example.a74021.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.widget.RemoteViews;
import  com.example.a74021.lab3.MainActivity;

import org.greenrobot.eventbus.EventBus;

import static com.example.a74021.lab3.MainActivity.adapter;
import static com.example.a74021.lab3.MainActivity.page;

/**
 * Created by 74021 on 2017/11/1.
 */

public class DynamicReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("dynamic"))
        {
            item p=(item)intent.getSerializableExtra("item");
            int resID=(int)intent.getSerializableExtra("resID");
            NotificationManager manager=(NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder builder=new Notification.Builder(context);
            builder.setContentTitle(p.getItemName())
                    .setContentText("该商品 已经添加到购物车")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                //  .setLargeIcon()
                    .setAutoCancel(false);

            //绑定intent，点击图标进入活动
/*            Intent mIntent=new Intent(context,trolley.class);
            PendingIntent mPendingIntent=PendingIntent.getActivity(context,0,mIntent,0);
            builder.setContentIntent(mPendingIntent);*/
            //绑定Notification，发送通知请求
            Notification notify=builder.build();
            manager.notify(0,notify);

        }
        if(intent.getAction().equals("widget_d"))
        {
            item p=(item)intent.getSerializableExtra("item");
            int resID=(int)intent.getSerializableExtra("resID");
            RemoteViews view=new RemoteViews(context.getPackageName(),R.layout.m_widget);
            view.setTextViewText(R.id.appwidget_text,p.getItemName()+"已经添加到购物车");
            view.setImageViewResource(R.id.appwidget_img,resID);
            pageEvent pageEvent=new pageEvent(1);
            EventBus.getDefault().postSticky(pageEvent);
            Intent intent1=new Intent(context,MainActivity.class);
            PendingIntent pi=PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
            view.setOnClickPendingIntent(R.id.appwidget_img,pi);
           AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(new ComponentName(context, mWidget.class), view);
        }
    }
}
