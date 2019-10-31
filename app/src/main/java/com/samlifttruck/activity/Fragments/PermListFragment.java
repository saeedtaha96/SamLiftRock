package com.samlifttruck.activity.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.PermListAdapter;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;
import com.samlifttruck.activity.Models.PermListModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PermListFragment extends Fragment {
    List<JSONObject> list = null;
    List<PermListModel> permList;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DATE = "date";

    // TODO: Rename and change types of parameters
    private String mDate;
    RecyclerView rvPermList;
    PermListAdapter permListAdapter;
    ProgressBar progressBar;

    public PermListFragment() {
        // Required empty public constructor
    }


    public static PermListFragment newInstance(String date) {
        PermListFragment fragment = new PermListFragment();
        Bundle args = new Bundle();
        args.putString(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDate = getArguments().getString(DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv_list, container, false);
        rvPermList = view.findViewById(R.id.activity_rv_list_recyclerview);

        getPermList();

        return view;
    }

    private void getPermList() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("pDate");
        p1.setValue(mDate);
        p1.setType(String.class);

        final SoapCall ss = new SoapCall((AppCompatActivity) Objects.requireNonNull(getActivity()), SoapCall.METHOD_GET_PERM_LIST);
        ss.execute(p0, p1);


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

                                    permList = new ArrayList<>(list.size());
                                    PermListModel model;
                                    for (int i = 0; i < list.size(); i++) {
                                        model = new PermListModel();
                                        try {
                                            model.setBusinessID(list.get(i).getInt("BusinessID"));
                                            model.setPreFactorNum(list.get(i).getString("ReferalBusinessNominal"));
                                            model.setPermNum(list.get(i).getString("BusinessNominal"));
                                            model.setCustName(list.get(i).getString("PersonName"));
                                            model.setCondition(list.get(i).getString("StatusName"));
                                            model.setDate(list.get(i).getString("PersianBusinessDate"));
                                            model.setDescrip(list.get(i).getString("Description1"));
                                            permList.add(model);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    permListAdapter = new PermListAdapter(permList);
                                    rvPermList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                    //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
                                    rvPermList.setAdapter(permListAdapter);

                                } else {
                                    Toast.makeText(getActivity(), "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
