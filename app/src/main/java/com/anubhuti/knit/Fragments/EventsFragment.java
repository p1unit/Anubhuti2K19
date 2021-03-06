package com.anubhuti.knit.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
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

import com.anubhuti.knit.Activities.EventListActivity;
import com.anubhuti.knit.Adapter.CategoryAdapter;
import com.anubhuti.knit.Interfaces.EventListInterface;
import com.anubhuti.knit.Listener.CategoryListner;
import com.anubhuti.knit.Migration.FireBaseData;
import com.anubhuti.knit.Model.EventCatogry;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.EventCategoryResponse;
import com.anubhuti.knit.Response.EventTypeResponse;
import com.anubhuti.knit.Services.EventListService;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EventsFragment extends Fragment implements CategoryListner,EventListInterface {

    private DatabaseReference mDatabase;
    private List<EventCatogry> list=new ArrayList<>();
    private EventCategoryResponse response=new EventCategoryResponse();
    private RecyclerView recyclerView;
    private FireBaseData fireBaseData;
    private String name="";
    private ProgressDialog pd;
    private EventCatogry obj;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

        recyclerView=(RecyclerView)view.findViewById(R.id.event_category_recycler);
        recyclerView.setHasFixedSize(true);
        mDatabase=FirebaseDatabase.getInstance().getReference("EventCatogry");

        showPd();

        fireBaseData=new FireBaseData();

        getStoredData();   // getting stored Data
        getFirebaseData(); // getting online Data

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    }

    private void showPd() {

        pd=new ProgressDialog(getActivity());
        pd.setMessage("Please Wait for a Sec");
        pd.setCancelable(false);
        pd.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(pd.isShowing())
                            Config.toastShort(ApplicationContextProvider.getContext(),Config.ERROR_TOAST);
                        pd.dismiss();
                    }
                }, 10000);

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
                    EventCatogry item=postSnapshot.getValue(EventCatogry.class);
                    //   Log.e("Team",teamDetail.getName());
                    list.add(item);
                    //                 Log.d("team",)

                }
                response.setEventCatogry(list);
                fireBaseData.setCategoryData(response);
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
            List<EventCatogry> list2 = fireBaseData.getCategoryData();
            showData(list2);
        }catch (NullPointerException ignored){

        }
    }

    private void showData(List<EventCatogry> list2) {

        CategoryAdapter adapter = new CategoryAdapter(this,list2);
        recyclerView.setAdapter(adapter);
        pd.dismiss();
    }

    @Override
    public void callId(EventCatogry obj) {

        this.obj=obj;
        pd.show();
        this.name=name;
        EventListService eventListService=new EventListService(this);
        eventListService.getEventList(obj.getId());

    }

    @Override
    public void setEventList(EventTypeResponse response) {
        Intent intent=new Intent(ApplicationContextProvider.getContext(),EventListActivity.class);
        intent.putExtra("listEvent",response);
        intent.putExtra("name",obj.getName());
        intent.putExtra("EventData",obj);
        startActivity(intent);
        pd.dismiss();

    }

    @Override
    public void errorResponse() {
        pd.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }
}
