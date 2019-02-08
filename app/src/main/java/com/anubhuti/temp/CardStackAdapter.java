package com.anubhuti.temp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.anubhuti.knit.Model.PastFutureData;
import com.anubhuti.knit.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<PastFutureData> spots;

    public CardStackAdapter(Context context, List<PastFutureData> spots) {
        this.inflater = LayoutInflater.from(context);
        this.spots = spots;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.temp_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PastFutureData spot = spots.get(position);

        Glide.with(holder.image)
                .load(spot.getImageUrl())
                .into(holder.image);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), spot.name, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return spots.size();
    }

    public List<PastFutureData> getSpots() {
        return spots;
    }

    public void setSpots(List<PastFutureData> spots) {
        this.spots = spots;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ViewHolder(View view) {
            super(view);
            this.image = view.findViewById(R.id.item_image);
        }
    }

}