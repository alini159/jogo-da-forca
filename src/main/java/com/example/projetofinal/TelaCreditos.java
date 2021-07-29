package com.example.projetofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TelaCreditos extends AppCompatActivity {

    private TextView tx;
    private Animation animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_creditos);
        getSupportActionBar().hide();


        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.credits);
        tx = findViewById(R.id.tx);
        tx.setText("GAME\n"+"OF\n"+"GALLOWS\n"+"\n"+"CREDITOS: \n"+"\n"+"Consultores: \n"
                +"Professor Rafael dos Passos Canteri\n"+"\n"+"Roteirista: \n"+"Alini Rodrigues Ferreira\n"
                +"\n"+"Designer: \n" +"Alini Rodrigues Ferreira\n"+"\n"+"Developer: \n"
                +"Herivelto Junior de Jesus Monteiro\n" +"\n"+"Conceitual Art:\n"+" Alini Rodrigues Ferreira \n"+
                "\n"+"Supoervisor de Som: \n"+"Herivelto Junior de Jesus Monteiro\n"
                +"\n"+"Efeitos Visuais: \n"+ "Herivelto Junior de Jesus Monteiro\n"+"\n"+"Musicas: \n"
                +"Halloween Theme 8bit\n"+"Naruto Sadness and Sorrow 8bit\n"+"Ievan Polkka 8bit\n"
                +"S.T.A.L.K.E.R Clear Sky - Bandit Radio 8bit\n"+"We Are The Champions - Queen 8bit\n"
                +"\n"+"Effects:\n"+"Wilhelm Scream\n"+"Loud Scream Sound - effect 50k\n"+"Som de uma risada maléfica - Sound effect Free\n"
                +"Agent Smith Laughing - Green Screen\n"+"Sword Sound Effect"+"\n"+"\n"
                +"Agradecimentos Especiais:\n"+"UFMS\n"
                +"Universidade Federal \ndo\n Mato Grosso do sul\n"
                +"CPAN "+"\n"+"Campus Pantanal\n"+"\n"+"\n"+"OBRIGADO A TODOS!!!\n"+"\n"+"PARABÉNS!!!\n");

        tx.startAnimation(animation);
    }
}
