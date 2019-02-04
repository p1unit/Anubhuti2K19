package com.anubhuti.knit.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anubhuti.knit.Adapter.PastAndFutureAdapter;
import com.anubhuti.knit.Migration.FireBaseData;
import com.anubhuti.knit.Model.PastFutureData;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.PastFutureResponse;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
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
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView upcomingRecycler;
    RecyclerView pastRecycler;
    RecyclerView addressRecycler;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase3;
    private DatabaseReference mDatabase2;
    private PastFutureResponse response1;
    private PastFutureResponse response2;
    private PastFutureResponse response3;
    private FireBaseData fireBaseData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        upcomingRecycler=(RecyclerView)view.findViewById(R.id.upcoming_recycler);
        pastRecycler=(RecyclerView)view.findViewById(R.id.past_recycler);
        addressRecycler=(RecyclerView)view.findViewById(R.id.address_recycler);
        fireBaseData=new FireBaseData();
        response1=new PastFutureResponse();
        response2=new PastFutureResponse();
        response3=new PastFutureResponse();

        upcomingRecycler.setHasFixedSize(true);
        pastRecycler.setHasFixedSize(true);
        addressRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ApplicationContextProvider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(ApplicationContextProvider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(ApplicationContextProvider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        upcomingRecycler.setLayoutManager(layoutManager);
        pastRecycler.setLayoutManager(layoutManager2);
        addressRecycler.setLayoutManager(layoutManager3);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(upcomingRecycler);
        SnapHelper snapHelper2 = new PagerSnapHelper();
        SnapHelper snapHelper3 = new PagerSnapHelper();
        snapHelper2.attachToRecyclerView(pastRecycler);
        snapHelper3.attachToRecyclerView(addressRecycler);

        setGloriousPast();
        setAddressData();
    }

    private void getFirebaseData1() {

        final List<PastFutureData> list=new ArrayList<>();

        mDatabase1.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    PastFutureData data=postSnapshot.getValue(PastFutureData.class);
                    list.add(data);

                }
                response1.setData(list);
                fireBaseData.setUpcoming(response1);
                showData1(list);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("ErrorTAG", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    private void getStoredData1() {
        try {
            List<PastFutureData> list = fireBaseData.getUpcoming();
            showData2(list);
        }catch (NullPointerException ignored){

        }
    }

    private void showData1(List<PastFutureData> list) {
    }

    private void setGloriousPast() {

        mDatabase2= FirebaseDatabase.getInstance().getReference("glorious_past");
        getStoredData2();   // getting stored Data
        getFirebaseData2();
    }

    private void getFirebaseData2() {

        final List<PastFutureData> list=new ArrayList<>();

        mDatabase2.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    PastFutureData data=postSnapshot.getValue(PastFutureData.class);
                    list.add(data);

                }
                response2.setData(list);
                fireBaseData.setGloriousPast(response2);
                showData2(list);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("ErrorTAG", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    private void getStoredData2() {
        try {
            List<PastFutureData> list = fireBaseData.getGloriousPast();
            showData2(list);
        }catch (NullPointerException ignored){

        }
    }

    private void showData2(List<PastFutureData> list) {

        PastAndFutureAdapter pastAndFutureAdapter=new PastAndFutureAdapter(list);
        pastRecycler.setAdapter(pastAndFutureAdapter);
        pastRecycler.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);
    }



    private void setAddressData() {
        mDatabase3= FirebaseDatabase.getInstance().getReference("address");
        getStoredData3();   // getting stored Data
        getFirebaseData3();
    }

    private void getFirebaseData3() {

        final List<PastFutureData> list=new ArrayList<>();

        mDatabase3.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    PastFutureData data=postSnapshot.getValue(PastFutureData.class);
                    list.add(data);

                }
                response3.setData(list);
                fireBaseData.setAddress(response3);
                showData3(list);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("ErrorTAG", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    private void getStoredData3() {
        try {
            List<PastFutureData> list = fireBaseData.getAddress();
            showData3(list);
        }catch (NullPointerException ignored){

        }
    }

    private void showData3(List<PastFutureData> list) {

        PastAndFutureAdapter pastAndFutureAdapter=new PastAndFutureAdapter(list);
        addressRecycler.setAdapter(pastAndFutureAdapter);
        addressRecycler.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);
    }

}
