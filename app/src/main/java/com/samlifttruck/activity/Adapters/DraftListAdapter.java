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
import com.samlifttruck.activity.Fragments.DraftListInfoAllFragment;
import com.samlifttruck.activity.Models.DraftListModel;

import java.util.ArrayList;
import java.util.List;

public class DraftListAdapter extends RecyclerView.Adapter<DraftListAdapter.MyViewHolder> {

    private List<DraftListModel> list;

    public DraftListAdapter(List<DraftListModel> draft) {
        this.list = (draft == null) ? new ArrayList<DraftListModel>() : draft;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_draft_list, parent, false);
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
        private TextView draftNumb, permNumb, custName, date, condition;
        private View myItem;
        AppCompatActivity activity;
        DraftListInfoAllFragment frag;
        private String sDraftNum, sPermNum, sCustName, sDate, sDraftType, sServicePage, sDescrip;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            draftNumb = itemView.findViewById(R.id.activity_draft_list_tv_draft_num);
            permNumb = itemView.findViewById(R.id.activity_draft_list_tv_perm_num);
            custName = itemView.findViewById(R.id.activity_draft_list_tv_cust_name);
            date = itemView.findViewById(R.id.activity_draft_list_tv_date);
            condition = itemView.findViewById(R.id.activity_draft_list_tv_condition);
            activity = (AppCompatActivity) itemView.getContext();
            myItem = itemView;
        }

        void bind(final DraftListModel item) {
            sDraftNum = item.getDraftNum();
            sPermNum = item.getPermNum();
            sCustName = item.getCustName();
            sDate = item.getDate();
            sDraftType = item.getDraftType();
            sServicePage = item.getServicePage();
            sDescrip = item.getDescription();

            draftNumb.setText(sDraftNum);
            permNumb.setText(sPermNum);
            custName.setText(sCustName);
            date.setText(sDate);
            myItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    frag = DraftListInfoAllFragment.newInstance(sDraftNum, sPermNum, sCustName, sDate, sDraftType, sServicePage, sDescrip);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_draft_list, frag).addToBackStack(null).commit();
                }
            });
        }
    }
}
