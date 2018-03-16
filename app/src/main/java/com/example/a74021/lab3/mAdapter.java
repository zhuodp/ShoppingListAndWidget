package com.example.a74021.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Map;
import java.util.Objects;
import java.util.List;

/**
 * Created by 74021 on 2017/10/24.
 */
public class mAdapter extends BaseAdapter {
    private Context context;
    private List<item> list;
    public mAdapter(Context context,List<item> list){
        this.context=context;
        this.list=list;
    }
    public void setItemList(List<item> itemlist)
    {
        this.list=itemlist;
    }


    @Override
    public int getCount()
    {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i)
    {
        if(list==null) {
            return null;
        }
        return list.get(i);
    }
    @Override
     public long getItemId(int i){
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        View convertView;
        viewHolder viewHolder;
        if(view==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.listview,null);
            viewHolder=new viewHolder();
            viewHolder.itemcycle=(TextView)convertView.findViewById(R.id.cycle);
            viewHolder.itemName=(TextView)convertView.findViewById(R.id.name);
            viewHolder.itemPrice=(TextView)convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        }else{
            convertView=view;
            viewHolder=(viewHolder) convertView.getTag();
        }



        if(i==0&&String.valueOf(list.get(i).getcycle()).equals("购"))
        {
            viewHolder.itemcycle.setText("*");
        }
        else {
        viewHolder.itemcycle.setText(String.valueOf(list.get(i).getcycle()));
        }
        viewHolder.itemName.setText(list.get(i).getItemName());
        viewHolder.itemPrice.setText(list.get(i).getItemPrice());

        return convertView;
    }
    private class viewHolder{
        public TextView itemcycle;
        public TextView itemName;
        public TextView itemPrice;
    }
}
