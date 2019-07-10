package com.cursoandroid.petinder.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cursoandroid.petinder.R;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<cards> {

    Context context;

    public arrayAdapter(Context context, int resourceId, List<cards> items){
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        cards card_item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.nameItemid);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageItemid);

        name.setText(card_item.getName()+", " + card_item.getAge());
        switch(card_item.getAge()){//m
            case "default":
                name.setText(card_item.getName());
                break;
            default:
                name.setText(card_item.getName()+","+card_item.getAge());
                break;
        }
        switch(card_item.getProfileImage()){//m
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
                break;
            default:
                Glide.clear(image);
                Glide.with(convertView.getContext()).load(card_item.getProfileImage()).into(image);
                break;
        }

        return convertView;

    }


}
