package com.anubhuti.knit.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anubhuti.knit.Adapter.PastAndFutureAdapter;
import com.anubhuti.knit.Migration.FireBaseData;
import com.anubhuti.knit.Model.PastFutureData;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.PastFutureResponse;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import cn.iwgang.countdownview.CountdownView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener, YouTubePlayer.PlayerStateChangeListener {


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
    private CountdownView mCvCountdownView;
    private int num=2;
    private ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        num=2;
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

        pd=new ProgressDialog(getActivity());
        pd.setMessage("Please Wait for a Sec");
        pd.setCancelable(false);
        pd.show();

        num=3;

        mCvCountdownView = view.findViewById(R.id.counter);

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

        dateTimeStuff();

        setGloriousPast();
        setAddressData();
        setUpcoming();


        // youtube set

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize("AIzaSyAqP021C3PpCUgxj1AnTixCEsl6xRJW1QA",this);

    }


    private void dateTimeStuff() {

//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date firstDate = new Date();
        Date secondDate = new Date(1550773800000L);

        Log.e("Dates",firstDate+" -- "+secondDate);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        Log.e("Time",diffInMillies+" -- "+diff);


        mCvCountdownView.start(diffInMillies);
    }



    private void setUpcoming() {
        mDatabase1= FirebaseDatabase.getInstance().getReference("upcoming");
        getStoredData1();   // getting stored Data
        getFirebaseData1();

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
            showData1(list);
        }catch (NullPointerException ignored){

        }
    }

    private void showData1(List<PastFutureData> list) {

        PastAndFutureAdapter pastAndFutureAdapter=new PastAndFutureAdapter(list);
        upcomingRecycler.setAdapter(pastAndFutureAdapter);
        upcomingRecycler.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);

        num-=1;
        if(num==0)
            pd.dismiss();
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
                if(num!=0)
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


//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new ScrollTask(pastAndFutureAdapter,pastRecycler), 2000, 5000);

        num-=1;
        if(num==0)
            pd.dismiss();
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

        num-=1;
        if(num==0)
            pd.dismiss();
    }

    private class ScrollTask extends TimerTask {

        PastAndFutureAdapter adapter;
        RecyclerView recyclerView;
        int position= Integer.MAX_VALUE/2;

        public ScrollTask(PastAndFutureAdapter adapter, RecyclerView recyclerView) {
            this.adapter = adapter;
            this.recyclerView = recyclerView;
        }

        public void run() {
            pastRecycler.post(new Runnable() {
                public void run() {

                    position++;
                    recyclerView.smoothScrollToPosition(position);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {

        player.setPlaybackEventListener(this);
        player.setPlayerStateChangeListener(this);

        if (!b) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            player.loadVideo("2dyD9_cJ9Q8");
            player.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        String errorMessage = youTubeInitializationResult.toString();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        Log.d("errorMessage:", errorMessage);
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

}
