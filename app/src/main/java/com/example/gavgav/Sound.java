package com.example.gavgav;

import android.media.SoundPool;

public class Sound extends SoundPool {
    public Sound(int maxStreams, int streamType, int srcQuality) {
        super(maxStreams, streamType, srcQuality);
    }
    @Override
    public int load(String path, int priority) {
        return super.load(path, priority);
    }



}
