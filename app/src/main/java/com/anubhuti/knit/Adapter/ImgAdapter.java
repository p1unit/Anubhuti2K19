package com.anubhuti.knit.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.MyViewHolder> {

    List<String> list;


    public ImgAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_row,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.temp);
        requestOptions.error(R.drawable.temp);

        Glide.with(ApplicationContextProvider.getContext()).load(list.get(i%list.size()))
                .apply(requestOptions).thumbnail(0.5f).into(holder.img);
    }


    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.lis_img);
        }
    }
}
