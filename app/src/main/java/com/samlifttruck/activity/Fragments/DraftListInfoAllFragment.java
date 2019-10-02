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
import com.samlifttruck.activity.Adapters.PermListInfoAllAdapter;
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

public class DraftListInfoAllFragment extends Fragment {
    private List<JSONObject> list = null;
    private List<DetailsModel> detailsList;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DRAFT_NUM_KEY = "draftNum";
    private static final String DATE_KEY = "date";
    private static final String CUSTOMER_NAME_KEY = "custName";
    private static final String PERM_NUMB_KEY = "permNum";
    private static final String DRAFT_TYPE_KEY = "draftType";
    private static final String SERVICE_PAGE_KEY = "servicePage";
    private static final String DESCRIPTION_KEY = "descrip";
    private static final String BUSINESS_ID_KEY = "businessID";

    // Views
    private TextView tvDraftNum, tvPermNum, tvCustName, tvDate, tvDraftType, tvServicePage, tvDescrip;
    private RecyclerView rvDraftListStuff;
    private DraftListInfoAllAdapter draftAdapter;

    // TODO: Rename and change types of parameters
    private String mDraftNum;
    private String mDate;
    private String mCustName;
    private String mPermNum;
    private String mDraftType;
    private String mServicePage;
    private String mDescrip;
    private String mBusinessID;


    public DraftListInfoAllFragment() {
        // Required empty public constructor
    }


    public static DraftListInfoAllFragment newInstance(String businessID, String draftNum, String permNum, String custName, String date, String draftType, String servicePage, String descrip) {
        DraftListInfoAllFragment fragment = new DraftListInfoAllFragment();
        Bundle args = new Bundle();
        args.putString(DRAFT_NUM_KEY, draftNum);
        args.putString(PERM_NUMB_KEY, permNum);
        args.putString(CUSTOMER_NAME_KEY, custName);
        args.putString(DATE_KEY, date);
        args.putString(DRAFT_TYPE_KEY, draftType);
        args.putString(SERVICE_PAGE_KEY, servicePage);
        args.putString(DESCRIPTION_KEY, descrip);
        args.putString(BUSINESS_ID_KEY, businessID);
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
            mBusinessID = getArguments().getString(BUSINESS_ID_KEY);
            //Toast.makeText(getActivity(), mBusinessID, Toast.LENGTH_SHORT).show();
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
        rvDraftListStuff = rootView.findViewById(R.id.fragment_draft_info_all_recyclerview);

        if (getArguments() != null) {
            tvDraftNum.setText(mDraftNum);
            tvPermNum.setText(mPermNum);
            tvCustName.setText(mCustName);
            tvDate.setText(mDate);
            tvDraftType.setText(mDraftType);
            tvServicePage.setText(mServicePage);
            tvDescrip.setText(mDescrip);

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
        p2.setValue(4);
        p2.setType(Integer.class);

        final SoapCall ss = new SoapCall(null, SoapCall.METHOD_GET_BUSINESS_DETAILS);
        ss.execute(p0, p1,p2);


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

                        draftAdapter = new DraftListInfoAllAdapter(detailsList);
                        rvDraftListStuff.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
                        rvDraftListStuff.setAdapter(draftAdapter);

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
