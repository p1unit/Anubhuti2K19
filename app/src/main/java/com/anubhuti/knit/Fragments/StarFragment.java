package com.anubhuti.knit.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anubhuti.knit.Activities.EventDiscription;
import com.anubhuti.knit.Adapter.CategoryAdapter;
import com.anubhuti.knit.Interfaces.StarInterface;
import com.anubhuti.knit.Listener.CategoryListner;
import com.anubhuti.knit.Migration.FireBaseData;
import com.anubhuti.knit.Model.EventCatogry;
import com.anubhuti.knit.Model.PastFutureData;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.EventCategoryResponse;
import com.anubhuti.knit.Response.EventDescResponse;
import com.anubhuti.knit.Response.PastFutureResponse;
import com.anubhuti.knit.Services.EventListService;
import com.anubhuti.knit.TinderSlider.CardStackAdapter;
import com.anubhuti.knit.TinderSlider.SpotDiffCallback;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarFragment extends Fragment implements CardStackListener, CategoryListner, StarInterface {

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private CardStackView cardStackView;
    private DatabaseReference mDatabase;
    private FireBaseData fireBaseData;
    private PastFutureResponse response;
    private EventCategoryResponse response2=new EventCategoryResponse();
    private DatabaseReference  mDatabase2;
    int num=2;
    private List<EventCatogry> list2=new ArrayList<>();
    private RecyclerView recyclerView;
    private EventListService service;
    private ProgressDialog pd;

    public StarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_start, container, false);

        cardStackView =(CardStackView)view.findViewById(R.id.card_stack_holder2) ;
        fireBaseData=new FireBaseData();
        response=new PastFutureResponse();

        recyclerView=(RecyclerView)view.findViewById(R.id.star_recycler);
        recyclerView.setHasFixedSize(true);
       recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabase2=FirebaseDatabase.getInstance().getReference("StarList");

        showPd();
        setStarData();

        getFirebaseData2();
        getStoredData2();

        return view;
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

    //---------------- Star List -------------------------------------------------\\

    private void getFirebaseData2(){

        mDatabase2.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    EventCatogry item=postSnapshot.getValue(EventCatogry.class);
                    //   Log.e("Team",teamDetail.getName());
                    list2.add(item);
                    //                 Log.d("team",)

                }
                response2.setEventCatogry(list2);
                fireBaseData.setStars(response2);
                showData2(list2);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("ErrorTAG", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    private void getStoredData2(){
        try {
            List<EventCatogry> list2_2 = fireBaseData.getStars();
            showData2(list2_2);
        }catch (NullPointerException ignored){

        }
    }

    private void showData2(List<EventCatogry> list2_2) {

        CategoryAdapter adapter = new CategoryAdapter(this,list2_2);
        recyclerView.setAdapter(adapter);
        num-=1;
        if(num<=0)
            pd.dismiss();
    }

    @Override
    public void callId(EventCatogry obj) {
        service=new EventListService(this);
        service.getStarDes(obj.getId());
        showPd();
    }

    @Override
    public void getDetail(EventDescResponse response) {

        Intent intent=new Intent(ApplicationContextProvider.getContext(), EventDiscription.class);
        intent.putExtra("eventData",response);
        intent.putExtra("isShow",false);
        startActivity(intent);
        pd.dismiss();
    }

    @Override
    public void errorResponse() {
        pd.dismiss();
    }

    //----------------  Star Data -------------------------------------------------\\

    private void setStarData() {

        mDatabase= FirebaseDatabase.getInstance().getReference("starData");
        getStoredData1();   // getting stored Data
        getFirebaseData1();
    }

    private void getFirebaseData1() {

        final List<PastFutureData> list=new ArrayList<>();

        mDatabase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    PastFutureData data=postSnapshot.getValue(PastFutureData.class);
                    list.add(data);

                }
                response.setData(list);
                fireBaseData.setStarList(response);
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

    private void getStoredData1() {
        try {
            List<PastFutureData> list = fireBaseData.getStarList();
            initialize(list);
        }catch (NullPointerException ignored){

        }
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
        if(num<=0)
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
            list = fireBaseData.getStarList();
        }catch (NullPointerException ignored){

        }

        return list;
    }



}
