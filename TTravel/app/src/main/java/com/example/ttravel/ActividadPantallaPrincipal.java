package com.example.ttravel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class ActividadPantallaPrincipal extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_pantalla_principal);

        Button buttonConfiguracion = findViewById(R.id.buttonConfiguracion);




        GifImageView gifImageView = findViewById(R.id.gifImageView);
        gifImageView.setImageResource(R.drawable.giffondoprincipal);

        // Comprobar si se han concedido los permisos de ubicación
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permisos de ubicación concedidos
            Toast.makeText(this,"Los permisos de ubicacion han sido otorgados.", Toast.LENGTH_SHORT).show();
        } else {
            // Permisos de ubicación no concedidos
            Toast.makeText(this,"La aplicacion no tiene permisos de ubicacion.", Toast.LENGTH_SHORT).show();
        }*/


        buttonConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActividadPantallaPrincipal.this, ActividadConfiguracion.class);
                startActivity(intent);
            }
        });

    }
}