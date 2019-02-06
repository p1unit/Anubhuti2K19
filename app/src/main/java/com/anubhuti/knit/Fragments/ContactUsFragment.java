package com.anubhuti.knit.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.anubhuti.knit.Listener.ContactListener;
import com.anubhuti.knit.Migration.FireBaseData;
import com.anubhuti.knit.Model.ContactUs;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.ContactUsResponse;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;


public class ContactUsFragment extends Fragment implements ContactListener {

    private DatabaseReference databaseReference;
    private List<ContactUs> list=new ArrayList<>();
    private ContactUsResponse response;
    RecyclerView  recyclerView;
    private ContactUsAdapter adapter;
    private FireBaseData fireBaseData;
    private String person_info="";
    boolean isPhone=true;
    private ProgressDialog pd;


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

        pd=new ProgressDialog(getActivity());
        pd.setMessage("Please Wait for a Sec");
        pd.setCancelable(false);
        pd.show();

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
        adapter=new ContactUsAdapter(list2,this);
        recyclerView.setAdapter(adapter);
        pd.dismiss();
    }

    @Override
    public void phoneClicked(String s) {

        isPhone=true;
        person_info=s;
        askPermission();
    }

    @Override
    public void mailClicked(String s) {

        isPhone=false;
        person_info=s;
        askPermission();
    }


    private void askPermission() {

        TedPermission.with(ApplicationContextProvider.getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this feature\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CALL_PHONE)
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            if(isPhone)
                call();
            else
                mail();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            Config.toastShort(ApplicationContextProvider.getContext(), Config.PERMISSION_REQUEST);
        }


    };

    private void call(){
        String s=person_info;
        String ss="tel:"+ s;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(ss));
        startActivity(intent);
    }

    private void mail(){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        String s=person_info;
        String ss="mailto:"+ s +"?subject=" + "" + "&body=" + "";
        Uri data = Uri.parse(ss);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }


}
