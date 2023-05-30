package com.example.myfitplan;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Da un segundo para mostrar el Splash Screen
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setTheme(R.style.Theme_MyFitPlan);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        //Determina en qué fragments se mostrará el BottomNavigationView y en cuáles no
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.ejercicioFragment|| navDestination.getId() == R.id.alimentacionFragment|| navDestination.getId() == R.id.ajustesFragment){
                bottomNavigationView.setVisibility(View.VISIBLE);
            }else{
                bottomNavigationView.setVisibility(View.GONE);
            }
        });


        }

    }
