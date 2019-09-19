package com.samlifttruck.activity.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Models.CardexDetailsModel;
import com.samlifttruck.activity.Models.DetailsModel;

import java.util.List;
import java.util.Random;

public class CardexDetailsAdapter extends RecyclerView.Adapter<CardexDetailsAdapter.MyViewHolder> {

    Random r;
    List<CardexDetailsModel> cardexList;

    public CardexDetailsAdapter(List<CardexDetailsModel> cardexList) {
        this.cardexList = cardexList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardex_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        r = new Random();
        int random1 = r.nextInt(255) + 1;
        int random2 = r.nextInt(255) + 1;
        int random3 = r.nextInt(255) + 1;
        int cc = Color.rgb(random1, random2, random3);
        holder.bind(cardexList.get(position), cc);
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return cardexList.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);
        anim.setDuration(900);
        view.startAnimation(anim);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvCustName, tvDate, tvIncoming, tvOutgoing, tvInventory;
        View colorLine;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.adapter_cardex_tv_id);
            tvCustName = itemView.findViewById(R.id.adapter_cardex_tv_cust_name);
            tvDate = itemView.findViewById(R.id.adapter_cardex_tv_date);
            tvIncoming = itemView.findViewById(R.id.adapter_cardex_tv_incoming);
            tvOutgoing = itemView.findViewById(R.id.adapter_cardex_tv_outgoing);
            tvInventory = itemView.findViewById(R.id.adapter_cardex_inventory);
            colorLine = itemView.findViewById(R.id.adapter_cardex_rv_view);
        }

        void bind(CardexDetailsModel item, int rgb) {
            colorLine.setBackgroundColor(rgb);
            tvId.setText(item.getID());
            tvCustName.setText(item.getPersonName());
            tvDate.setText(item.getPersianBusinessDate());
            tvIncoming.setText(String.valueOf(item.getIncoming()));
            tvOutgoing.setText(String.valueOf(item.getOutGoing()));
            tvInventory.setText(String.valueOf(item.getOnHand()));
        }
    }
}
