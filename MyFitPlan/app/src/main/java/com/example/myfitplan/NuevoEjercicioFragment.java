package com.example.myfitplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfitplan.db.DataBaseHandler;

public class NuevoEjercicioFragment extends Fragment {

    DataBaseHandler db;

    EditText nombre;
    EditText series;
    EditText repeticiones;
    EditText peso;
    Button guardarVolver;
    Button guardarNuevo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuevo_ejercicio, container, false);

        nombre = view.findViewById(R.id.fne_ed_nombre);
        series = view.findViewById(R.id.fne_ed_series);
        repeticiones = view.findViewById(R.id.fne_ed_reprticiones);
        peso = view.findViewById(R.id.fne_ed_peso);
        guardarVolver = view.findViewById(R.id.fne_button_guardarvolver);
        guardarNuevo = view.findViewById(R.id.fne_button_guardarnuevo);

        //Recoge el argumento enviado desde el fragment anterior
        assert getArguments() != null;
        String nombreDeLaRutina = NuevoEjercicioFragmentArgs.fromBundle(getArguments()).getNomRutinaEjer();

        db = new DataBaseHandler(this.getContext());
        db.getWritableDatabase();

        //Al presionar, si los EditText tienen texto, se crea un nuevo ejercicio y se vuelve al fragment anterior, si no se muestra un Toast
        guardarVolver.setOnClickListener(v -> {
            if (!nombre.getText().toString().trim().isEmpty() && !series.getText().toString().trim().isEmpty() && !repeticiones.getText().toString().trim().isEmpty() && !peso.getText().toString().trim().isEmpty()){
                if (db.insertEjercicio(nombre.getText().toString(), Integer.parseInt(series.getText().toString()), Integer.parseInt(repeticiones.getText().toString()), Integer.parseInt(peso.getText().toString()), nombreDeLaRutina)) {
                    Toast.makeText(NuevoEjercicioFragment.this.getContext(), "Ejercicio guardado correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NuevoEjercicioFragment.this.getContext(), "Hubo un fallo al intenter guardar el ejercicio en la base de datos", Toast.LENGTH_SHORT).show();
                }
                Navigation.findNavController(view).navigate(NuevoEjercicioFragmentDirections.actionNuevoEjercicioFragmentToRutEjerFragment(nombreDeLaRutina));
            }else{
                Toast.makeText(NuevoEjercicioFragment.this.getContext(), "Tienes que rellenar todos los campos para guardar el elercicio", Toast.LENGTH_SHORT).show();
            }
        });

        //Al presionar, si los EditText tienen texto, se crea un nuevo ejercicio y se borran los EditText para insertar otro ejercicio, si no se muestra un Toast
        guardarNuevo.setOnClickListener(v -> {
            if (!nombre.getText().toString().trim().isEmpty() && !series.getText().toString().trim().isEmpty() && !repeticiones.getText().toString().trim().isEmpty() && !peso.getText().toString().trim().isEmpty()){
                if (db.insertEjercicio(nombre.getText().toString(), Integer.parseInt(series.getText().toString()), Integer.parseInt(repeticiones.getText().toString()), Integer.parseInt(peso.getText().toString()), nombreDeLaRutina)) {
                    Toast.makeText(NuevoEjercicioFragment.this.getContext(), "Ejercicio guardado correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                    nombre.getText().clear();
                    series.getText().clear();
                    repeticiones.getText().clear();
                    peso.getText().clear();
                } else {
                    Toast.makeText(NuevoEjercicioFragment.this.getContext(), "Hubo un fallo al intenter guardar el ejercicio en la base de datos", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(NuevoEjercicioFragment.this.getContext(), "Tienes que rellenar todos los campos para guardar el elercicio", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}