package com.samlifttruck.activity.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Fragments.DraftListInfoAllFragment;
import com.samlifttruck.activity.Models.DraftListModel;

import java.util.ArrayList;
import java.util.List;

public class PermListInfoAllAdapter extends RecyclerView.Adapter<PermListInfoAllAdapter.MyViewHolder> {

    private List<DraftListModel> list;

    public PermListInfoAllAdapter(List<DraftListModel> draft) {
        this.list = (draft == null) ? new ArrayList<DraftListModel>() : draft;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_perm_list_info_all_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(list.get(position));
        //setScaleAnimation(holder.itemView);
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.5f, 1.0f, 0.3f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(800);
        view.startAnimation(anim);
    }

    // View Holder //
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView techNum, productName, unit, count, inventory;
        AppCompatActivity activity;
        DraftListInfoAllFragment frag;
        private String mTechNum, mProductName, mUnit, mCount, mInventory;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            techNum = itemView.findViewById(R.id.adapter_perm_info_all_rv_tech_num);
            productName = itemView.findViewById(R.id.adapter_perm_info_all_rv_product_name);
            unit = itemView.findViewById(R.id.adapter_perm_info_all_rv_unit);
            count = itemView.findViewById(R.id.adapter_perm_info_all_rv_count);
            inventory = itemView.findViewById(R.id.adapter_perm_info_all_rv_inventory);
            activity = (AppCompatActivity) itemView.getContext();
        }

        void bind(final DraftListModel item) {
            mTechNum = item.getDraftNum();
            mProductName = item.getPermNum();
            mUnit = item.getReceiver();
            mCount = item.getDate();
            mInventory = item.getDraftType();

            techNum.setText(mTechNum);
            productName.setText(mProductName);
            unit.setText(mUnit);
            count.setText(mCount);
            inventory.setText(mInventory);

        }
    }
}
