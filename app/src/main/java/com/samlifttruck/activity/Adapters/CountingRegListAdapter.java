package com.samlifttruck.activity.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.samlifttruck.R;
import com.samlifttruck.activity.CountingRegActivity;
import com.samlifttruck.activity.Utility.Consts;
import com.samlifttruck.activity.Models.CountingRegModel;

import java.util.List;

public class CountingRegListAdapter extends RecyclerView.Adapter<CountingRegListAdapter.MyViewHolder> {

    List<CountingRegModel> list;
    private Context context;

    public CountingRegListAdapter(@NonNull List<CountingRegModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_counting_reg_list, parent, false);
        context = parent.getContext();
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
        private TextView tvInventory, tvTechNum, tvProductName, tvShelfNum, tvCounting1, tvCounting2, tvCounting3, tvResult_1_2, tvFinalResult;
        private Button btnEdit;
        private int productCode;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.adapter_counting_reg_tv_product_name);
            tvShelfNum = itemView.findViewById(R.id.adapter_counting_reg_tv_shelf_num);
            tvCounting1 = itemView.findViewById(R.id.adapter_counting_reg_tv_counting_1);
            tvCounting2 = itemView.findViewById(R.id.adapter_counting_reg_tv_counting_2);
            tvCounting3 = itemView.findViewById(R.id.adapter_counting_reg_tv_counting_3);
            tvResult_1_2 = itemView.findViewById(R.id.adapter_counting_reg_tv_result_1_2);
            tvFinalResult = itemView.findViewById(R.id.adapter_counting_reg_tv_final_result);
            tvTechNum = itemView.findViewById(R.id.adapter_counting_reg_tv_tech_num);
            tvInventory = itemView.findViewById(R.id.adapter_counting_reg_tv_inventory);
            btnEdit = itemView.findViewById(R.id.counting_reg_list_btn_edit);
        }

        void bind(final CountingRegModel item) {
            productCode = item.getProductCode();
            tvInventory.setText(String.valueOf(item.getOnHand()));
            tvTechNum.setText(item.getTechNo());
            tvProductName.setText(item.getProductName());
            tvShelfNum.setText(item.getShelfNum());
            tvCounting1.setText(String.valueOf(item.getCount1()));
            tvCounting2.setText(String.valueOf(item.getCount2()));
            tvCounting3.setText(String.valueOf(item.getCount3()));
            tvResult_1_2.setText(String.valueOf(item.getCountResult_1_2()));
            tvFinalResult.setText(String.valueOf(item.getFinalResult()));

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CountingRegActivity.class);
                    intent.putExtra(Consts.CntReg.ON_HAND, item.getOnHand());
                    intent.putExtra(Consts.CntReg.PRODUCT_NAME, item.getProductName());
                    intent.putExtra(Consts.CntReg.PRODUCT_CODE, item.getProductCode());
                    intent.putExtra(Consts.CntReg.TECH_NO, item.getTechNo());
                    intent.putExtra(Consts.CntReg.SHELF_NUM, item.getShelfNum());
                    intent.putExtra(Consts.CntReg.COUNT_1, item.getCount1());
                    intent.putExtra(Consts.CntReg.COUNT_2, item.getCount2());
                    intent.putExtra(Consts.CntReg.COUNT_3, item.getCount3());
                    intent.putExtra(Consts.CntReg.RESULT_1_2, item.getCountResult_1_2());
                    intent.putExtra(Consts.CntReg.FINAL_RESULT, item.getFinalResult());
                    context.startActivity(intent);
                }
            });


        }

    }
}
