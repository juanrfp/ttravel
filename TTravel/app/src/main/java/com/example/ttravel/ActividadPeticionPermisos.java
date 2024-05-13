package com.example.ttravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class ActividadPeticionPermisos extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_peticion_permisos);

        // Verificar si los permisos de ubicación ya han sido concedidos anteriormente
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Los permisos de ubicación ya han sido concedidos
            // Puedes iniciar la siguiente actividad aquí
            iniciarSiguienteActividad();
        } else {
            // Solicitar permisos de ubicación
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Debes explicar al usuario por qué necesita los permisos de ubicación
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Mostrar un mensaje o diálogo explicativo aquí
                    Toast.makeText(this, "La aplicación necesita permisos de ubicación para algunas funcionalidades.", Toast.LENGTH_LONG).show();
                }

                // Solicitar permisos de ubicación
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Los permisos de ubicación han sido concedidos por el usuario
                // Puedes iniciar la siguiente actividad aquí
            } else {
                // El usuario ha negado los permisos de ubicación
                // Puedes manejar este caso aquí, por ejemplo, mostrar un mensaje al usuario
                Toast.makeText(this, "Algunas funcionalidades estarán reducidas sin permisos de ubicación.", Toast.LENGTH_SHORT).show();
            }
            iniciarSiguienteActividad();
        }
    }

    private void iniciarSiguienteActividad() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Opcional: Finalizar la actividad actual si ya no se necesita
    }
}
