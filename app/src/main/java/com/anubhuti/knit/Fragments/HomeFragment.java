package com.anubhuti.knit.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anubhuti.knit.Adapter.PastAndFutureAdapter;
import com.anubhuti.knit.Model.PastFutureData;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView=(RecyclerView)view.findViewById(R.id.upcoming_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ApplicationContextProvider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<PastFutureData> lis=new ArrayList<>();
        PastFutureData obj=new PastFutureData("Puneet","https://firebasestorage.googleapis.com/v0/b/anubhuti-14e39.appspot.com/o/dance%2FSOLO-DANCE%2Fimg1.jpg?alt=media&token=e01d96c3-5ff8-4535-afbb-56000558f823");

        lis.add(obj);
        lis.add(obj);
        lis.add(obj);
        lis.add(obj);
        PastAndFutureAdapter pastAndFutureAdapter=new PastAndFutureAdapter(lis);

        recyclerView.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);
        recyclerView.setAdapter(pastAndFutureAdapter);
    }
}
