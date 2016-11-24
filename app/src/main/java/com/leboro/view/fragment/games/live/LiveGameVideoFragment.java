package com.leboro.view.fragment.games.live;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.leboro.R;

public class LiveGameVideoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.live_game_video_fragment, container, false);

        //        String url = "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4"; // your URL here
        //        String url = "rtmp://cloud2live.it2.com/meytel-user-4-live01/_definst_&file=toro&provider=rtmp"; // your URL here
        String url = "http://cloud2live.it2.com:1935/meytel-user-4-live01/_definst_/mp4:toro/playlist.m3u8"; // your URL here

        VideoView videoView = (VideoView) mView.findViewById(R.id.liveGameVideoView);

        MediaController mc = new MediaController(getActivity());
        mc.setAnchorView(videoView);
        mc.setMediaPlayer(videoView);
        Uri video = Uri.parse(url);
        videoView.setMediaController(mc);
        videoView.setVideoURI(video);

        videoView.start();

        return mView;
    }

}
