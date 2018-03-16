package com.example.a74021.lab3;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.IDNA;
import android.net.sip.SipAudioCall;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity {
    private static int count=0;

    static int page=0;
    ListView mListView;
    static mAdapter adapter;
    static int i=-1;
    private FloatingActionButton floatingActionButton;
    //itemList of Trolley
    final  List<item>Infos1= new ArrayList<item>() {{
        add(new item("购物车","价格","类别","值","图片名"));
    }};
    //itemList  of  shoppingList
    final  List<item> Infos =new ArrayList<item>(){{
        add(new item("Enchanted Forest","$5.00","作者","Johanna Bsford","_enchated_forest"));
        add(new item("Arla Milk","$59.00","产地","德国","_arla"));
        add(new item("Deveondale Milk","$79.00","产地","澳大利亚","_devondale"));
        add(new item("Kindle Oasis","$2399.00","版本","8GB","kindle"));
        add(new item("waitrose 早餐麦片","$179","重量","2kg","waitrose"));
        add(new item("Mcvitie's 饼干","￥14.90","产地","英国","_mcvitie"));
        add(new item("Ferrero Rocher","￥132.59","重量","300g","_ferrero"));
        add(new item("Maltesers","￥141.43","重量","118g","_maltesers"));
        add(new item("Lindt","￥139.43","重量","249g","_lindt"));
        add(new item("Borggreve","￥28.90","重量","640g" ,"_borggreve"));
    }};



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //输入商品所有信息
        mListView= (ListView) findViewById(R.id.shoppinglist);
        //RecyclerView mRecyclerView=(RecyclerView)findViewById(R.id.recyclerList);
        //RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(mLayoutManager);
        if(getIntent().getSerializableExtra("page")!=null)
        {
           page=(int)getIntent().getSerializableExtra("page");
            if (page==0) {
                adapter = new mAdapter(this, Infos);
            }
            else
            {
                adapter=new mAdapter(this,Infos1);
            }
            mListView.setAdapter(adapter);
        }
        //第一次创建时确定显示的页面
        if (page==0) {
            adapter = new mAdapter(this, Infos);
        }
        else
        {
            adapter=new mAdapter(this,Infos1);
        }
        mListView.setAdapter(adapter);

        //adapter and trolley





        //***********************************************************************************






      //Broadcast广播
        Random random=new Random();
        i=random.nextInt(8);
        item mitem=Infos.get(i);
        String img = mitem.getImageName();
        int resID = getResources().getIdentifier(img,"drawable", this.getPackageName());
        //widget静态广播
        Bundle bundle=new Bundle();
        Intent intentWidget=new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intentWidget.putExtra("itemIndex",i);
        intentWidget.putExtra("resID",resID);
        intentWidget.putExtra("item",mitem);
        sendBroadcast(intentWidget);

        //lab4 静态广播
        Intent intentBroadcast=new Intent("STATICACTION");
        intentBroadcast.putExtra("resID", resID);
        intentBroadcast.putExtra("item",mitem);
        sendBroadcast(intentBroadcast);



        //sendBroadcast(intentWidget);
        //单击
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int i,long l){
                Intent intent=new Intent(MainActivity.this,infoActivity.class);
                item temp;
                if(page==0) {
                    temp = Infos.get(i);
                }
                else
                {
                    temp=Infos1.get(i);
                }
               intent.putExtra("item",temp);
                startActivity(intent);
            }
        });

       //长按
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public  boolean onItemLongClick(AdapterView<?> parent,View view,final int position,long id){
                AlertDialog.Builder message=new AlertDialog.Builder(MainActivity.this);
                message.setTitle("移除商品");
                message.setMessage("确定移除商品？");
                message.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface,int i){
                    }});
                message.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface,int i){
                        if(page==0) {
                            Infos.remove(position);
                            adapter.setItemList(Infos);
                        }
                        else{
                            Infos1.remove(position);
                            adapter.setItemList(Infos1);
                        }
                        mListView.setAdapter(adapter);
                    }});

                message.create().show();
                return true;
            }
            }
        );

        floatingActionButton=(FloatingActionButton)findViewById(R.id.float_btn);
        floatingActionButton.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v){
                if(page==0)
                {
                    floatingActionButton.setImageResource(R.drawable.mainpage);
                    adapter.setItemList(Infos1);
                    mListView.setAdapter(adapter);
                    page=1;
                }
                else
                {
                    floatingActionButton.setImageResource(R.drawable.shoplist);
                    adapter.setItemList(Infos);
                    mListView.setAdapter(adapter);
                    page=0;
                }
            }
        });
        //注册接收器
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);}
       // EventBus.getDefault().unregister(this);
    }
    //接收eventBus
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessageEvent(item p){
        Infos1.add(p);
        adapter.setItemList(Infos1);
        mListView.setAdapter(adapter);
    }
    @Subscribe(sticky = true)
    public void onMessageEvent(pageEvent p)
    {
        page=1;
        floatingActionButton.setImageResource(R.drawable.mainpage);
        adapter.setItemList(Infos1);
        mListView.setAdapter(adapter);
    }







    @Override
 protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

