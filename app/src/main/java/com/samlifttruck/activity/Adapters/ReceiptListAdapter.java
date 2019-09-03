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
       // private TextView
        private View myItem;
        AppCompatActivity activity;
        ReceiptListInfoAllFragment frag;
       // private String

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myItem = itemView;
        }

        void bind(final ReceiptListModel item) {




            myItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 //   frag = ReceiptListInfoAllFragment.newInstance();
                   // activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_draft_list, frag).addToBackStack(null).commit();
                }
            });
        }
    }
}