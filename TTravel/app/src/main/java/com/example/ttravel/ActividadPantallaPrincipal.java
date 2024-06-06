package com.example.ttravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class ActividadPantallaPrincipal extends AppCompatActivity {

    private static final String TAG = "ActividadPantallaPrincipal";
    private double latitud, longitud;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    //Objetos de configuracion
    private boolean estaFondoAnimadoActivado=true;
    private boolean estaModoAccesibleActivado=true;

    //Objetos de configuracion de voz
    private TextToSpeech textToSpeech;
    private Handler handler = new Handler();


    // Shared preferences
    SharedPreferences sharedPreferences;

    GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_pantalla_principal);

        // Inicializar TextToSpeech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });


        //Acceder a las preferencias
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        estaFondoAnimadoActivado = sharedPreferences.getBoolean("switch_preference_fondo", false);
        estaModoAccesibleActivado = sharedPreferences.getBoolean("switch_preference_lector", false);

        // Inicializar objetos de elementos
        Button buttonConfiguracion = findViewById(R.id.buttonConfiguracion);
        Button buttonSalir = findViewById(R.id.buttonSalirAplicacion);
        Button buttonUbicacionActual = findViewById(R.id.buttonUbicacionActual);
        Button buttonConsultarUbicacion=findViewById(R.id.buttonConsultarUbicacion);
        gifImageView = findViewById(R.id.gifImageView);

        //Cambio de fondo inicial
        cambiarFondo();


        //Configuracion de accesibilidad para los botones
        configurarAccesibilidadBoton(buttonConfiguracion, "Open settings");
        configurarAccesibilidadBoton(buttonConsultarUbicacion,"Gives info on a selected place");
        configurarAccesibilidadBoton(buttonSalir, "Exit the application");
        configurarAccesibilidadBoton(buttonUbicacionActual, "Show current location");

        // Listeners de botones
        buttonUbicacionActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActividadPantallaPrincipal.this, ActividadPantallaUbicacion.class);
                startActivity(intent);
            }
        });

        buttonConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActividadPantallaPrincipal.this, ActividadConfiguracion.class);
                startActivity(intent);
            }
        });

        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "¡Hasta pronto!☀☀👋👋", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Acciones para el acceso a la ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latitud = location.getLatitude();
                        longitud = location.getLongitude();
                        Log.d(TAG, "Latitud: " + latitud + ", Longitud: " + longitud);
                        Toast.makeText(getApplicationContext(), "Latitud: " + latitud + ", Longitud: " + longitud, Toast.LENGTH_SHORT).show();
                        // Detener las actualizaciones de ubicación después de obtener una ubicación
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };

        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permiso ya concedido
            setCoordenadas();
        } else {
            // Solicitar permiso
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void setCoordenadas() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(5000); // Intervalo de actualización de 5 segundos
            locationRequest.setFastestInterval(2000); // Intervalo más rápido posible de 2 segundos
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            Log.e(TAG, "Permisos de ubicación no concedidos.");
            Toast.makeText(this, "Permisos de ubicación no concedidos.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permisos concedidos
                setCoordenadas();
            } else {
                // Permisos denegados
                Log.e(TAG, "Permisos de ubicación denegados.");
                Toast.makeText(this, "Permisos de ubicación denegados.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cambiarFondo(){
        if (estaFondoAnimadoActivado){
            gifImageView.setImageResource(R.drawable.giffondoprincipal);
        }else{
            gifImageView.setImageResource(R.drawable.ic_launcher_background);
        }
    }


    //Metodos para la accesibilidad de los botones
    private void configurarAccesibilidadBoton(final Button button, final String texto) {
        if (estaModoAccesibleActivado) {
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null);
                        }
                    }, 1000); // 1 segundos
                    return true;
                }
            });
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, android.view.MotionEvent event) {
                    if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                        handler.removeCallbacksAndMessages(null);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        // Apagar TextToSpeech para liberar recursos
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

}
