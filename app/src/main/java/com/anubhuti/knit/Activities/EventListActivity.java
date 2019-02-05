package com.anubhuti.knit.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhuti.knit.Adapter.EventListAdapter;
import com.anubhuti.knit.Interfaces.Detail_RegisterInterface;
import com.anubhuti.knit.Model.EventTypeModel;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.EventDescResponse;
import com.anubhuti.knit.Response.EventTypeResponse;
import com.anubhuti.knit.Services.EventListService;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity implements Detail_RegisterInterface {

    RecyclerView recyclerView;
    TextView toolbarText;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        recyclerView=this.findViewById(R.id.event_list_recycler);
        toolbarText=this.findViewById(R.id.toolbar_text);
        backbtn=this.findViewById(R.id.backbtn);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent=getIntent();
        EventTypeResponse obj =(EventTypeResponse)intent.getSerializableExtra("listEvent");

        toolbarText.setText(intent.getStringExtra("name"));
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        List<EventTypeModel> lis=obj.getAllData();
        EventListAdapter eventListAdapter=new EventListAdapter(this,lis);
        recyclerView.setAdapter(eventListAdapter);
    }

    @Override
    public void registerEvent() {

    }

    @Override
    public void detailEvent(String id) {

        EventListService eventListService=new EventListService(this);
        eventListService.getEventDesd(id);

    }

    @Override
    public void getEvent(EventDescResponse response) {

        Intent intent=new Intent(this,EventDiscription.class);
        intent.putExtra("eventData",response);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }
}
