package com.anubhuti.knit.Fragments;


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
import com.anubhuti.knit.Model.TeamDetail;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.TeamResponse;
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
public class TeamFragment extends Fragment {

    private static final String EXTRA_TEXT = "text";
    private DatabaseReference mDatabase;
    private List<TeamDetail> list=new ArrayList<>();
    private TeamResponse teamResponse=new TeamResponse();
    View view;
    RecyclerView teamrecyclerview;
    TeamviewAdapter teamviewAdapter;


    public static TeamFragment createFor(String text) {
        TeamFragment fragment = new TeamFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_team, container, false);
        teamrecyclerview= (RecyclerView)view.findViewById(R.id.team_recyclerview);
        teamrecyclerview.setHasFixedSize(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("TeamDetails");

        getFirebaseData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        teamrecyclerview.setLayoutManager(layoutManager);
       // teamviewAdapter = new TeamviewAdapter(list);
     //   teamrecyclerview.setAdapter(teamviewAdapter);
       // Toast.makeText(getActivity(),"fsnjns",Toast.LENGTH_LONG).show();

        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        final String text = getArguments().getString(EXTRA_TEXT);
      //  mDatabase = FirebaseDatabase.getInstance().getReference("TeamDetails");

//        TextView textView = view.findViewById(R.id.text);
//        textView.setText("hh");
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                Toast.makeText(v.getContext(), "hh", Toast.LENGTH_SHORT).show();
//            }
//        });

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
                TeamviewAdapter teamviewAdapter = new TeamviewAdapter(list);
                teamrecyclerview.setAdapter(teamviewAdapter);

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ErrorTAG", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    private void showData() {

        teamResponse.setTeamDetails(list);
        for(int i=0;i<list.size();i++)
            Config.toastShort(getContext(),list.get(i).getName());
    }
}
