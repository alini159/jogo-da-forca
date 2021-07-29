package com.example.projetofinal;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

public class TelaMorreu extends AppCompatActivity {

    AppCompatEditText etPalavra, etNick;
    Intent it, nick;

    TelaJogo telaJogo = new TelaJogo();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_morreu);
        getSupportActionBar().hide();
        iniciaComponentes();
    }

    private void iniciaComponentes(){
        etNick= findViewById(R.id.etNick);
        etPalavra = findViewById(R.id.et3);
        it = getIntent();
        nick = getIntent();
        if(nick.hasExtra("avatarNome")){
            etNick.getText().append(nick.getStringExtra("avatarNome").toUpperCase());
        }
        if(it.hasExtra("palavra")){
            etPalavra.setText(it.getStringExtra("palavra").toUpperCase());
        }
        AudioPlay.stopAudio();
        AudioPlay.playEffect(this, R.raw.sefodeu);
        AudioPlay.playAudio(this,R.raw.sadnaruto);
        Toast.makeText(this, "SE FODEU", Toast.LENGTH_LONG).show();

    }

    public void novoJogo(View view) {
        AudioPlay.stopAudio();
        AudioPlay.playAudio(this,R.raw.main_music);
        finish();
        onBackPressed();
    }
}
