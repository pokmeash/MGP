package com.nofaultofmine.mgp_p4;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Map;

// Created by RyanLau

public class SoundManager {
    public final static SoundManager Instance = new SoundManager();

    private SurfaceView view = null;
    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();

    public void Init(SurfaceView _view)
    {
        view = _view;
        Release();
    }

    public void playSound(int _id, float _volume)
    {
        // if audio is loaded
        GetAudio(_id);
        if (audioMap.containsKey(_id))
        {
            // have the clip
            MediaPlayer curr = audioMap.get(_id);
            curr.seekTo(0);
            curr.setVolume(_volume,_volume);
            curr.start();
        }
        else
        {
            MediaPlayer curr =MediaPlayer.create(view.getContext(),_id);
            audioMap.put(_id,curr);
            curr.setVolume(_volume,_volume);
            curr.start();
        }
    }

    public void StopAudio(int _id)
    {
        MediaPlayer curr = audioMap.get(_id);
        curr.stop();
    }

    public void Release()
    {
        for (Map.Entry<Integer,MediaPlayer> entry : audioMap.entrySet())
        {
            entry.getValue().stop();
            entry.getValue().reset();
            entry.getValue().release();
        }

        // empty it
        audioMap.clear();
    }

    private MediaPlayer GetAudio(int _id)
    {
        // check if audio is loaded or not
        if (audioMap.containsKey(_id))
        {
            return  audioMap.get(_id);
        }

        MediaPlayer result = MediaPlayer.create(view.getContext(),_id);
        audioMap.put(_id, result);
        return result;
    }
}
