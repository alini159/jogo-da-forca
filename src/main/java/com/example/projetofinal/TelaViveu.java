package com.example.projetofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TelaViveu extends AppCompatActivity {

    Intent categoria;
    String nomeDaCategoria;
    AppCompatEditText deposito;
    MemoriaInterna bd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_viveu);
        getSupportActionBar().hide();
        iniciaComponentes();
    }

    public void iniciaComponentes() {
        AudioPlay.playAudio(this,R.raw.wearechampions);
        categoria = getIntent();
        deposito = findViewById(R.id.etNovaPalavra);
        if (categoria.hasExtra("banco")) {
            bd = (MemoriaInterna) categoria.getSerializableExtra("banco");
        }
        nomeDaCategoria = bd.categoriaEscolhida;
    }


    public void depositor(View view) {

        if (bd.ler().contains(deposito.getText().toString())) {
            Toast t = Toast.makeText(this, "Esse palavra já existe, escreva outra", Toast.LENGTH_LONG);
            t.show();
        } else {
            if(deposito.getText().toString().equalsIgnoreCase("Depositar") || deposito.getText().toString().equalsIgnoreCase("") ){
                Toast.makeText(this, "precisa ser uma palavra",Toast.LENGTH_LONG).show();
            }else{
                bd.escrever(deposito.getText().toString().toLowerCase());
                Toast.makeText(this, "Depositado com Sucesso!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void limpaCampoDeposito(View view) {
        Toast t = Toast.makeText(this, "Tem que ser " + nomeDaCategoria, Toast.LENGTH_LONG);
        t.show();
        deposito.setText("");
    }

    public void novoJogo(View view) {
        AudioPlay.stopAudio();
        AudioPlay.playAudio(this,R.raw.main_music);
        finish();
        onBackPressed();
    }

    public void creditos(View view){
        startActivity(new Intent(this, TelaCreditos.class));
    }
}


