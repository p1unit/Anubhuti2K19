package com.anubhuti.knit.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhuti.knit.Model.PastFutureData;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PastAndFutureAdapter extends RecyclerView.Adapter<PastAndFutureAdapter.Holder>{

    List<PastFutureData> list;

    public PastAndFutureAdapter(List<PastFutureData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_list_layout,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

//        holder.name.setText(list.get(i%list.size()).getName());
        holder.setImg(list.get(i%list.size()).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class Holder extends RecyclerView.ViewHolder{

        ImageView img;
//        TextView name;

        public Holder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.bc_image);
//            name=itemView.findViewById(R.id.some_name);
        }

        public void setImg(String url){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.temp);
            requestOptions.error(R.drawable.temp);


            Glide.with(ApplicationContextProvider.getContext()).load(url)
                    .apply(requestOptions).thumbnail(0.5f).into(img);
        }
    }
}
