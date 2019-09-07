package com.samlifttruck.activity.Adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samlifttruck.R;

public class CustomSpinnerAdapter extends BaseAdapter {

    private Context context;
    private String[] items;
    private LayoutInflater inflater;

    public CustomSpinnerAdapter(Context context, String[] items) {
        this.items = items;
        this.context = context;
    }


    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_custom_spinner_adapter, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(position);
        return convertView;
    }


    public class ViewHolder {
        TextView item;

        ViewHolder(View itemView) {
            item = itemView.findViewById(R.id.custom_spinner_tv);
        }

        void fill(int position) {
            item.setText(items[position]);
        }
    }
}
