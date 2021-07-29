package com.example.projetofinal;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

public class AudioPlay {

    public static MediaPlayer musica;
    private static SoundPool soundPool;
    public static boolean isplayingAudio=false;
    public static void playAudio(Context c,int id){
        musica = MediaPlayer.create(c,id);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
        if(!musica.isPlaying())
        {

            isplayingAudio=true;
            musica.setLooping(true);
            musica.start();
        }
    }
    public static void stopAudio(){
        isplayingAudio=false;
        musica.setLooping(false);
        musica.stop();
    }
    public static MediaPlayer efeito;
    private static SoundPool effectPool;
    public static boolean isplayingEffect=false;
    public static void playEffect(Context c,int id){
        efeito = MediaPlayer.create(c,id);
        effectPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
        if(!efeito.isPlaying())
        {
            isplayingEffect=true;
            efeito.start();
        }
    }

    public static void stopEffect(){
        isplayingEffect=false;
        efeito.stop();
    }


}