package com.samlifttruck.activity.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.PermListInfoAllAdapter;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;
import com.samlifttruck.activity.Models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PermListInfoAllFragment extends Fragment {
    // TODO: fix this class
    List<JSONObject> list = null;
    List<DetailsModel> detailsList;

    private static final String TAG = "PermListInfoAllFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PERM_NUM_KEY = "1";
    private static final String DATE_KEY = "2";
    private static final String CUSTOMER_NAME_KEY = "3";
    private static final String PRE_FACTOR_KEY = "4";
    private static final String DESCRIPTION_KEY = "5";
    private static final String BUSINESS_ID_KEY = "6";


    // Views
    private TextView tvPermNum, tvCustName, tvDate, tvPreFactorNum, tvDescrip;
    TextView tvListSize;
    RecyclerView rvPermListStuff;
    PermListInfoAllAdapter permAdapter;

    // TODO: Rename and change types of parameters
    private String mPermNum;
    private String mDate;
    private String mCustName;
    private String mPreFactorNum;
    private String mDescrip;
    private int mBusinessID;


    public PermListInfoAllFragment() {
        // Required empty public constructor
    }


    public static PermListInfoAllFragment newInstance(int businessID, String permNum, String date, String custName, String preFactorNum, String descrip) {
        PermListInfoAllFragment fragment = new PermListInfoAllFragment();
        Bundle args = new Bundle();
        args.putString(PERM_NUM_KEY, permNum);
        args.putString(DATE_KEY, date);
        args.putString(CUSTOMER_NAME_KEY, custName);
        args.putString(PRE_FACTOR_KEY, preFactorNum);
        args.putString(DESCRIPTION_KEY, descrip);
        args.putInt(BUSINESS_ID_KEY, businessID);
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
            mBusinessID = getArguments().getInt(BUSINESS_ID_KEY);
            //Toast.makeText(getActivity(), mBusinessID, Toast.LENGTH_SHORT).show();
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
        tvListSize = rootView.findViewById(R.id.fragment_draft_list_info_all_list_size);


        if (getArguments() != null) {
            tvPermNum.setText(mPermNum);
            tvCustName.setText(mCustName);
            tvDate.setText(mDate);
            tvPreFactorNum.setText(mPreFactorNum);
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
        p1.setValue(mBusinessID);
        p1.setType(Integer.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("businessTypeId");
        p2.setValue(3);
        p2.setType(Integer.class);

        final SoapCall ss = new SoapCall((AppCompatActivity) Objects.requireNonNull(getActivity()), SoapCall.METHOD_GET_BUSINESS_DETAILS);
        ss.execute(p0, p1, p2);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list = ss.get();
                    if (getActivity() != null) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (list != null) {

                                    detailsList = new ArrayList<>(list.size());
                                    DetailsModel model;
                                    for (int i = 0; i < list.size(); i++) {
                                        model = new DetailsModel();
                                        try {
                                            model.setOnHand(list.get(i).getInt("onHand"));
                                            model.setProductName(list.get(i).getString("ProductName"));
                                            model.setQty(list.get(i).getInt("Qty"));
                                            model.setTechNo(list.get(i).getString("TechNo"));
                                            model.setUnitName(list.get(i).getString("UnitName"));

                                            detailsList.add(model);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.i(TAG, "run: " +  e.getMessage());
                                        }

                                    }
                                    tvListSize.setText(String.valueOf(detailsList.size()));
                                    permAdapter = new PermListInfoAllAdapter(detailsList);
                                    rvPermListStuff.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                    //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
                                    rvPermListStuff.setAdapter(permAdapter);

                                } else {
                                    Toast.makeText(getActivity(), "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Log.i(TAG, "run: " +  e.getMessage());
                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
