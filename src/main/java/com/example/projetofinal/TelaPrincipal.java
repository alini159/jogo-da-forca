package com.example.projetofinal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import java.io.Serializable;

public class TelaPrincipal extends AppCompatActivity {
    AppCompatButton btnUser1, btnUser2, btnUser3;
    AppCompatEditText nickName;
    String avatarSelecionado = "zumbi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout .tela_principal);
        getSupportActionBar().hide();
        iniciaComponentes();
    }

    private void iniciaComponentes(){
        btnUser1 = findViewById(R.id.btnUser1);
        btnUser2 = findViewById(R.id.btnUser2);
        btnUser3 = findViewById(R.id.btnUser3);
        nickName = findViewById(R.id.etNickName);
    }

    public void user1(View v){
        btnUser1.setBackground(getDrawable(R.drawable.botao_user1));
        btnUser2.setBackground(getDrawable(R.drawable.user2mini));
        btnUser3.setBackground(getDrawable(R.drawable.user3mini));
        avatarSelecionado = "User1mini";
    }

    public void user2(View v){
        btnUser1.setBackground(getDrawable(R.drawable.user1mini));
        btnUser2.setBackground(getDrawable(R.drawable.botao_user2));
        btnUser3.setBackground(getDrawable(R.drawable.user3mini));
        avatarSelecionado = "User2mini";
    }

    public void user3(View v){
        btnUser1.setBackground(getDrawable(R.drawable.user1mini));
        btnUser2.setBackground(getDrawable(R.drawable.user2mini));
        btnUser3.setBackground(getDrawable(R.drawable.botao_user3));
        avatarSelecionado = "User3mini";
    }

    public void proximo(View v){
        Intent it = new Intent(this, TelaCategoria.class);
        it.putExtra("avatar",avatarSelecionado);
        it.putExtra("nome", nickName.getText().toString());
        if (nickName.getText().toString().equals("digite o seu Nome") || nickName.getText().toString().equals("")) {
            nickName.setText("zumbi");
            Toast.makeText(this,"insira seu nome", Toast.LENGTH_LONG).show();
        }else{

            startActivity(it);
        }

    }

    public void limpaCampoNickName(View view) {
        nickName.setText("");
    }
}
