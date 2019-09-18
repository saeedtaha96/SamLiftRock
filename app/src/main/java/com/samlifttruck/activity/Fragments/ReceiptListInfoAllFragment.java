package com.samlifttruck.activity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.DraftListInfoAllAdapter;
import com.samlifttruck.activity.Adapters.ReceiptListInfoAllAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;
import com.samlifttruck.activity.Models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReceiptListInfoAllFragment extends Fragment {
    private List<JSONObject> list = null;
    private List<DetailsModel> detailsList;
    //TODO: Fix need class
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RECEIPT_NUM_KEY = "1";
    private static final String DATE_KEY = "2";
    private static final String PRODUCT_SOURCE_KEY = "3";
    private static final String RECEIPT_TYPE_KEY = "4";
    private static final String DESCRITION_1_KEY = "5";
    private static final String DESCRITION_2_KEY = "6";
    private static final String DESCRITION_3_KEY = "7";
    private static final String BUSINESS_ID_KEY = "8";

    // Views
    private TextView tvReceiptNum, tvReceiptType, tvProductSource, tvDate, tvDescrip1, tvDescrip2, tvDescrip3;
    private RecyclerView rvReceiptListStuff;
    private ReceiptListInfoAllAdapter receiptAdapter;

    // TODO: Rename and change types of parameters
    private String mReceiptNum, mReceiptType, mProductSource, mDate, mDescrip1, mDescrip2, mDescrip3, mBusinessID;

    public ReceiptListInfoAllFragment() {
        // Required empty public constructor
    }


    public static ReceiptListInfoAllFragment newInstance(String businessID, String receiptNum, String receiptType, String productSource, String date, String descrip1, String descrip2, String descrip3) {
        ReceiptListInfoAllFragment fragment = new ReceiptListInfoAllFragment();
        Bundle args = new Bundle();
        args.putString(RECEIPT_NUM_KEY, receiptNum);
        args.putString(RECEIPT_TYPE_KEY, receiptType);
        args.putString(PRODUCT_SOURCE_KEY, productSource);
        args.putString(DATE_KEY, date);
        args.putString(DESCRITION_1_KEY, descrip1);
        args.putString(DESCRITION_2_KEY, descrip2);
        args.putString(DESCRITION_3_KEY, descrip3);
        args.putString(BUSINESS_ID_KEY, businessID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mReceiptNum = getArguments().getString(RECEIPT_NUM_KEY);
            mReceiptType = getArguments().getString(RECEIPT_TYPE_KEY);
            mProductSource = getArguments().getString(PRODUCT_SOURCE_KEY);
            mDate = getArguments().getString(DATE_KEY);
            mDescrip1 = getArguments().getString(DESCRITION_1_KEY);
            mDescrip2 = getArguments().getString(DESCRITION_2_KEY);
            mDescrip3 = getArguments().getString(DESCRITION_3_KEY);
            mBusinessID = getArguments().getString(BUSINESS_ID_KEY);
            //Toast.makeText(getActivity(), mBusinessID, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_receipt_list_info_all, container, false);
        tvReceiptNum = rootView.findViewById(R.id.fragment_receipt_info_all_receipt_num);
        tvReceiptType = rootView.findViewById(R.id.fragment_receipt_info_all_receipt_type);
        tvProductSource = rootView.findViewById(R.id.fragment_receipt_info_all_product_source);
        tvDate = rootView.findViewById(R.id.fragment_receipt_info_all_date);
        tvDescrip1 = rootView.findViewById(R.id.fragment_receipt_info_all_descrip1);
        tvDescrip2 = rootView.findViewById(R.id.fragment_receipt_info_all_descrip2);
        tvDescrip3 = rootView.findViewById(R.id.fragment_receipt_info_all_descrip3);
        rvReceiptListStuff = rootView.findViewById(R.id.fragment_receipt_info_all_recyclerview);

        if (getArguments() != null) {
            tvReceiptNum.setText(mReceiptNum);
            tvReceiptType.setText(mReceiptType);
            tvProductSource.setText(mProductSource);
            tvDate.setText(mDate);
            tvDescrip1.setText(mDescrip1);
            tvDescrip2.setText(mDescrip2);
            tvDescrip3.setText(mDescrip3);

            getProducts();
        }
        return rootView;
    }

    private void getProducts() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("businessId");
        p1.setValue(Integer.valueOf(mBusinessID));
        p1.setType(Integer.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("businessTypeId");
        p2.setValue(5);
        p2.setType(Integer.class);

        final SoapCall ss = new SoapCall(null, SoapCall.METHOD_GET_BUSINESS_DETAILS, SoapCall.SOAP_ACTION_GET_BUSINESS_DETAILS);
        ss.execute(p0, p1, p2);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ss.get() != null) {
                        list = ss.get();
                        detailsList = new ArrayList<>(list.size());
                        DetailsModel model;
                        for (int i = 0; i < list.size(); i++) {
                            model = new DetailsModel();
                            model.setOnHand(list.get(i).getString("onHand"));
                            model.setProductName(list.get(i).getString("ProductName"));
                            model.setQty(list.get(i).getString("Qty"));
                            model.setTechNo(list.get(i).getString("TechNo"));
                            model.setUnitName(list.get(i).getString("UnitName"));

                            detailsList.add(model);
                        }

                        receiptAdapter = new ReceiptListInfoAllAdapter(detailsList);
                        rvReceiptListStuff.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        rvReceiptListStuff.setAdapter(receiptAdapter);

                    } else if (ss.get() == null) {
                        Toast.makeText(getActivity(), "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                    }

                } catch (ExecutionException | JSONException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
