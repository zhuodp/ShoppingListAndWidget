package com.example.a74021.lab3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.Serializable;

import static com.example.a74021.lab3.R.id.name;

/**
 * Created by 74021 on 2017/10/25.
 */

public class item implements Serializable {
    private String itemName;
    private String itemPrice;
    private String itemType;
    private String itemValue;
    private String imageName;
    private Bitmap bitmap;
    public item(String itemName,String itemPrice,String itemType,String itemValue,String imageName){
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.itemType=itemType;
        this.itemValue=itemValue;
        this.imageName=imageName;
    }
    public String getItemName(){
        return itemName;
    }
    public String getItemPrice(){
        return itemPrice;
    }
    public String getItemType(){return  itemType;}
    public String getItemValue(){return  itemValue;}
    public String getImageName(){return  imageName;}
    public char getcycle(){
        char first=itemName.charAt(0);
        if(first>=97&&first<122){
            first-=32;
        }
        return first;
    }
}