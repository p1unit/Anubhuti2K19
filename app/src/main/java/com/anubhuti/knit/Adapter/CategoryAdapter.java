package com.anubhuti.knit.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anubhuti.knit.Model.EventCatogry;
import com.anubhuti.knit.R;

import java.util.List;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {


    List<EventCatogry> list;

    public CategoryAdapter(List<EventCatogry> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.evenet_category_row,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.event_name.setText(list.get(i).getName());
        holder.setBackGround(list.get(i).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView event_name;

        MyViewHolder(View itemView) {
            super(itemView);

            relativeLayout=itemView.findViewById(R.id.event_background);
            event_name=itemView.findViewById(R.id.event_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClicked(getAdapterPosition());
                }
            });
        }

        private void cardClicked(int adapterPosition) {

            // ToDo handel click
        }

        public void setBackGround(String imageUrl) {

            // Todo setBAckground
        }
    }
}
