package com.example.projetofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;


public class TelaCategoria extends AppCompatActivity {

    Button btnFruta, btnAnimal, btnPais, btnFilme;
    AppCompatEditText avatarNome;

    AppCompatButton avatarLogo;

    String avatar;
    Intent avatarSelecionado;

    MemoriaInterna banco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.tela_categoria);


       iniciarComponentes();
    }

    private void iniciarComponentes(){
        AudioPlay.playEffect(this, R.raw.risadacategoria);
        btnAnimal = findViewById(R.id.btnAnimal);
        btnFilme = findViewById(R.id.btnDisciplina);
        btnPais = findViewById(R.id.btnTeste);
        btnFruta = findViewById(R.id.btnFruta);
        avatarLogo = findViewById(R.id.avatar);
        avatarNome = findViewById(R.id.etNomeAvatar);

        avatarSelecionado = getIntent();
        avatar = avatarSelecionado.getStringExtra("avatar");
        avatarNome.setText(avatarSelecionado.getStringExtra("nome").toUpperCase());

        if(avatar.equals("User1mini")){
            avatarLogo.setBackground(getDrawable(R.drawable.user1));
        }else if(avatar.equals("User2mini")){
            avatarLogo.setBackground(getDrawable(R.drawable.user2));
        }else if(avatar.equals("User3mini")){
            avatarLogo.setBackground(getDrawable(R.drawable.user3));
        }else if(avatar.equals("zumbi")){
            avatarLogo.setBackground(getDrawable(R.drawable.zumbi));
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioPlay.stopAudio();
    }

    public void clickAnimal (View v){
        banco = new MemoriaInterna(this.getApplicationContext(),"animal");
        Intent telaJogo = new Intent(this, TelaJogo.class);
        telaJogo.putExtra("banco",banco);
        telaJogo.putExtra("avatarNome", avatarNome.getText().toString());
        startActivity(telaJogo);
    }

    public void clickPais (View v){
        banco = new MemoriaInterna(this.getApplicationContext(),"pais");
        Intent telaJogo = new Intent(this, TelaJogo.class);
        telaJogo.putExtra("banco",banco);
        telaJogo.putExtra("avatarNome", avatarNome.getText().toString());
        startActivity(telaJogo);
    }

    public void clickDisciplina (View v){
        banco = new MemoriaInterna(this.getApplicationContext(),"disciplina");
        Intent telaJogo = new Intent(this, TelaJogo.class);
        telaJogo.putExtra("banco",banco);
        telaJogo.putExtra("avatarNome",  avatarNome.getText().toString());
        startActivity(telaJogo);
    }

    public void clickFruta (View v){
        banco = new MemoriaInterna(this.getApplicationContext(),"fruta");
        Intent telaJogo = new Intent(this, TelaJogo.class);
        telaJogo.putExtra("banco",banco);
        telaJogo.putExtra("avatarNome",  avatarNome.getText().toString());
        telaJogo.putExtras(this.getIntent());
        startActivity(telaJogo);
    }
}
