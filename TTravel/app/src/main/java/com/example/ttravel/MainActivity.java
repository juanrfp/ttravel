package com.example.ttravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mediaPlayer;

        // Obtén referencias a los elementos de la vista
        ImageView imageView = findViewById(R.id.imageViewLogoCarga);
        ProgressBar progressBar = findViewById(R.id.progressBarCarga);

        // Define la animación para la imagen
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacion_de_carga);
        imageView.startAnimation(anim);

        // Cargar y reproducir el sonido de viento
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_carga);
        float volume = 1.0f;
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.start();

        // Simula algún proceso de carga
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Simula la finalización de la carga después de 3 segundos

                mediaPlayer.stop();
                mediaPlayer.release();

                progressBar.setVisibility(View.GONE);

                // Continúa a la siguiente actividad
                Intent intent = new Intent(MainActivity.this, ActividadPantallaPrincipal.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 milisegundos = 3 segundos

    }
}