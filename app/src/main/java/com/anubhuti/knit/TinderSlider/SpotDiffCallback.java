package com.anubhuti.knit.TinderSlider;

import android.support.v7.util.DiffUtil;

import com.anubhuti.knit.Model.PastFutureData;

import java.util.List;

public class SpotDiffCallback extends DiffUtil.Callback {

    private final List<PastFutureData> oldList;
    private final List<PastFutureData> newList;

    public SpotDiffCallback(List<PastFutureData> oldList, List<PastFutureData> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPosition, int newPosition) {
        return oldList.get(oldPosition).getImageUrl().equals(newList.get(newPosition).getImageUrl());
    }

    @Override
    public boolean areContentsTheSame(int oldPosition, int newPosition) {
        PastFutureData oldSpot = oldList.get(oldPosition);
        PastFutureData newSpot = newList.get(newPosition);
        return oldSpot.getImageUrl().equals(newSpot.getImageUrl());
    }

}
