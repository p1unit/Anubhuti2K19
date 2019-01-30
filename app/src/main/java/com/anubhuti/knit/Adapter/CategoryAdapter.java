package com.anubhuti.knit.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anubhuti.knit.Listener.CategoryListner;
import com.anubhuti.knit.Model.EventCatogry;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    CategoryListner listner;
    List<EventCatogry> list;

    public CategoryAdapter(CategoryListner listner,List<EventCatogry> list)
    {
        this.listner=listner;
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

        ImageView bgImg;
        TextView event_name;

        MyViewHolder(View itemView) {
            super(itemView);

            bgImg=itemView.findViewById(R.id.background_image);
            event_name=itemView.findViewById(R.id.event_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClicked(getAdapterPosition());
                }
            });
        }

        private void cardClicked(int adapterPosition) {

            listner.callId(list.get(adapterPosition).getId(),list.get(adapterPosition).getName());
        }

        public void setBackGround(String imageUrl) {

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.temp);
            requestOptions.error(R.drawable.temp);

            Glide.with(ApplicationContextProvider.getContext()).load(imageUrl)
                    .apply(requestOptions).thumbnail(1.0f).into(bgImg);
        }
    }
}
