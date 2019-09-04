package com.samlifttruck.activity.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Fragments.ReceiptListInfoAllFragment;
import com.samlifttruck.activity.Models.ReceiptListModel;

import java.util.ArrayList;
import java.util.List;

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.MyViewHolder> {

    private List<ReceiptListModel> list;

    public ReceiptListAdapter(List<ReceiptListModel> draft) {
        this.list = (draft == null) ? new ArrayList<ReceiptListModel>() : draft;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_receipt_list, parent, false);
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
        private TextView tvReceiptNum, tvReceiptType, tvProductSource, tvDate, tvCondition;
        private View myItem;
        AppCompatActivity activity;
        ReceiptListInfoAllFragment frag;
        private String mReceiptNum, mReceiptType, mProductSource, mDate, mCondition, mDescrip1, mDescrip2, mDescrip3;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReceiptNum = itemView.findViewById(R.id.activity_receipt_list_tv_receipt_num);
            tvReceiptType = itemView.findViewById(R.id.activity_receipt_list_tv_receipt_type);
            tvProductSource = itemView.findViewById(R.id.activity_receipt_list_tv_product_source);
            tvDate = itemView.findViewById(R.id.activity_receipt_list_tv_date);
            tvCondition = itemView.findViewById(R.id.activity_receipt_list_tv_condition);
            activity = (AppCompatActivity) itemView.getContext();
            myItem = itemView;
        }

        void bind(final ReceiptListModel item) {
            mReceiptNum = item.getReceiptNum();
            mReceiptType = item.getReceiptType();
            mProductSource = item.getProductSource();
            mDate = item.getDate();
            mCondition = item.getCondition();
            mDescrip1 = item.getDescrip1();
            mDescrip2 = item.getDescrip2();
            mDescrip3 = item.getDescrip3();

            tvReceiptNum.setText(mReceiptNum);
            tvReceiptType.setText(mReceiptType);
            tvProductSource.setText(mProductSource);
            tvDate.setText(mDate);
            tvCondition.setText(mCondition);

            myItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    frag = ReceiptListInfoAllFragment.newInstance(mReceiptNum, mReceiptType, mProductSource, mDate, mDescrip1, mDescrip2, mDescrip3);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_receipt_list, frag).addToBackStack(null).commit();
                }
            });
        }
    }
}