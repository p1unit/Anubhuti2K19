package com.anubhuti.knit.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anubhuti.knit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SponsorsPageFragment extends Fragment {
    View view;
    FirebaseDatabase database;
    DatabaseReference reference;


    public SponsorsPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sponsors_page, container, false);
         final TextView textView = (TextView)view.findViewById(R.id.tv);
        database =FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance();
        reference=database.getReference("SponsorDetail");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             //   list = new ArrayList<>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                   long a= dataSnapshot1.getChildrenCount();
                    textView.append(dataSnapshot1.getKey());


                   // Log.

               //     SponsorDetail listdata=new SponsorDetail();
//                            ListData listData = dataSnapshot1.getValue(ListData.class);
//                            String name = listData.getName();
                  //  listdata.setName(dataSnapshot1.child("description").getValue(String.class));
//                    String name =listdata.getName();
                    //sponsortext.setText(name);

                    // String name = sponsorDetail.getName();
                    //listdata.setName(sponsorDetail);

                    //list.add(sponsorDetail);
                }
//                LinearLayoutManager layoutManager = new LinearLayoutManager(SponsorActivity.this);
//                recyclerView.setLayoutManager(layoutManager);
//                SponsorAdapter recycler= new SponsorAdapter(list);
//                recyclerView.setAdapter(recycler);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        return  view;

   }

}
