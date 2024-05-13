package com.example.ttravel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class ActividadConfiguracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_configuracion);

        // Instanciar el fragmento
        ActividadPreferencias fragmentoPreferencias = new ActividadPreferencias();

        // Obtener el FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Iniciar una transacción de fragmentos
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplazar el contenido del contenedor con el fragmento
        transaction.replace(R.id.contenedorFragmentoDePreferencias, fragmentoPreferencias);

        // Confirmar la transacción
        transaction.commit();

    }
}