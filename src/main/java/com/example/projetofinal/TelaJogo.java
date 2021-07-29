package com.example.projetofinal;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TelaJogo extends AppCompatActivity implements SensorEventListener {

    AppCompatImageView forquinhaAtual, forquinha1 ,forquinha2, forquinha3, forquinha4, forquinha5, forquinha6, forquinha7;;
    AppCompatButton btnJogar, btnChutar;
    AppCompatImageButton btnmenu;
    TextView tvCronometro, letraEscolhida;
    AppCompatEditText letra;
    LinearLayout campoLetras, letrasUsadas;
    ConstraintLayout menuShape, boxMenu;
    AppCompatImageButton btnRetornarMenu;
    String palavraCerta;
    String[] letrasForca;
    HashMap<Integer,TextView> tvletras = new HashMap<Integer, TextView>();
    Integer vida ;
    int acertos = 0;
    boolean flag;
    boolean pause ;
    boolean flagChute;
    Handler handler;
    Integer timer, contador;
    ArrayList<String> letrasEscolhidas  = new ArrayList<String>();
    ArrayList<String> listaBanco = new ArrayList<String>();
    Intent categoria;
    Vibrator vibrador;
    SensorManager sm;
    Sensor acelerometro , proximidade;
    Thread t = null;
    ConstraintLayout corvo;
    Integer contadorCorvo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_jogo);
        getSupportActionBar().hide();

        iniciaComponentes();
    }

