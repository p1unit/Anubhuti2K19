package com.anubhuti.knit.Adapter;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhuti.knit.Model.SponserDetail;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class SponserApapter extends  RecyclerView.Adapter<SponserApapter.MyViewHolder> {

    List<SponserDetail> list;

    public SponserApapter(List<SponserDetail> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sponser_details,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.desc.setText(list.get(i).getDesc());
        holder.name.setText(list.get(i).getName());
        holder.setImage(list.get(i).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView sponser_image;
        TextView name,desc;

        MyViewHolder(View itemView) {
            super(itemView);

            sponser_image=itemView.findViewById(R.id.sponser_id);
            name=itemView.findViewById(R.id.name);
            desc=itemView.findViewById(R.id.desc);
            desc.setPaintFlags(desc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClicked(getAdapterPosition());
                }
            });
        }

        private void cardClicked(int adapterPosition) {

            // ToDo handel Click
            String url =list.get(adapterPosition).getWebLink();
        }

        public void setImage(String url) {

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.temp);
            requestOptions.error(R.drawable.temp);

            Glide.with(ApplicationContextProvider.getContext()).load(url)
                    .apply(requestOptions).thumbnail(0.5f).into(sponser_image);
        }

    }
}
