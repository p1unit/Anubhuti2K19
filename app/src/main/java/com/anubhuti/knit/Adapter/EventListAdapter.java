package com.anubhuti.knit.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anubhuti.knit.Interfaces.Detail_RegisterInterface;
import com.anubhuti.knit.Model.EventTypeModel;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;

import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {

    List<EventTypeModel> list;
    Detail_RegisterInterface anInterface;

    public EventListAdapter(Detail_RegisterInterface anInterface,List<EventTypeModel> list) {
        this.list = list;
        this.anInterface=anInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_list_row,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.name.setText(list.get(i).getName());
        ArrayList<String> str=new ArrayList<>();
        str.add(list.get(i).getImage1());
        str.add(list.get(i).getImage2());
        str.add(list.get(i).getImage3());
        holder.imgRecyler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ApplicationContextProvider.getContext(),LinearLayoutManager.HORIZONTAL,false);
        holder.imgRecyler.setLayoutManager(layoutManager);
        holder.imgRecyler.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);
        ImgAdapter adapter=new ImgAdapter(str);
        holder.imgRecyler.setAdapter(adapter);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        RecyclerView imgRecyler;;
        CardView details;
        CardView register;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.event_name_lis);
            imgRecyler=itemView.findViewById(R.id.container_recycler);
            details =itemView.findViewById(R.id.details_card);
            register=itemView.findViewById(R.id.register_card);

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDetail(getAdapterPosition());
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getRegister();
                }
            });
        }

        private void getRegister() {

        }

        private void getDetail(int adapterPosition) {

            anInterface.detailEvent(list.get(adapterPosition).getDescId());
        }
    }
}
