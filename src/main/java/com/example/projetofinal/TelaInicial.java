package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;

import java.io.Serializable;

public class TelaInicial extends AppCompatActivity implements Serializable {

    MemoriaInterna banco ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);
        AudioPlay.playAudio(this,R.raw.main_music);
        getSupportActionBar().hide();
    }

    public void iniciar (View v){
        Intent it = new Intent(this, TelaPrincipal.class);
        it.putExtra("banco", banco);
        startActivity(it);
    }

}