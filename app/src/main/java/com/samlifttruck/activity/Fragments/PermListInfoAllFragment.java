package com.samlifttruck.activity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samlifttruck.R;

public class PermListInfoAllFragment extends Fragment {
// TODO: fix this class

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PERM_NUM_KEY = "1";
    private static final String DATE_KEY = "2";
    private static final String CUSTOMER_NAME_KEY = "3";
    private static final String PRE_FACTOR_KEY = "4";
    private static final String DESCRIPTION_KEY = "5";

    // Views
    private TextView tvPermNum, tvCustName, tvDate, tvPreFactorNum, tvDescrip;
    private RecyclerView rvPermListStuff;

    // TODO: Rename and change types of parameters
    private String mPermNum;
    private String mDate;
    private String mCustName;
    private String mPreFactorNum;
    private String mDescrip;

    public PermListInfoAllFragment() {
        // Required empty public constructor
    }


    public static PermListInfoAllFragment newInstance(String permNum, String date, String custName, String preFactorNum, String descrip) {
        PermListInfoAllFragment fragment = new PermListInfoAllFragment();
        Bundle args = new Bundle();
        args.putString(PERM_NUM_KEY, permNum);
        args.putString(DATE_KEY, date);
        args.putString(CUSTOMER_NAME_KEY, custName);
        args.putString(PRE_FACTOR_KEY, preFactorNum);
        args.putString(DESCRIPTION_KEY, descrip);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPermNum = getArguments().getString(PERM_NUM_KEY);
            mDate = getArguments().getString(DATE_KEY);
            mCustName = getArguments().getString(CUSTOMER_NAME_KEY);
            mPreFactorNum = getArguments().getString(PRE_FACTOR_KEY);
            mDescrip = getArguments().getString(DESCRIPTION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perm_list_info_all, container, false);
        tvPermNum = rootView.findViewById(R.id.fragment_perm_info_all_perm_num);
        tvCustName = rootView.findViewById(R.id.fragment_perm_info_all_cust_name);
        tvDate = rootView.findViewById(R.id.fragment_perm_info_all_date);
        tvPreFactorNum = rootView.findViewById(R.id.fragment_perm_info_all_prefactor_num);
        tvDescrip = rootView.findViewById(R.id.fragment_perm_info_all_descrip);
        rvPermListStuff = rootView.findViewById(R.id.fragment_perm_info_all_recyclerview);

        if (getArguments() != null) {
            tvPermNum.setText(mPermNum);
            tvCustName.setText(mCustName);
            tvDate.setText(mDate);
            tvPreFactorNum.setText(mPreFactorNum);
            tvDescrip.setText(mDescrip);
        }
        return rootView;
    }

}
