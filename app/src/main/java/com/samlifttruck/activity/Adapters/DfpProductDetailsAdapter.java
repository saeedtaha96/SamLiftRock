package com.samlifttruck.activity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Models.DetailsModel;

import java.util.ArrayList;
import java.util.List;

public class DfpProductDetailsAdapter extends RecyclerView.Adapter<DfpProductDetailsAdapter.MyViewHolder> {

    List<DetailsModel> detailsList;
    Context context;

    public DfpProductDetailsAdapter(@NonNull Context context, List<DetailsModel> list) {
        this.context = context;
        this.detailsList = (list == null) ? new ArrayList<DetailsModel>() : list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dfp_product_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(detailsList.get(position));
    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTechNo, tvProductName, tvUnit, tvOnHand, tvTedad;
        Button btnEdit;
        TextView tvCounter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTechNo = itemView.findViewById(R.id.adapter_dfp_product_details_tv_tech_no);
            tvProductName = itemView.findViewById(R.id.adapter_dfp_product_details_tv_product_name);
            tvUnit = itemView.findViewById(R.id.adapter_dfp_product_details_tv_unit);
            tvOnHand = itemView.findViewById(R.id.adapter_dfp_product_details_tv_onhand);
            btnEdit = itemView.findViewById(R.id.adapter_dfp_product_details_btn_edit);
            tvTedad = itemView.findViewById(R.id.adapter_dfp_product_details_tv_tedad);
            tvCounter = itemView.findViewById(R.id.list_tv_counter);
        }

        private void bind(DetailsModel model) {
            tvCounter.setText(String.valueOf(getAdapterPosition()));
            tvTechNo.setText(model.getTechNo());
            tvProductName.setText(model.getProductName());
            tvUnit.setText(String.valueOf(model.getUnitName()));
            tvOnHand.setText(String.valueOf(model.getOnHand()));
            tvTedad.setText(String.valueOf(model.getQty()));


            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });

        }
    }
}
