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
import com.samlifttruck.activity.Fragments.PermListInfoAllFragment;
import com.samlifttruck.activity.Fragments.ReceiptListInfoAllFragment;
import com.samlifttruck.activity.Models.PermListModel;

import java.util.ArrayList;
import java.util.List;

public class PermListAdapter extends RecyclerView.Adapter<PermListAdapter.MyViewHolder> {

    private List<PermListModel> list;

    public PermListAdapter(List<PermListModel> draft) {
        this.list = (draft == null) ? new ArrayList<PermListModel>() : draft;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_perm_list, parent, false);
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

    // TODO: Fix all this
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPermNum, tvCustName, tvDate, tvPreFactorNum, tvCondition;
        private View myItem;
        AppCompatActivity activity;
        PermListInfoAllFragment frag;
        private String mPermNum, mDate, mCustName, mPreFactorNum, mDescrip, mCondition;
        private int mBusinessID;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPreFactorNum = itemView.findViewById(R.id.activity_perm_list_tv_pre_factor_num);
            tvPermNum = itemView.findViewById(R.id.activity_perm_list_tv_perm_num);
            tvCustName = itemView.findViewById(R.id.activity_perm_list_tv_cust_name);
            tvDate = itemView.findViewById(R.id.activity_perm_list_tv_date);
            tvCondition = itemView.findViewById(R.id.activity_perm_list_tv_condition);
            activity = (AppCompatActivity) itemView.getContext();
            myItem = itemView;
        }

        void bind(PermListModel item) {
            mPermNum = item.getPermNum();
            mDate = item.getDate();
            mCustName = item.getCustName();
            mPreFactorNum = item.getPreFactorNum();
            mDescrip = item.getDescrip();
            mCondition = item.getCondition();
            mBusinessID = item.getBusinessID();

            tvPreFactorNum.setText(mPreFactorNum);
            tvPermNum.setText(mPermNum);
            tvCustName.setText(mCustName);
            tvDate.setText(mDate);
            tvCondition.setText(mCondition);

            myItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    frag = PermListInfoAllFragment.newInstance(mBusinessID, mPermNum, mDate, mCustName, mPreFactorNum, mDescrip);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_perm_list, frag).addToBackStack(null).commit();
                }
            });
        }
    }
}