package com.anubhuti.knit.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.util.DiffUtil;
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
import com.anubhuti.knit.Model.YoutubeId;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.PastFutureResponse;
import com.anubhuti.knit.Response.YoutubeList;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.TinderSlider.CardStackAdapter;
import com.anubhuti.knit.TinderSlider.SpotDiffCallback;
import com.anubhuti.knit.Utils.Config;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cn.iwgang.countdownview.CountdownView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener, YouTubePlayer.PlayerStateChangeListener, CardStackListener {


    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView upcomingRecycler;
//    RecyclerView pastRecycler;
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
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private CardStackView cardStackView;

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
//        pastRecycler=(RecyclerView)view.findViewById(R.id.past_recycler);
        addressRecycler=(RecyclerView)view.findViewById(R.id.address_recycler);
        fireBaseData=new FireBaseData();
        response1=new PastFutureResponse();
        response2=new PastFutureResponse();
        response3=new PastFutureResponse();

        showPd();

        num=3;

        mCvCountdownView = view.findViewById(R.id.counter);

        upcomingRecycler.setHasFixedSize(true);
//        pastRecycler.setHasFixedSize(true);
        addressRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ApplicationContextProvider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(ApplicationContextProvider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(ApplicationContextProvider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        upcomingRecycler.setLayoutManager(layoutManager);
//        pastRecycler.setLayoutManager(layoutManager2);
        addressRecycler.setLayoutManager(layoutManager3);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(upcomingRecycler);
        SnapHelper snapHelper2 = new PagerSnapHelper();
        SnapHelper snapHelper3 = new PagerSnapHelper();
//        snapHelper2.attachToRecyclerView(pastRecycler);
        snapHelper3.attachToRecyclerView(addressRecycler);

        cardStackView = view.findViewById(R.id.card_stack_view);

        dateTimeStuff();

        setGloriousPast();
        setAddressData();
        setUpcoming();

        getYoutubeData();

        // youtube set

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize("AIzaSyAqP021C3PpCUgxj1AnTixCEsl6xRJW1QA",this);

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
                }, 11000);

    }

    private void getYoutubeData() {

        final List<YoutubeId> list=new ArrayList<>();

        DatabaseReference mD=FirebaseDatabase.getInstance().getReference("youtubeVideos");

        final YoutubeList repo=new YoutubeList();

        mD.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    YoutubeId data=postSnapshot.getValue(YoutubeId.class);
                    list.add(data);

                }
                repo.setYoutubeVideos(list);
                fireBaseData.setYouTubeData(repo);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("ErrorTAG", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }


    private void dateTimeStuff() {

//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date firstDate = new Date();
        Date secondDate = new Date(1550835000000L);

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
                    initialize(list);


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
            initialize(list);
        }catch (NullPointerException ignored){

        }
    }

    // todo Removed for slider

    private void showData2(List<PastFutureData> list) {

        PastAndFutureAdapter pastAndFutureAdapter=new PastAndFutureAdapter(list);
//        pastRecycler.setAdapter(pastAndFutureAdapter);
//        pastRecycler.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);


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

    // ToDo timer task

//    private class ScrollTask extends TimerTask {
//
//        PastAndFutureAdapter adapter;
//        RecyclerView recyclerView;
//        int position= Integer.MAX_VALUE/2;
//
//        public ScrollTask(PastAndFutureAdapter adapter, RecyclerView recyclerView) {
//            this.adapter = adapter;
//            this.recyclerView = recyclerView;
//        }
//
//        public void run() {
//            pastRecycler.post(new Runnable() {
//                public void run() {
//
//                    position++;
//                    recyclerView.smoothScrollToPosition(position);
//                }
//            });
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {

        List<YoutubeId> ylist;

        try {
            ylist = fireBaseData.getYouTubeData();
        }catch (NullPointerException ignored){
            ylist=new ArrayList<>();
            ylist.add(new YoutubeId("2dyD9_cJ9Q8"));
        }

        String str=ylist.get((new Random().nextInt(100))%ylist.size()).getCode();


        player.setPlaybackEventListener(this);
        player.setPlayerStateChangeListener(this);
        player.setShowFullscreenButton(false);

        if (!b) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            player.loadVideo(str);
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


    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {

        if (manager.getTopPosition() == adapter.getItemCount() - 5) {
            paginate();
        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }

    private void initialize(List<PastFutureData> plist) {
        manager = new CardStackLayoutManager(ApplicationContextProvider.getContext(), this);
        manager.setStackFrom(StackFrom.Top);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.1f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(true);
        adapter = new CardStackAdapter(ApplicationContextProvider.getContext(), plist);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);

        num-=1;
        if(num==0)
            pd.dismiss();
    }

    private void paginate() {
        List<PastFutureData> oldList = adapter.getSpots();
        List<PastFutureData> newList = new ArrayList<PastFutureData>() {{
            addAll(adapter.getSpots());
            addAll(createSpots());
        }};
        SpotDiffCallback callback = new SpotDiffCallback(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setSpots(newList);
        result.dispatchUpdatesTo(adapter);
    }

    private List< PastFutureData> createSpots() {

        List<PastFutureData> list=new ArrayList<>();

        try {
            list = fireBaseData.getGloriousPast();
        }catch (NullPointerException ignored){

        }

        return list;
    }

}
