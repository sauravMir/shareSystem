package com.educareapps.jsonreader.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.educareapps.jsonreader.R;
import com.educareapps.jsonreader.dao.Item;

import java.util.ArrayList;

/**
 * Created by RK-REAZ on 7/20/2017.
 */

public class ItemAdapter extends BaseAdapter {
    ArrayList<Item> items;
    Context context;


    public ItemAdapter(Context context, ArrayList<Item> items) {
        this.context = context;

        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater   mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_cell, null);
            holder.tvItem = (TextView) convertView.findViewById(R.id.tvItem);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvItem.setText(items.get(position).getUserText());


        return convertView;
    }

    private static class ViewHolder {
        TextView tvItem;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
