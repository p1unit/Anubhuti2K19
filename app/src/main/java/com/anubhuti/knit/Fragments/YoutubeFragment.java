package com.anubhuti.knit.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.anubhuti.knit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeFragment extends YouTubePlayerSupportFragment {
    View view;
    private static final String YoutubeApIKey = "AIzaSyAqP021C3PpCUgxj1AnTixCEsl6xRJW1QA";
    private YouTubePlayer YPlayer;
    private YouTubePlayerSupportFragment youTubePlayerFragment;


    public YoutubeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_youtube, container, false);
       // YouTubePlayerSupportFragment youTubePlayerFragment = getFragmentManager().findFragmentById(R.id.youtube_view);

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_view, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(YoutubeApIKey, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(true);
                    YPlayer.loadVideo("2zNSgSzhBfM");
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {


            }
        });


        return view;
    }

}
