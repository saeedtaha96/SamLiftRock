package com.samlifttruck.activity.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.DraftListAdapter;
import com.samlifttruck.activity.Adapters.PermListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;
import com.samlifttruck.activity.Models.DraftListModel;
import com.samlifttruck.activity.Models.PermListModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class DraftListFragment extends Fragment {
    List<JSONObject> list = null;
    List<DraftListModel> draftList;
    SoapCall ss;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DATE = "date";

    // TODO: Rename and change types of parameters
    private String mDate;
    RecyclerView rvDraftList;
    DraftListAdapter draftListAdapter;

    public DraftListFragment() {
        // Required empty public constructor
    }


    public static DraftListFragment newInstance(String date) {
        DraftListFragment fragment = new DraftListFragment();
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
        rvDraftList = view.findViewById(R.id.activity_rv_list_recyclerview);

        getDraftList();
        return view;
    }

    private void getDraftList() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("pDate");
        p1.setValue(mDate);
        p1.setType(String.class);

        ss = new SoapCall((AppCompatActivity) Objects.requireNonNull(getActivity()), SoapCall.METHOD_GET_DRAFT_LIST);
        ss.execute(p0, p1);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    list = ss.get();

                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {

                                draftList = new ArrayList<>(list.size());
                                DraftListModel model;
                                for (int i = 0; i < list.size(); i++) {
                                    model = new DraftListModel();
                                    try {
                                        model.setBusinessID(list.get(i).getString("BusinessID"));
                                        model.setPermNum(list.get(i).getString("ReferalBusinessNominal"));
                                        model.setDraftNum(list.get(i).getString("BusinessNominal"));
                                        model.setReceiver(list.get(i).getString("PersonName"));
                                        model.setCondition(list.get(i).getString("StatusName"));
                                        model.setDate(list.get(i).getString("PersianBusinessDate"));
                                        model.setDescription(list.get(i).getString("Description1"));
                                        model.setDraftType(list.get(i).getString("HavalehTypeName"));

                                        draftList.add(model);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                draftListAdapter = new DraftListAdapter(draftList);
                                rvDraftList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
                                rvDraftList.setAdapter(draftListAdapter);

                            } else {
                                Toast.makeText(getActivity(), "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ss.cancel(true);
    }
}
