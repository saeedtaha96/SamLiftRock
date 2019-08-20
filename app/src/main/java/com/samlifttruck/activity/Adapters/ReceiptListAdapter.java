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
import com.samlifttruck.activity.Models.DraftListModel;

import java.util.ArrayList;
import java.util.List;

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.MyViewHolder> {

    private List<DraftListModel> list;

    public ReceiptListAdapter(List<DraftListModel> draft) {
        this.list = (draft == null) ? new ArrayList<DraftListModel>() : draft;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_draft_list_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(list.get(position));
        setScaleAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   /* private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }*/

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.5f, 1.0f, 0.3f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(800);
        view.startAnimation(anim);
    }

    // View Holder //
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView draftNumb, permNumb, custName, date;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            draftNumb = itemView.findViewById(R.id.activity_draft_list_tv_draft_num);
            permNumb = itemView.findViewById(R.id.activity_draft_list_tv_perm_num);
            custName = itemView.findViewById(R.id.activity_draft_list_tv_cust_name);
            date = itemView.findViewById(R.id.activity_draft_list_tv_date);
        }

        void bind(DraftListModel item) {
            draftNumb.setText(item.getDraftNum());
            permNumb.setText(item.getPermNum());
            custName.setText(item.getCustName());
            date.setText(item.getDate());
        }
    }
}