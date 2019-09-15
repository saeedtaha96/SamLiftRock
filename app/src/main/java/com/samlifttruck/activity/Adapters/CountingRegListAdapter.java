package com.samlifttruck.activity.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.samlifttruck.R;
import com.samlifttruck.activity.Models.DraftListModel;

import java.util.List;

public class CountingRegListAdapter extends RecyclerView.Adapter<CountingRegListAdapter.MyViewHolder> {

    List<DraftListModel> list;

    public CountingRegListAdapter(List<DraftListModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_counting_reg_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(list.get(position));
        YoYo.with(Techniques.FadeIn)
                .duration(700)

                .playOn(holder.itemView);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvInventory, tvTechNum, tvProductName, tvShelfNum, tvCounting1, tvEtCounting2, tvEtCounting3, tvResult_1_2, tvFinalResult;
        private Button btnReject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.adapter_counting_reg_tv_product_name);
            tvShelfNum = itemView.findViewById(R.id.adapter_counting_reg_tv_shelf_num);
            tvCounting1 = itemView.findViewById(R.id.adapter_counting_reg_tv_counting_1);
            tvEtCounting2 = itemView.findViewById(R.id.adapter_counting_reg_tv_counting_2);
            tvEtCounting3 = itemView.findViewById(R.id.adapter_counting_reg_tv_counting_3);
            tvResult_1_2 = itemView.findViewById(R.id.adapter_counting_reg_tv_result_1_2);
            tvFinalResult = itemView.findViewById(R.id.adapter_counting_reg_tv_final_result);
            tvTechNum = itemView.findViewById(R.id.adapter_counting_reg_tv_tech_num);
            tvInventory = itemView.findViewById(R.id.adapter_counting_reg_tv_inventory);
            btnReject = itemView.findViewById(R.id.counting_reg_list_reject);
        }

        void bind(DraftListModel item) {
            tvInventory.setText(item.getDate());
            tvTechNum.setText(item.getCondition());
            tvProductName.setText(item.getDraftNum());
            tvShelfNum.setText(item.getServicePage());
            tvCounting1.setText(item.getPermNum());
            tvEtCounting2.setText(item.getReceiver());
            tvEtCounting3.setText(item.getReceiver());
            tvResult_1_2.setText(item.getDraftType());
            tvFinalResult.setText(item.getPermNum());

            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), list.size());
                }
            });

        }

    }
}
