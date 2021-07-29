package com.example.projetofinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MemoriaInterna extends AppCompatActivity implements Serializable {
    public static final String FRUTA = "fruta.txt";
    public static final String ANIMAL = "animal.txt";
    public static final String PAIS = "pais.txt";
    public static final String DISCIPLINA = "disciplina.txt";

    File nomeDaCategoria;
    String categoriaEscolhida;

    private HashMap<String,String> arquivos = new HashMap<String, String>();




    public MemoriaInterna(Context c, String categoria){
        populaHash();
        nomeDaCategoria = categoria(c,categoria);
        if (!verificaArquivos(c)){
            populador(c);
        }
    }

    private boolean verificaArquivos(Context c){
        boolean flag = false;
        File file;
        for (String arquivo: arquivos.values()) {
            file = c.getFileStreamPath(arquivo);
            if(file.exists()){
                flag= true;
            }else {
                return false;
            }
        }
        return flag;
    }
    private  File categoria(Context context, String categoria){
        switch (categoria){
            case "fruta":
                categoriaEscolhida = "fruta";
                return new File(context.getFilesDir(),FRUTA);
            case"animal":
                categoriaEscolhida = "animal";
                return new File(context.getFilesDir(),ANIMAL);
            case"pais":
                categoriaEscolhida ="pais";
                return new File(context.getFilesDir(),PAIS);
            case"disciplina":
                categoriaEscolhida = "disciplina";
                return new File(context.getFilesDir(),DISCIPLINA);
        }

        return new File(context.getFilesDir(),DISCIPLINA);
    }



    private void populaHash(){
        arquivos.put("fruta",FRUTA);
        arquivos.put("animal",ANIMAL);
        arquivos.put("pais",PAIS);
        arquivos.put("disciplina",DISCIPLINA);
    }

    public void populador(Context context) {
        HashMap<String,String> arrStr = new HashMap<String, String>();
        arrStr.put("fruta",
                "abacate\n" +
                "abacaxi\n" +
                "uva\n" +
                "acerola\n" +
                "cereja\n" +
                "banana\n" +
                "limao\n" +
                "maracuja \n" +
                "goiaba\n" +
                "laranja\n");
        arrStr.put("animal",
                "abelha\n" +
                "andorinha\n" +
                "macaco\n" +
                "baleia\n" +
                "cachorro\n" +
                "hipopotamo\n" +
                "ema\n" +
                "elefante\n" +
                "texugo\n" +
                "gato\n");
        arrStr.put("pais",
                "egito\n" +
                "alemanha\n" +
                "argentina\n" +
                "australia\n" +
                "holanda\n" +
                "brasil\n" +
                "china\n" +
                "canada\n" +
                "inglaterra\n" +
                "chile\n");
        arrStr.put("disciplina",
                "matematica\n" +
                "sociologia\n" +
                "ingles\n" +
                "quimica\n" +
                "espanhol\n" +
                "biologia\n" +
                "geografia\n" +
                "filosofia\n" +
                "ciencias\n" +
                "historia\n");
        for (String nomeDoBanco: arrStr.keySet()) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(categoria(context,nomeDoBanco), true));
                bw.write(arrStr.get(nomeDoBanco));
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> ler(){
        ArrayList<String> leitura = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader((new FileReader(nomeDaCategoria)));
            while (br.ready()){
                leitura.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return leitura;
    }

    public void escrever(String palavra){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDaCategoria, true));
            bw.write(palavra);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