// Inicia os componentes de tela, sensores, arquivo etc.
    private void iniciaComponentes(){

    //---------------EFEITOS INICIAIS-----------------------
        AudioPlay.stopAudio();
        AudioPlay.playAudio(this,R.raw.musica_in_game);

    //-------------------COMPONENTES DE TELA-----------------
        forquinhaAtual = findViewById(R.id.imagemForca1);
        forquinha1 = findViewById(R.id.imagemForca1);
        forquinha2 = findViewById(R.id.imagemForca2);
        forquinha3 = findViewById(R.id.imagemForca3);
        forquinha4 = findViewById(R.id.imagemForca4);
        forquinha5 = findViewById(R.id.imagemForca5);
        forquinha6 = findViewById(R.id.imagemForca6);
        forquinha7 = findViewById(R.id.imagemForca7);
        campoLetras = findViewById(R.id.campoLetras);
        corvo = findViewById(R.id.corvo);
        letrasUsadas = findViewById(R.id.campoLetrasEscolhidas);
        letra = findViewById(R.id.etLetra);
        tvCronometro = (TextView) findViewById(R.id.tvContador);
        btnJogar = findViewById(R.id.btnJogar);
        btnChutar = findViewById(R.id.btnChutar);
        btnmenu = findViewById(R.id.btnMenu);
        menuShape = findViewById(R.id.menuShape);
        boxMenu = findViewById(R.id.boxMenu);
        btnRetornarMenu = findViewById(R.id.btnRetornoMenu);
        letraEscolhida = findViewById(R.id.letrasEscolhidas);

    //----------------LÓGICA DE JOGO------------------------------
        vida = 6;
        flagChute = true;
        flag = true;
        pause = false;
        handler = new Handler();

    //-----------------INTENT------------------------------------------
        categoria = getIntent();

    //----------------------------SENSORES------------------------------
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximidade = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        vibrador = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if(proximidade != null){
            sm.registerListener(this, proximidade, sm.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(this,"sensor de proximidade indisponivel", Toast.LENGTH_SHORT).show();
        }
        if(acelerometro != null){
            sm.registerListener(this,acelerometro, sm.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this,"acelerometro indisponivel", Toast.LENGTH_SHORT).show();
        }

    //-----------------------------CONEXÃO COM ARQUIVO------------------------------
        conectaBanco();
    }

//--------------------------------------PRÉ-JOGO---------------------------------------------------
    private void conectaBanco(){
        if(categoria.hasExtra("banco")){
            MemoriaInterna bd;
            bd = (MemoriaInterna) categoria.getSerializableExtra("banco");
            listaBanco.addAll(bd.ler());
            setPalavraCerta();
            preparaLetras();
            play();
        }
    }

    private void setPalavraCerta(){
        Random r = new Random();
        palavraCerta = listaBanco.get(r.nextInt(listaBanco.size()));
        letrasForca = palavraCerta.split("");
    }

    private void preparaLetras(){
        for (int i = 1; i < letrasForca.length; i++) {
            TextView t = new TextView(getApplicationContext());
            LinearLayout l = new LinearLayout(getApplicationContext());
            l.setBackground(getDrawable(R.drawable.shape_letras));
            l.addView(t);
            t.setTextSize(30);
            t.setAllCaps(true);
            t.setText(letrasForca[i]);
            tvletras.put(i,t);
            campoLetras.addView(l);
            t.setVisibility(View.INVISIBLE);
            campoLetras.setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }

    public void play() {
        AudioPlay.playEffect(this,R.raw.cuteffect);
        btnJogar.setEnabled(true);
        btnChutar.setEnabled(true);
        contador = (10*(letrasForca.length-1));
        tvCronometro.setText(contador.toString());
        timer = contador;
        contadorCorvo = contador/2;
        cronometroStart();
    }

//-----------------------------------EM-JOGO--------------------------------------------------------
    public void palpite(View v){
    letrasUsadas();
    if (verificaLetra()){
        if (verificaPalavra(acertos)){
            viveu();
        }
    }else{
        if(vida==1){
            cronometroStop();
            AudioPlay.playEffect(getApplicationContext(), R.raw.risadamalefica);
            AudioPlay.playEffect(getApplicationContext(),R.raw.cuteffect);
            vida = 0;
            threadErrou();
        }else{
            AudioPlay.playEffect(getApplicationContext(), R.raw.risadamalefica);
            AudioPlay.playEffect(getApplicationContext(),R.raw.cuteffect);
            threadErrou();
            vida --;
        }
    }
    letra.setText("");
}

    public void chute(View view) {
        cronometroStop();
        if(flagChute){
            letra.setFilters(new InputFilter[]{new InputFilter.LengthFilter(palavraCerta.length())});
            btnJogar.setEnabled(false);
            flagChute = false;
            Toast.makeText(this, "Agora voce pode chutar!", Toast.LENGTH_LONG).show();
        }else{
            if(letra.getText().toString().equals(palavraCerta)){
                viveu();
            }else{
                vida=0;
                threadErrou();
            }
        }
        letra.setText("");
    }

    private void letrasUsadas(){
        Toast t = Toast.makeText(this,"já escolheu essa letra, tente outra",Toast.LENGTH_SHORT);
        if(letrasEscolhidas.contains(letra.getText().toString())){
            t.show();
        }else{
            letrasEscolhidas.add(letra.getText().toString());
            letraEscolhida.setText(letraEscolhida.getText().toString()+"  "+letra.getText().toString());
        }
    }

    private boolean verificaLetra(){
        boolean verificador = false;
        for (TextView i : tvletras.values()) {
            if (i.getText().toString().equals(letra.getText().toString())){
                i.setVisibility(View.VISIBLE);
                this.acertos ++;
                verificador = true;
            }
        }
        return verificador;
    }

    private boolean verificaPalavra(int acertos){
        if(palavraCerta.length() == acertos){
            return  true;
        }
        return false;
    }

    private void viveu(){
        AudioPlay.stopAudio();
        cronometroStop();
        Intent it = new Intent(this, TelaViveu.class);
        it.putExtras(categoria);
        Intent nick = getIntent();
        String avatarNome;
        if(nick.hasExtra("avatarNome")){
            avatarNome = nick.getStringExtra("avatarNome");
            it.putExtra("avatarNome",avatarNome);
        }
        finish();
        startActivity(it);
    }

    private void morreu(){
        Intent nick = getIntent();
        Intent it = new Intent(this, TelaMorreu.class);
        it.putExtra("palavra",palavraCerta);
        String avatarNome;
        if(nick.hasExtra("avatarNome")){
            avatarNome = nick.getStringExtra("avatarNome");
            it.putExtra("avatarNome",avatarNome);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrador.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.EFFECT_HEAVY_CLICK));
        }else {
            vibrador.vibrate(1000);
        }
        AudioPlay.playEffect(getApplicationContext(), R.raw.risadacategoria);
        AudioPlay.playEffect(getApplicationContext(),R.raw.willhealmscream);
        finish();
        startActivity(it);
    }

    private void oCorvo(Integer contador) {
        if(contadorCorvo.equals(contador)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrador.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.EFFECT_HEAVY_CLICK));
            }else {
                vibrador.vibrate(1000);
            }
            if(AudioPlay.isplayingAudio){
                AudioPlay.stopAudio();
            }
            AudioPlay.playAudio(getApplicationContext(), R.raw.bandit_radio);
            menuShape.setVisibility(View.VISIBLE);
            corvo.setVisibility(View.VISIBLE);
            if(AudioPlay.isplayingEffect){
                AudioPlay.stopEffect();
            }
            AudioPlay.playEffect(getApplicationContext(), R.raw.susto);
        }
    }


    //------------------------------------------THREADS---------------------------------------
    private void threadErrou(){
        new Thread(){
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (vida){
                            case 0:
                                btnChutar.setEnabled(false);
                                btnJogar.setEnabled(false);
                                forquinha7.setVisibility(View.VISIBLE);
                                forquinha6.setVisibility(View.INVISIBLE);
                                try {
                                    Thread.sleep(2000);
                                    morreu();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            case 1:
                                forquinhaAtual = findViewById(R.id.imagemForca6);
                                forquinha6.setVisibility(View.VISIBLE);
                                forquinha5.setVisibility(View.INVISIBLE);
                            case 2:
                                forquinhaAtual = findViewById(R.id.imagemForca5);
                                forquinha5.setVisibility(View.VISIBLE);
                                forquinha4.setVisibility(View.INVISIBLE);
                            case 3:
                                forquinhaAtual = findViewById(R.id.imagemForca4);
                                forquinha4.setVisibility(View.VISIBLE);
                                forquinha3.setVisibility(View.INVISIBLE);
                            case 4:
                                forquinhaAtual = findViewById(R.id.imagemForca3);
                                forquinha3.setVisibility(View.VISIBLE);
                                forquinha2.setVisibility(View.INVISIBLE);
                            case 5:
                                forquinha2.setVisibility(View.VISIBLE);
                                forquinhaAtual = findViewById(R.id.imagemForca2);
                                forquinha1.setVisibility(View.INVISIBLE);
                            case 6:
                                forquinhaAtual = findViewById(R.id.imagemForca1);
                                forquinha1.setVisibility(View.VISIBLE);
                        }
                    }
                },1000);
            }
        }.start();
    }
    
    public void cronometroPause(){
        pause = true;
    }

    public void cronometroReturn(){
        pause = false;
    }

    public void cronometroStart(){
        if (t!=null){
            t.interrupt();
            t=null;
        }
        t= cronometroRun();
        t.start();
    }

    public void cronometroStop(){
        flag = false;
        if(t!=null){
            t.interrupt();
            t = null;
        }
    }

    public Thread cronometroRun(){
        return new Thread(){
            @Override
            public void run() {
                if (!flag) {
                    currentThread().interrupt();
                } else {
                    while (flag) {
                        if (contador <= 0) {
                            cronometroPause();
                            cronometroStop();
                            vida = 0;
                            threadErrou();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                oCorvo(contador);
                                tvCronometro.setText(contador.toString());
                                if (!pause) {
                                    contador--;
                                }
                            }
                        }, 1000);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    //------------------------------------------SENSORES----------------------------------------------
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            if(event.values[0] == 0.0 ){
                menuShape.setVisibility(View.INVISIBLE);
                corvo.setVisibility(View.INVISIBLE);
            }
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if(event.values[0] > 10) {
                menu();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(this, proximidade);
        sm.unregisterListener(this, acelerometro);
    }


    //------------------------------------------------MENU-------------------------------------
    public void menu(View view){
       menu();
    }
    public void menu(){
        cronometroPause();
        boxMenu.setVisibility(View.VISIBLE);
        menuShape.setVisibility(View.VISIBLE);
        btnRetornarMenu.setVisibility(View.VISIBLE);
    }

    public void retornaMenu(View v){
        boxMenu.setVisibility(View.INVISIBLE);
        menuShape.setVisibility(View.INVISIBLE);
        btnRetornarMenu.setVisibility(View.INVISIBLE);
        cronometroReturn();
    }

    public void sair(View view) {
        this.finishAffinity();
        this.finish();
        System.exit(0);
    }

    public void novoJogo(View v){
//        AudioPlay.stopEffect();
//        AudioPlay.stopAudio();
//        cronometroStop();
//        Map<Thread, StackTraceElement[]> y = Thread.getAllStackTraces();
//        y.forEach((thread, stackTraceElements) -> {
//            if(thread.isAlive()){
//                thread.interrupt();
//            }
//        });
//        boxMenu.setVisibility(View.INVISIBLE);
//        menuShape.setVisibility(View.INVISIBLE);
//        btnRetornarMenu.setVisibility(View.INVISIBLE);
        finish();
        AudioPlay.stopAudio();
        AudioPlay.playAudio(this,R.raw.main_music);
        onBackPressed();
    }

    public void reiniciar(View v){
        AudioPlay.stopAudio();
        AudioPlay.stopEffect();
//        forquinhaAtual.setVisibility(View.INVISIBLE);
//        forquinha1.setVisibility(View.VISIBLE);
        retornaMenu(v);
//        btnJogar.setEnabled(true);
//        acertos=0;
//        contador = timer;
//        letraEscolhida.setText("");
//        letrasEscolhidas.clear();

//        for (TextView i : tvletras.values()) {
//            i.setVisibility(View.INVISIBLE);
//        }
        recreate();
//        AudioPlay.playAudio(getApplicationContext(),R.raw.musica_in_game);
//        AudioPlay.playEffect(getApplicationContext(),R.raw.cuteffect);
    }



}
