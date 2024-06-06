package com.example.ttravel;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ttravel.databinding.ActivityActividadPantallaUbicacionBinding;
import com.example.ttravel.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActividadPantallaUbicacion extends AppCompatActivity {


    ActivityActividadPantallaUbicacionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actividad_pantalla_ubicacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding=ActivityActividadPantallaUbicacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FragmentoTiempo());

        binding.bottomNavigationMenu.setOnItemSelectedListener(item->{
            int itemId = item.getItemId();
            if (itemId == R.id.opcion_weather) {
                replaceFragment(new FragmentoTiempo());
            } else if (itemId == R.id.opcion_locations) {
                replaceFragment(new FragmentoLugares());
            }
            return true;
        });







    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutConsulta, fragment);
        fragmentTransaction.commit();
    }
}