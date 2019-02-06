package com.anubhuti.knit.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anubhuti.knit.Adapter.TeamviewAdapter;
import com.anubhuti.knit.Interfaces.TeamInterface;
import com.anubhuti.knit.Migration.FireBaseData;
import com.anubhuti.knit.Model.TeamDetail;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.TeamResponse;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment implements TeamInterface {

    private static final String EXTRA_TEXT = "text";
    private DatabaseReference mDatabase;
    private List<TeamDetail> list=new ArrayList<>();
    private TeamResponse teamResponse=new TeamResponse();
    View view;
    RecyclerView teamrecyclerview;
    private FireBaseData fireBaseData;
    private String person_info="";

    private ProgressDialog pd;


//    public static TeamFragment createFor(String text) {
//        TeamFragment fragment = new TeamFragment();
//        Bundle args = new Bundle();
//        args.putString(EXTRA_TEXT, text);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_team, container, false);

        teamrecyclerview= (RecyclerView)view.findViewById(R.id.team_recyclerview);
        teamrecyclerview.setHasFixedSize(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("TeamDetails");
//        mDatabase.
        fireBaseData = new FireBaseData();

        getStoredData();   // getting stored Data
        getFirebaseData(); // getting online Data

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        teamrecyclerview.setLayoutManager(layoutManager);

        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.show();


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        final String text = getArguments().getString(EXTRA_TEXT);


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
                    TeamDetail teamDetail=postSnapshot.getValue(TeamDetail.class);
                //   Log.e("Team",teamDetail.getName());
                    list.add(teamDetail);
   //                 Log.d("team",)

                }
                teamResponse.setTeamDetails(list);
                fireBaseData.setTeamData(teamResponse);
                showData(list);


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ErrorTAG", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    private void getStoredData(){
        try {
            List<TeamDetail> teamList = fireBaseData.getTaemData();
            showData(teamList);
        }catch (NullPointerException ignored){

        }
    }

    private void showData(List<TeamDetail> teamList) {

        Collections.sort(teamList,new Comparator<TeamDetail>() {
            @Override
            public int compare(TeamDetail o1, TeamDetail o2) {
                return Integer.parseInt(o1.getPriority())-Integer.parseInt(o2.getPriority());
            }
        });

        TeamviewAdapter teamviewAdapter = new TeamviewAdapter(teamList,this);
        teamrecyclerview.setAdapter(teamviewAdapter);

        pd.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }

    @Override
    public void fbClick(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/anubhutiknit/"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/anubhutiknit/")));
        }
    }

    @Override
    public void phoneClick(String number) {

        person_info=number;
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

            call();
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

    @Override
    public void emailClick(String email) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String s=email;
        String ss="mailto:"+ s +"?subject=" + "" + "&body=" + "";
        Uri data = Uri.parse(ss);
        intent.setData(data);
        startActivity(intent);
    }
}
