package com.anubhuti.knit.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anubhuti.knit.Adapter.SponserApapter;
import com.anubhuti.knit.Migration.FireBaseData;
import com.anubhuti.knit.Model.SponserDetail;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.SponserResponse;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SponserFragment extends Fragment {

    private DatabaseReference mDatabase;
    private List<SponserDetail> list=new ArrayList<>();
    private SponserResponse sponserResponse=new SponserResponse();
    View view;
    RecyclerView recyclerView;
    SponserApapter sponserApapter;
    private FireBaseData  fireBaseData;
    private ProgressDialog pd;


    public SponserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sponser, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView=(RecyclerView)view.findViewById(R.id.sponser_recycler);
        recyclerView.setHasFixedSize(true);
        mDatabase=FirebaseDatabase.getInstance().getReference("SponserDetail");

        pd=new ProgressDialog(getActivity());
        pd.setMessage("Please Wait for a Sec");
        pd.setCancelable(false);
        pd.show();

        fireBaseData=new FireBaseData();

        getStoredData();   // getting stored Data
        getFirebaseData(); // getting online Data

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void onStart() {
        super.onStart();
        // getFirebaseData();
    }

    private void getFirebaseData(){

        mDatabase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    SponserDetail teamDetail=postSnapshot.getValue(SponserDetail.class);
                    //   Log.e("Team",teamDetail.getName());
                    list.add(teamDetail);
                    //                 Log.d("team",)

                }
                sponserResponse.setSponserDetail(list);
                fireBaseData.setSponserData(sponserResponse);
                showData(list);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("ErrorTAG", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    private void getStoredData(){
        try {
            List<SponserDetail> sponderList = fireBaseData.getSponserData();
            showData(sponderList);
        }catch (NullPointerException ignored){

        }
    }

    private void showData(List<SponserDetail> sponserList) {

        Log.e("list",sponserList.get(0).getName()+" "+sponserList.get(1).getName());

        sponserApapter=new SponserApapter(sponserList);
        recyclerView.setAdapter(sponserApapter);
        pd.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }
}

