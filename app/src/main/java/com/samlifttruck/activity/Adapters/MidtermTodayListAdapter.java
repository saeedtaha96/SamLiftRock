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
        TextView shomareFanni, productName, inventory, currCount;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shomareFanni = itemView.findViewById(R.id.activity_today_list_tv_shomare_fanni);
            productName = itemView.findViewById(R.id.activity_today_list_tv_product_name);
            inventory = itemView.findViewById(R.id.activity_today_list_inventory);
            currCount = itemView.findViewById(R.id.activity_today_list_current_counting);
        }

        void bind(ReceiptListModel item) {
            shomareFanni.setText(item.getDraftNum());
            productName.setText(item.getReceiptNum());
            inventory.setText(item.getCustName());
            currCount.setText(item.getDate());
        }
    }
}