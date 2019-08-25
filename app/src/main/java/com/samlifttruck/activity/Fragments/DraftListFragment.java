package com.samlifttruck.activity.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.DraftListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;

public class DraftListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DATE = "date";

    // TODO: Rename and change types of parameters
    private String mDate;
    private RecyclerView rvDraftList;
    private DraftListAdapter draftListAdapter;

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
        View view = inflater.inflate(R.layout.fragment_draft_list, container, false);
        rvDraftList = view.findViewById(R.id.activity_draft_recyclerview);
        DraftListAdapter draftListAdapter = new DraftListAdapter(DataGenerator.getDraftList());
        rvDraftList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
        rvDraftList.setAdapter(draftListAdapter);
        return view;
    }



}
