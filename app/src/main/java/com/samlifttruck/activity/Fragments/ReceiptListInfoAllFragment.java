package com.samlifttruck.activity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samlifttruck.R;

public class ReceiptListInfoAllFragment extends Fragment {

//TODO: Fix need class
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DRAFT_NUM_KEY = "draftNum";
    private static final String DATE_KEY = "date";
    private static final String CUSTOMER_NAME_KEY = "custName";
    private static final String PERM_NUMB_KEY = "permNum";
    private static final String DRAFT_TYPE_KEY = "draftType";
    private static final String SERVICE_PAGE_KEY = "servicePage";
    private static final String DESCRIPTION_KEY = "descrip";

    // Views
    TextView tvDraftNum, tvPermNum, tvCustName, tvDate, tvDraftType, tvServicePage, tvDescrip;

    // TODO: Rename and change types of parameters
    private String mDraftNum;
    private String mDate;
    private String mCustName;
    private String mPermNum;
    private String mDraftType;
    private String mServicePage;
    private String mDescrip;

    public ReceiptListInfoAllFragment() {
        // Required empty public constructor
    }


    public static DraftListInfoAllFragment newInstance(String draftNum, String permNum, String custName, String date, String draftType, String servicePage, String descrip) {
        DraftListInfoAllFragment fragment = new DraftListInfoAllFragment();
        Bundle args = new Bundle();
        args.putString(DRAFT_NUM_KEY, draftNum);
        args.putString(PERM_NUMB_KEY, permNum);
        args.putString(CUSTOMER_NAME_KEY, custName);
        args.putString(DATE_KEY, date);
        args.putString(DRAFT_TYPE_KEY, draftType);
        args.putString(SERVICE_PAGE_KEY, servicePage);
        args.putString(DESCRIPTION_KEY, descrip);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDraftNum = getArguments().getString(DRAFT_NUM_KEY);
            mPermNum = getArguments().getString(PERM_NUMB_KEY);
            mCustName = getArguments().getString(CUSTOMER_NAME_KEY);
            mDate = getArguments().getString(DATE_KEY);
            mDraftType = getArguments().getString(DRAFT_TYPE_KEY);
            mServicePage = getArguments().getString(SERVICE_PAGE_KEY);
            mDescrip = getArguments().getString(DESCRIPTION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_draft_list_info_all, container, false);
        tvDraftNum = rootView.findViewById(R.id.fragment_draft_info_all_draft_num);
        tvPermNum = rootView.findViewById(R.id.fragment_draft_info_all_permNum);
        tvCustName = rootView.findViewById(R.id.fragment_draft_info_all_cust_name);
        tvDate = rootView.findViewById(R.id.fragment_draft_info_all_date);
        tvDraftType = rootView.findViewById(R.id.fragment_draft_info_all_draft_type);
        tvServicePage = rootView.findViewById(R.id.fragment_draft_info_all_service_page);
        tvDescrip = rootView.findViewById(R.id.fragment_draft_info_all_descrip);

        if (getArguments() != null) {
            tvDraftNum.setText(mDraftNum);
            tvPermNum.setText(mPermNum);
            tvCustName.setText(mCustName);
            tvDate.setText(mDate);
            tvDraftType.setText(mDraftType);
            tvServicePage.setText(mServicePage);
            tvDescrip.setText(mDescrip);
        }
        return rootView;
    }

}
