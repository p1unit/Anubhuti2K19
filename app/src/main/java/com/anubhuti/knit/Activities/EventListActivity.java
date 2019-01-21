package com.anubhuti.knit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anubhuti.knit.Adapter.EventListAdapter;
import com.anubhuti.knit.Model.EventTypeModel;
import com.anubhuti.knit.R;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        recyclerView=this.findViewById(R.id.event_list_recycler);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        EventTypeModel obj=new EventTypeModel();
        obj.setName("puneet");
        obj.setImage1("https://firebasestorage.googleapis.com/v0/b/anubhuti-39dbc.appspot.com/o/490710121800363.jpeg?alt=media&token=88f69ad4-30a8-485f-8cb1-23cdbc36652b");
        obj.setImage2("https://firebasestorage.googleapis.com/v0/b/anubhuti-39dbc.appspot.com/o/490710121800363.jpeg?alt=media&token=88f69ad4-30a8-485f-8cb1-23cdbc36652b");
        obj.setImage3("https://firebasestorage.googleapis.com/v0/b/anubhuti-39dbc.appspot.com/o/490710121800363.jpeg?alt=media&token=88f69ad4-30a8-485f-8cb1-23cdbc36652b");


        EventTypeModel obj2=new EventTypeModel();
        obj2.setName("puneet");
        obj2.setImage1("https://firebasestorage.googleapis.com/v0/b/anubhuti-39dbc.appspot.com/o/490710121800363.jpeg?alt=media&token=88f69ad4-30a8-485f-8cb1-23cdbc36652b");
        obj2.setImage2("https://firebasestorage.googleapis.com/v0/b/anubhuti-39dbc.appspot.com/o/490710121800363.jpeg?alt=media&token=88f69ad4-30a8-485f-8cb1-23cdbc36652b");
        obj2.setImage3("https://firebasestorage.googleapis.com/v0/b/anubhuti-39dbc.appspot.com/o/490710121800363.jpeg?alt=media&token=88f69ad4-30a8-485f-8cb1-23cdbc36652b");

        List<EventTypeModel> lis=new ArrayList<>();
        lis.add(obj);
        lis.add(obj2);

        EventListAdapter eventListAdapter=new EventListAdapter(lis);

        recyclerView.setAdapter(eventListAdapter);
    }
}
