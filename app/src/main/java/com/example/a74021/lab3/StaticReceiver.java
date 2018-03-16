package com.example.a74021.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;

import static android.graphics.drawable.Icon.createWithBitmap;
import static com.example.a74021.lab3.MainActivity.page;

/**
 * Created by 74021 on 2017/10/31.
 */

public class StaticReceiver extends BroadcastReceiver{
    @Override
    public  void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("STATICACTION")){
            final item p=(item) intent.getSerializableExtra("item");
            final int resID=(int)intent.getSerializableExtra("resID");

            //获取状态通知栏管理
            NotificationManager manager=(NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder builder=new Notification.Builder(context);
            builder.setContentTitle("马上下单")
                    .setContentText(p.getItemName()+"仅售"+p.getItemPrice()+"!")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                   // .setLargeIcon(p.getBitmap())
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(false);



            //绑定intent，点击图标进入活动
            Intent mIntent=new Intent(context,infoActivity.class);
            mIntent.addCategory(Intent.CATEGORY_DEFAULT);
            mIntent.putExtra("item",p);
            PendingIntent mPendingIntent=PendingIntent.getActivity(context,0,mIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(mPendingIntent);
            //绑定Notification，发送通知请求
            Notification notify=builder.build();
            manager.notify(0,notify);

        }
    }

}
