package com.samlifttruck.activity.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.samlifttruck.R;
import com.samlifttruck.activity.Fragments.DfpProductsEditFragment;
import com.samlifttruck.activity.Models.DetailsModel;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DfpProductDetailsAdapter extends RecyclerView.Adapter<DfpProductDetailsAdapter.MyViewHolder> {
    private static final String TAG = "DfpProductDetailsAdapte";
    List<DetailsModel> detailsList;
    Context context;
    List<JSONObject> list;
    private int mUserId;

    public DfpProductDetailsAdapter(@NonNull Context context, List<DetailsModel> list) {
        this.context = context;
        this.detailsList = (list == null) ? new ArrayList<DetailsModel>() : list;

        SharedPreferences pref = context.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        mUserId = pref.getInt(Utility.LOGIN_USER_ID, 1);
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

        private TextView tvTechNo, tvProductName, tvUnit, tvOnHand, tvTedad;
        private Button btnEdit, btnDelete;
        private int productCode;
        private TextView tvCounter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTechNo = itemView.findViewById(R.id.adapter_dfp_product_details_tv_tech_no);
            tvProductName = itemView.findViewById(R.id.adapter_dfp_product_details_tv_product_name);
            tvUnit = itemView.findViewById(R.id.adapter_dfp_product_details_tv_unit);
            tvOnHand = itemView.findViewById(R.id.adapter_dfp_product_details_tv_onhand);
            btnEdit = itemView.findViewById(R.id.adapter_dfp_product_details_btn_edit);
            btnDelete = itemView.findViewById(R.id.adapter_dfp_product_details_btn_delete);
            tvTedad = itemView.findViewById(R.id.adapter_dfp_product_details_tv_tedad);
            tvCounter = itemView.findViewById(R.id.list_tv_counter);
        }

        private void bind(final DetailsModel model) {
            tvCounter.setText(String.valueOf(getAdapterPosition() + 1));
            tvTechNo.setText(model.getTechNo());
            tvProductName.setText(model.getProductName());
            tvUnit.setText(String.valueOf(model.getUnitName()));
            tvOnHand.setText(String.valueOf(model.getOnHand()));
            tvTedad.setText(String.valueOf(model.getQty()));
            productCode = model.getProductCode();


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOSDialogBuilder ios = Utility.newIOSdialog(context);
                    ios.setTitle(model.getProductName())
                            .setSubtitle("اطمینان از حذف ایتم فوق دارید؟")
                            .setPositiveListener("بله", new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                    deleteBusinessDetail(model.getUnitId(), model.getRow(), productCode, getAdapterPosition());
                                    dialog.dismiss();
                                }
                            }).setNegativeListener("خیر", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();
                        }
                    }).build().show();

                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DfpProductsEditFragment frag = DfpProductsEditFragment.newInstance(model.getProductName(), model.getTechNo(), productCode, model.getQty(), model.getOnHand(), model.getUnitName(), model.getUnitId(), model.getRow());
                    AppCompatActivity activity = (AppCompatActivity) context;
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.activity_reg_dfp_container, frag).addToBackStack(null).commit();
                }
            });

        }
    }


    {

    }

    private void deleteBusinessDetail(int unitId, int row, int productCode, final int adapterPos) {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("executeStatus");
        p1.setValue(3);
        p1.setType(Byte.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("userId");
        p2.setValue(mUserId);
        p2.setType(Integer.class);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("unitId");
        p3.setValue(unitId);
        p3.setType(Integer.class);

        PropertyInfo p4 = new PropertyInfo();
        p4.setName("row");
        p4.setValue(row);
        p4.setType(Integer.class);

        PropertyInfo p5 = new PropertyInfo();
        p5.setName("qty");
        p5.setValue(0);
        p5.setType(Integer.class);

        PropertyInfo p6 = new PropertyInfo();
        p6.setName("priceId");
        p6.setValue(0);
        p6.setType(Integer.class);

        PropertyInfo p7 = new PropertyInfo();
        p7.setName("salePrice");
        p7.setValue(0);
        p7.setType(Integer.class);

        PropertyInfo p8 = new PropertyInfo();
        p8.setName("buyPrice");
        p8.setValue(0);
        p8.setType(Integer.class);

        PropertyInfo p9 = new PropertyInfo();
        p9.setName("productName");
        p9.setValue("");
        p9.setType(String.class);

        PropertyInfo p10 = new PropertyInfo();
        p10.setName("businessTypeId");
        p10.setValue(4);
        p10.setType(Integer.class);

        PropertyInfo p11 = new PropertyInfo();
        p11.setName("productCode");
        p11.setValue(productCode);
        p11.setType(Integer.class);

        PropertyInfo p12 = new PropertyInfo();
        p12.setName("rate");
        p12.setValue(0);
        p12.setType(Integer.class);

        PropertyInfo p13 = new PropertyInfo();
        p13.setName("orderBusinessId");
        p13.setValue(0);
        p13.setType(Integer.class);

        PropertyInfo p14 = new PropertyInfo();
        p14.setName("isAndroid");
        p14.setValue(true);
        p14.setType(Boolean.class);

        final SoapCall ss = new SoapCall((AppCompatActivity) context, SoapCall.METHOD_EXECUTE_TEMP_BUSINESS_DETAILS);
        ss.execute(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list = ss.get();

                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {

                                try {
                                    if (list.get(0).getString("boolean").equals("true")) {
                                        Toast.makeText(context, "مورد با موفقیت حذف شد", Toast.LENGTH_LONG).show();

                                        // TODO: whats gonna do after this
                                        list.remove(adapterPos);
                                        notifyItemRemoved(adapterPos);
                                        notifyItemRangeChanged(adapterPos, list.size());
                                        //
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "run: " + e.getLocalizedMessage());
                                }

                            } else {
                                Toast.makeText(context, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: " + e.getLocalizedMessage());
                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
