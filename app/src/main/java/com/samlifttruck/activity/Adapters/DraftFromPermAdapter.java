package com.samlifttruck.activity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.DataGenerators.Consts;
import com.samlifttruck.activity.DraftFromPermActivity;
import com.samlifttruck.activity.Models.PermListModel;
import com.samlifttruck.activity.RegDFPActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DraftFromPermAdapter extends RecyclerView.Adapter<DraftFromPermAdapter.MyViewHolder> {

    Context context;
    private List<PermListModel> list;
    Random r;

    public DraftFromPermAdapter(Context context, List<PermListModel> draft) {
        this.list = (draft == null) ? new ArrayList<PermListModel>() : draft;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_draft_from_perm, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        r = new Random();
        int random1 = r.nextInt(200) + 1;
        int random2 = r.nextInt(200) + 1;
        int random3 = r.nextInt(200) + 1;
        int cc = Color.rgb(random1, random2, random3);
        holder.bind(list.get(position), cc);
        setFadeAnimation(holder.itemView);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);
        anim.setDuration(900);
        view.startAnimation(anim);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPermNum, tvCustName, tvDate, tvPreFactorNum, tvCondition;
        View counterBar;
        TextView tvCounter;
        Button btnDFP;
        Context ctx;
        Drawable shapeRaw,shapeCounterBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPermNum = itemView.findViewById(R.id.adapter_dfp_tv_perm_num);
            tvCustName = itemView.findViewById(R.id.adapter_dfp_tv_cust_name);
            tvPreFactorNum = itemView.findViewById(R.id.adapter_dfp_tv_prefactor_num);
            tvDate = itemView.findViewById(R.id.adapter_dfp_tv_date);
            tvCondition = itemView.findViewById(R.id.adapter_dfp_tv_condition);
            counterBar = itemView.findViewById(R.id.view_counter_bar);
            tvCounter = itemView.findViewById(R.id.list_tv_counter);
            btnDFP = itemView.findViewById(R.id.adapter_dfp_btn_draft);
            ctx = itemView.getContext();
            shapeRaw = ctx.getResources().getDrawable(R.drawable.shape_raw);
            shapeCounterBar = ctx.getResources().getDrawable(R.drawable.shape_gradiant);
        }

        void bind(final PermListModel model, int color) {
            tvCounter.setText(String.valueOf(getAdapterPosition()+1));

            shapeRaw.setTint(color);
            shapeCounterBar.setTint(color);
            tvCounter.setBackground(shapeRaw);
            counterBar.setBackground(shapeCounterBar);
            tvPreFactorNum.setText(model.getPreFactorNum());
            tvPermNum.setText(model.getPermNum());
            tvCustName.setText(model.getCustName());
            tvDate.setText(model.getDate());
            tvCondition.setText(model.getCondition());



            btnDFP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),RegDFPActivity.class);
                    intent.putExtra(Consts.dfp.CUST_NAME,model.getCustName());
                    intent.putExtra(Consts.dfp.CUST_NAME,model.getCustName());
                    intent.putExtra(Consts.dfp.CUST_NAME,model.getCustName());
                    context.startActivity(intent);
                }
            });



        }
    }


}
