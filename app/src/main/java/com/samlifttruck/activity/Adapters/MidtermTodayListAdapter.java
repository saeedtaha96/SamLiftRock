package com.samlifttruck.activity.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Models.ReceiptListModel;

import java.util.ArrayList;
import java.util.List;

public class MidtermTodayListAdapter extends RecyclerView.Adapter<MidtermTodayListAdapter.MyViewHolder> {

    private List<ReceiptListModel> list;

    public MidtermTodayListAdapter(List<ReceiptListModel> draft) {
        this.list = (draft == null) ? new ArrayList<ReceiptListModel>() : draft;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_midterm_today_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(list.get(position));
       // setScaleAnimation(holder.itemView);
       setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(800);
        view.startAnimation(anim);
    }

   /* private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.5f, 1.0f, 0.3f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        anim.setDuration(700);
        view.startAnimation(anim);
    }*/

    // View Holder //
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView draftNumb, receiptNum, custName, date;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            draftNumb = itemView.findViewById(R.id.activity_receipt_list_tv_draft_num);
            receiptNum = itemView.findViewById(R.id.activity_receipt_list_tv_receipt_num);
            custName = itemView.findViewById(R.id.activity_receipt_list_tv_cust_name);
            date = itemView.findViewById(R.id.qwerty);
        }

        void bind(ReceiptListModel item) {
            draftNumb.setText(item.getDraftNum());
            receiptNum.setText(item.getReceiptNum());
            custName.setText(item.getCustName());
            date.setText(item.getDate());
        }
    }
}