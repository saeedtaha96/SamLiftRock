package com.samlifttruck.activity.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.DfpProductDetailsAdapter;
import com.samlifttruck.activity.Adapters.DraftListInfoAllAdapter;
import com.samlifttruck.activity.Models.UnitInfoModel;
import com.samlifttruck.activity.RegDFPActivity;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;
import com.samlifttruck.activity.Models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class DraftListInfoAllFragment extends Fragment {
    private List<JSONObject> list = null;
    private List<JSONObject> list2 = null;
    private List<DetailsModel> detailsList;
    private Map<String, Integer> draftTypeMap;
    private static boolean isFirst = true;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DRAFT_NUM_KEY = "draftNum";
    private static final String DATE_KEY = "date";
    private static final String CUSTOMER_NAME_KEY = "custName";
    private static final String PERM_NUMB_KEY = "permNum";
    private static final String DRAFT_TYPE_KEY = "draftType";
    private static final String SERVICE_PAGE_KEY = "servicePage";
    private static final String DESCRIPTION_KEY = "descrip";
    private static final String BUSINESS_ID_KEY = "businessID";
    private static final String SOURCE_ACTIVITY_KEY = "sourceActivity";


    public static final int ACTIVITY_DFP = 0;
    public static final int ACTIVITY_DRAFT_LIST = 1;

    // Views
    private TextView tvDraftNum, tvPermNum, tvCustName, tvDraftType, tvDate, tvServicePage, tvDescrip;
    TextView tvListSize;
    RecyclerView rvDraftListStuff;
    DraftListInfoAllAdapter draftAdapter;
    DfpProductDetailsAdapter detailsAdapter;
    TableRow tblDraftNum, tblServicePage;


    // TODO: Rename and change types of parameters
    private String mDraftNum;
    private String mDate;
    private String mCustName;
    private String mPermNum;
    private String mDraftType;
    private String mServicePage;
    private String mDescrip;
    private int mBusinessID;
    private int mSourceActivity;
    private int mUserId;


    public DraftListInfoAllFragment() {
        // Required empty public constructor
    }


    public static DraftListInfoAllFragment newInstance(int sourceActivity, int businessID, String draftNum, String permNum, String custName, String date, String draftType, String servicePage, String descrip) {
        DraftListInfoAllFragment fragment = new DraftListInfoAllFragment();
        Bundle args = new Bundle();
        args.putString(DRAFT_NUM_KEY, draftNum);
        args.putString(PERM_NUMB_KEY, permNum);
        args.putString(CUSTOMER_NAME_KEY, custName);
        args.putString(DATE_KEY, date);
        args.putString(DRAFT_TYPE_KEY, draftType);
        args.putString(SERVICE_PAGE_KEY, servicePage);
        args.putString(DESCRIPTION_KEY, descrip);
        args.putInt(BUSINESS_ID_KEY, businessID);
        args.putInt(SOURCE_ACTIVITY_KEY, sourceActivity);
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
            mBusinessID = getArguments().getInt(BUSINESS_ID_KEY);
            mSourceActivity = getArguments().getInt(SOURCE_ACTIVITY_KEY);

            SharedPreferences pref = Objects.requireNonNull(getActivity()).getSharedPreferences("myprefs", Context.MODE_PRIVATE);
            mUserId = pref.getInt(Utility.LOGIN_USER_ID, 1);

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
        tvListSize = rootView.findViewById(R.id.fragment_draft_list_info_all_list_size);
        tblDraftNum = rootView.findViewById(R.id.fragment_draft_info_all_table_draft_num);
        tblServicePage = rootView.findViewById(R.id.fragment_draft_info_all_table_service_page);


        if (getArguments() != null) {
            tvDraftNum.setText(mDraftNum);
            tvPermNum.setText(mPermNum);
            tvCustName.setText(mCustName);
            tvDate.setText(mDate);
            tvDraftType.setText(mDraftType);
            tvServicePage.setText(mServicePage);
            tvDescrip.setText(mDescrip);


            if (mSourceActivity == ACTIVITY_DFP) {
                tblDraftNum.setVisibility(View.GONE);
                tblServicePage.setVisibility(View.GONE);
                ImageButton btnConfrim = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar_imgbtn_reg_dfp);
                btnConfrim.setVisibility(View.VISIBLE);
                btnConfrim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iOSDialogBuilder ios = Utility.newIOSdialog(getActivity());
                        ios.setTitle(mCustName)
                                .setSubtitle("اطمینان از ثبت حواله به مشتری فوق دارید؟")
                                .setPositiveListener("بله", new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                      if(tvDraftType.getText().toString().equals("")){
                                          tvDraftType.requestFocus();
                                          tvDraftType.setError(getString(R.string.please_fill));
                                      }else {
                                          acceptBussiness();
                                      }
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
                getDraftTypes();

                // getPermProductList();
                //  tvListSize.setText(String.valueOf(detailsList.size()));
                // rvDraftListStuff.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                // detailsAdapter = new DfpProductDetailsAdapter(getActivity(), detailsList);
                // rvDraftListStuff.setAdapter(detailsAdapter);

            } else if (mSourceActivity == ACTIVITY_DRAFT_LIST) {
                getProducts();
                tvDraftType.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                tvDescrip.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                tvDescrip.setEnabled(false);

            }

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
        p2.setValue(4);
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
                                        }

                                    }
                                    tvListSize.setText(String.valueOf(detailsList.size()));
                                    rvDraftListStuff.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                    draftAdapter = new DraftListInfoAllAdapter(detailsList);
                                    rvDraftListStuff.setAdapter(draftAdapter);


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

    public void getDraftTypes() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        final SoapCall ss = new SoapCall((AppCompatActivity) getActivity(), SoapCall.METHOD_GET_DRAFT_TYPES);
        ss.execute(p0);


        SoapCall.execute(new Runnable() {


            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list2 = ss.get();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (list2 != null) {
                                    draftTypeMap = new HashMap<>(list2.size());
                                    for (int i = 0; i < list2.size(); i++) {
                                        try {
                                            draftTypeMap.put(list2.get(i).getString("HavalehTypeName"), list2.get(i).getInt("HavalehTypeID"));


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    setDialogDraftType(draftTypeMap);

                                    getDfpProducts(mBusinessID);

                                } else {
                                    Toast.makeText(getActivity(), "خطا در بارگیری DropDown", Toast.LENGTH_SHORT).show();
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

    private void setDialogDraftType(Map<String, Integer> hashMap) {
        Collection<String> keysets = hashMap.keySet();
        final String[] draftTypeItems = keysets.toArray(new String[keysets.size()]);

        tvDraftType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                mBuilder.setTitle("نوع حواله را انتخاب کنید:");
                mBuilder.setSingleChoiceItems(draftTypeItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvDraftType.setText(draftTypeItems[i]);
                        if (draftTypeMap.get(draftTypeItems[i]) != null)
                            dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFirst = true;
    }

    private void getDfpProducts(int businessId) {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("businessTypeId");
        p1.setValue(4);
        p1.setType(Integer.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("userId");
        p2.setValue(mUserId);
        p2.setType(Integer.class);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("businessId");
        p3.setValue(businessId);
        p3.setType(Integer.class);


        final SoapCall ss = new SoapCall((AppCompatActivity) Objects.requireNonNull(getActivity()), SoapCall.METHOD_GET_TEMP_BUSINESS_DETAILS);
        ss.execute(p0, p1, p2, p3);


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
                                            model.setProductCode(list.get(i).getInt("ProductCode"));
                                            model.setUnitId(list.get(i).getInt("UnitID"));
                                            model.setOnHand(list.get(i).getInt("OnHand"));
                                            model.setProductName(list.get(i).getString("ProductName"));
                                            model.setQty(list.get(i).getInt("Qty"));
                                            model.setRow(list.get(i).getInt("Row"));
                                            model.setTechNo(list.get(i).getString("TechNo"));
                                            model.setUnitName(list.get(i).getString("UnitName"));

                                            detailsList.add(model);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    tvListSize.setText(String.valueOf(detailsList.size()));
                                    rvDraftListStuff.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                    detailsAdapter = new DfpProductDetailsAdapter(getActivity(), detailsList);
                                    rvDraftListStuff.setAdapter(detailsAdapter);


                                } else {
                                   // Toast.makeText(getActivity(), "موردی یافت نشد", Toast.LENGTH_SHORT).show();
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

    private void acceptBussiness() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("userId");
        p1.setValue(mUserId);
        p1.setType(Integer.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("personId");
        p2.setValue("");
        p2.setType(Integer.class);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("txtCustomerName");
        p3.setValue(mCustName);
        p3.setType(String.class);

        PropertyInfo p4 = new PropertyInfo();
        p4.setName("pDate");
        p4.setValue(mDate);
        p4.setType(String.class);

        PropertyInfo p5 = new PropertyInfo();
        p5.setName("businessTypes");
        p5.setValue(4);
        p5.setType(Byte.class);

        PropertyInfo p6 = new PropertyInfo();
        p6.setName("mojavezId");
        p6.setValue(mPermNum);
        p6.setType(Integer.class);

        PropertyInfo p7 = new PropertyInfo();
        p7.setName("txtDescription1");
        p7.setValue(tvDescrip.getText().toString());
        p7.setType(String.class);

        PropertyInfo p8 = new PropertyInfo();
        p8.setName("txtDescription2");
        p8.setValue(" ");
        p8.setType(String.class);

        PropertyInfo p9 = new PropertyInfo();
        p9.setName("txtDescription3");
        p9.setValue(" ");
        p9.setType(String.class);

        PropertyInfo p10 = new PropertyInfo();
        p10.setName("havaleType");
        p10.setValue(draftTypeMap.get(tvDraftType.getText().toString()));
        p10.setType(Byte.class);

        final SoapCall ss = new SoapCall((AppCompatActivity) getActivity(), SoapCall.METHOD_ACCEPT_BUSINESS);
        ss.execute(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                final List<JSONObject> myList;
                try {
                    myList = ss.get();
                    if (getActivity() != null) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (myList != null) {

                                    try {
                                        if (myList.get(0).getString("boolean").equals("true")) {
                                            Toast.makeText(getActivity(), "مورد با موفقیت ثبت شد", Toast.LENGTH_LONG).show();

                                            // TODO: whats gonna do after this
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        getActivity().finish();
                                                    }
                                                }, 1500);
                                            //
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

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
