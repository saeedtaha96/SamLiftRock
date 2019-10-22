package com.samlifttruck.activity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samlifttruck.R;

import java.util.Objects;

public class CustomSpinnerAdapter extends BaseAdapter {

    private Context context;
    private String[] items;
    public static final int SPINNER_DATE = 0;
    public static final int SPINNER_DRAFT_TYPE = 1;
    private int spinnerType;


    public CustomSpinnerAdapter(Context context, String[] items, int spinnerType) {
        this.items = items;
        this.context = context;
        this.spinnerType = spinnerType;
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
            if (spinnerType == SPINNER_DATE) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_custom_spinner_adapter, viewGroup, false);
            } else if (spinnerType == SPINNER_DRAFT_TYPE) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_spinner_reg_dfp, viewGroup, false);
            }
            holder = new ViewHolder(Objects.requireNonNull(convertView));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(position);
        return convertView;
    }


    public class ViewHolder {
        TextView item, item1;

        ViewHolder(View itemView) {
            item = itemView.findViewById(R.id.custom_spinner_tv);
            item1 = itemView.findViewById(R.id.custom_spinner_reg_pdf_tv);
        }

        void fill(int position) {
            if (spinnerType == SPINNER_DRAFT_TYPE) {
                item1.setText(items[position]);
            } else if (spinnerType == SPINNER_DATE)
                item.setText(items[position]);
        }
    }
}
