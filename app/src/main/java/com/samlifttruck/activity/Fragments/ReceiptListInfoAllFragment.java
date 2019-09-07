package com.samlifttruck.activity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.ReceiptListInfoAllAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;

public class ReceiptListInfoAllFragment extends Fragment {

    //TODO: Fix need class
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RECEIPT_NUM_KEY = "1";
    private static final String DATE_KEY = "2";
    private static final String PRODUCT_SOURCE_KEY = "3";
    private static final String RECEIPT_TYPE_KEY = "4";
    private static final String DESCRITION_1_KEY = "5";
    private static final String DESCRITION_2_KEY = "6";
    private static final String DESCRITION_3_KEY = "7";

    // Views
    private TextView tvReceiptNum, tvReceiptType, tvProductSource, tvDate, tvDescrip1, tvDescrip2, tvDescrip3;
    private RecyclerView rvReceiptListStuff;
    private ReceiptListInfoAllAdapter receiptAdapter;

    // TODO: Rename and change types of parameters
    private String mReceiptNum, mReceiptType, mProductSource, mDate, mDescrip1, mDescrip2, mDescrip3;

    public ReceiptListInfoAllFragment() {
        // Required empty public constructor
    }


    public static ReceiptListInfoAllFragment newInstance(String receiptNum, String receiptType, String productSource, String date, String descrip1, String descrip2, String descrip3) {
        ReceiptListInfoAllFragment fragment = new ReceiptListInfoAllFragment();
        Bundle args = new Bundle();
        args.putString(RECEIPT_NUM_KEY, receiptNum);
        args.putString(RECEIPT_TYPE_KEY, receiptType);
        args.putString(PRODUCT_SOURCE_KEY, productSource);
        args.putString(DATE_KEY, date);
        args.putString(DESCRITION_1_KEY, descrip1);
        args.putString(DESCRITION_2_KEY, descrip2);
        args.putString(DESCRITION_3_KEY, descrip3);
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

            receiptAdapter = new ReceiptListInfoAllAdapter(DataGenerator.getDraftList());
            rvReceiptListStuff.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            rvReceiptListStuff.setAdapter(receiptAdapter);
        }
        return rootView;
    }

}
