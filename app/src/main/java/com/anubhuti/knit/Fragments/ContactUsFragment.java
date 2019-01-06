package com.anubhuti.knit.Fragments;


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

import com.anubhuti.knit.Adapter.ContactUsAdapter;
import com.anubhuti.knit.Migration.FireBaseData;
import com.anubhuti.knit.Model.ContactUs;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.ContactUsResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ContactUsFragment extends Fragment {

    private DatabaseReference databaseReference;
    private List<ContactUs> list=new ArrayList<>();
    private ContactUsResponse response;
    RecyclerView  recyclerView;
    private ContactUsAdapter adapter;
    private FireBaseData fireBaseData;


    public ContactUsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView=(RecyclerView)view.findViewById(R.id.contact_recycler);
        recyclerView.setHasFixedSize(true);
        databaseReference=FirebaseDatabase.getInstance().getReference("ContactUs");

        fireBaseData =new FireBaseData();
        response=new ContactUsResponse();
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

        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ContactUs detail=postSnapshot.getValue(ContactUs.class);

                    list.add(detail);

                }
                response.setContactUs(list);
                fireBaseData.setContactUsData(response);
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
            List<ContactUs> list2 = fireBaseData.getContactUsData();
            showData(list2);
        }catch (NullPointerException ignored){

        }
    }

    private void showData(List<ContactUs> list2) {

        Log.e("lisiht",String.valueOf(list2.size()));
        adapter=new ContactUsAdapter(list2);
        recyclerView.setAdapter(adapter);
    }

}
