package com.example.a74021.lab3;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.actionModeFindDrawable;
import static android.R.attr.itemPadding;
import static android.R.attr.tag;
import static android.R.attr.zAdjustment;
import static com.example.a74021.lab3.R.drawable._arla;


/**
 * Created by 74021 on 2017/10/27.
 */

public class infoActivity extends MainActivity{
    item p;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_info);

        //get item information
        if (getIntent().getSerializableExtra("wItem")!=null)
        {
            p=(item) getIntent().getSerializableExtra("wItem");
        }
        else {
            p = (item) getIntent().getSerializableExtra("item");
        }


        TextView textView = (TextView) findViewById(R.id.image);
        String img = p.getImageName();
        final int resID = getResources().getIdentifier(img,"drawable", this.getPackageName());
        textView.setBackgroundResource(resID);

     //返回处理
        Button back = (Button) findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }

        });

        //界面布置
        TextView name = (TextView) findViewById(R.id.nameInInfo);
        name.setText(p.getItemName());
        TextView price = (TextView) findViewById(R.id.priceInInfo);
        price.setText(p.getItemPrice());
        TextView type = (TextView) findViewById(R.id.type);
        type.setText(p.getItemType());
        TextView value = (TextView) findViewById(R.id.value);
        value.setText(p.getItemValue());


        String[] operations2=new String[]{"更多商品信息","一键下单", "分享商品", "不感兴趣", "查看更多商品促销信息"};
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,R.layout.more,operations2);
        ListView listView2 =(ListView)findViewById(R.id.more);
        listView2.setAdapter(arrayAdapter2);

        Button button=(Button)findViewById(R.id.addTOList);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //普通方法
                //adapter.setItemList(Infos1);
                //adapter.notifyDataSetChanged();
                Toast toast=Toast.makeText(getApplicationContext(),"该商品已经添加到购物车",Toast.LENGTH_SHORT);
                toast.show();


                //eventbus方法
                EventBus.getDefault().postSticky(p);

                //动态广播
                //注册
                DynamicReceiver dynamicReceiver=new DynamicReceiver();
                IntentFilter dynamic_filter=new IntentFilter();
                dynamic_filter.addAction("dynamic");
                registerReceiver(dynamicReceiver,dynamic_filter);
                //发送
                Intent dynamicBroadcast=new Intent("dynamic");
                dynamicBroadcast.putExtra("resID",resID);
                dynamicBroadcast.putExtra("item",p);
                sendBroadcast(dynamicBroadcast);
                //注销
                unregisterReceiver(dynamicReceiver);
                 //WIDGET动态广播
                DynamicReceiver dynamicReceiver1=new DynamicReceiver();
                IntentFilter dynamic_filter1=new IntentFilter();
                dynamic_filter.addAction("widget_d");
                registerReceiver(dynamicReceiver,dynamic_filter);
                //发送
                Intent Broadcast1=new Intent("widget_d");
                Broadcast1.putExtra("resID",resID);
                Broadcast1.putExtra("item",p);
                sendBroadcast(Broadcast1);
            }
        });


    //星星图标
  final Button star=(Button)findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(!tag){
                star.setBackgroundResource(R.drawable.full_star);
                tag=true;
            }
            else
            {
                star.setBackgroundResource(R.drawable.empty_star);
                tag=false;
            }
        }
    });

    }
    private boolean tag=false;
}

