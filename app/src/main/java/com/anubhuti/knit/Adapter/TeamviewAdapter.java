package com.anubhuti.knit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhuti.knit.Model.TeamDetail;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class TeamviewAdapter extends RecyclerView.Adapter<TeamviewAdapter.MyViewHolder> {


    List<TeamDetail>list=new ArrayList<>();

    public TeamviewAdapter(List<TeamDetail> list) {
        this.list = list;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, post;
        ImageView profile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.team_name_textview);
            post = (TextView)itemView.findViewById(R.id.team_post_textview);
            profile=(ImageView)itemView.findViewById(R.id.team_profile_image);
        }

        public void setImage(String url){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_user);
            requestOptions.error(R.drawable.ic_user);


            Glide.with(ApplicationContextProvider.getContext()).load(url)
                    .apply(requestOptions).thumbnail(0.5f).into(profile);
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_item, parent, false);


        return new MyViewHolder(itemView);
    }


    public void onBindViewHolder(MyViewHolder holder, int position) {
        TeamDetail teamDetail = list.get(position);
        //Log.d("name",teamDetail.getName());
        holder.name.setText(teamDetail.getName());
        holder.post.setText(teamDetail.getPost());
        holder.setImage(teamDetail.getImageUrl());

    }


    public int getItemCount() {
        return list.size();
    }

}
