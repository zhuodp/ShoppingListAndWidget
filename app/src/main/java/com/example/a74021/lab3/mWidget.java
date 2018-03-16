package com.example.a74021.lab3;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;


import static com.example.a74021.lab3.MainActivity.i;



/**
 * Implementation of App Widget functionality.
 */
public class mWidget extends AppWidgetProvider {
    static int resID=0;
    static item temp;
    final List<item> goods=new ArrayList<item>(){{
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
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
        views.setTextViewText(R.id.appwidget_text,"nothing yet");
        views.setImageViewResource(R.id.appwidget_img,R.drawable.shoplist);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context,appWidgetManager,appWidgetIds);
            RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.m_widget);
            Intent i1 = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, 0, i1, PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.appwidget_img, pi); //may be wrong
            appWidgetManager.updateAppWidget(appWidgetIds, updateViews);

    }

    @Override
    public void onReceive(Context context,Intent intent)
    {
        super.onReceive(context,intent);
        if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE"))
        {
            RemoteViews view =new RemoteViews(context.getPackageName(),R.layout.m_widget);

            if(intent.getSerializableExtra("item")!=null && intent.getSerializableExtra("resID")!=null)
            {
                item p = (item) intent.getSerializableExtra("item");
                resID = (int) intent.getSerializableExtra("resID");
                view.setTextViewText(R.id.appwidget_text, p.getItemName() + "现在仅售" + p.getItemPrice() + "!");
                view.setImageViewResource(R.id.appwidget_img, resID);
                Intent intent1=new Intent(context,infoActivity.class);
                intent1.putExtra("wItem",p);
                PendingIntent pi=PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
                view.setOnClickPendingIntent(R.id.appwidget_img,pi);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                appWidgetManager.updateAppWidget(new ComponentName(context, mWidget.class), view);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

