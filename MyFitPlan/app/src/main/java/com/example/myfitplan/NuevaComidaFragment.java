package com.example.myfitplan;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfitplan.db.DataBaseHandler;

public class NuevaComidaFragment extends Fragment {

    DataBaseHandler db;

    EditText nombre;
    EditText ingrediente_principal;
    EditText peso;

    Button guardarVolver;
    Button guardarNuevo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nueva_comida, container, false);

        nombre = view.findViewById(R.id.fnc_ed_nombre);
        ingrediente_principal = view.findViewById(R.id.fnc_ed_ingrediente_principal);
        peso = view.findViewById(R.id.fnc_ed_peso);
        guardarVolver = view.findViewById(R.id.fnc_button_guardarvolver);
        guardarNuevo = view.findViewById(R.id.fnc_button_guardarnuevo);

        //Recoge el argumento enviado desde el fragment anterior
        assert getArguments() != null;
        String nombreDeLaRutina = NuevaComidaFragmentArgs.fromBundle(getArguments()).getNomRutinaAlim();

        db = new DataBaseHandler(this.getContext());
        db.getWritableDatabase();

        //Al presionar, si los EditText tienen texto, se crea una nueva comida y se vuelve al fragment anterior, si no se muestra un Toast
        guardarVolver.setOnClickListener(v -> {
            if (!nombre.getText().toString().trim().isEmpty() && !ingrediente_principal.getText().toString().trim().isEmpty() && !peso.getText().toString().trim().isEmpty()){
                if (db.insertComida(nombre.getText().toString(), ingrediente_principal.getText().toString(), Integer.parseInt(peso.getText().toString()), nombreDeLaRutina)) {
                    Toast.makeText(NuevaComidaFragment.this.getContext(), "Comida guardada correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NuevaComidaFragment.this.getContext(), "Hubo un fallo al intenter guardar la comida en la base de datos", Toast.LENGTH_SHORT).show();
                }
                Navigation.findNavController(view).navigate(NuevaComidaFragmentDirections.actionNuevaComidaFragmentToRutAlimFragment(nombreDeLaRutina));
            }else{
                Toast.makeText(NuevaComidaFragment.this.getContext(), "Tienes que rellenar todos los campos para guardar la comida", Toast.LENGTH_SHORT).show();
            }
        });

        //Al presionar, si los EditText tienen texto, se crea una nueva comida y se borran los EditText para insertar otra comida, si no se muestra un Toast
        guardarNuevo.setOnClickListener(v -> {
            if (!nombre.getText().toString().trim().isEmpty() && !ingrediente_principal.getText().toString().trim().isEmpty() && !peso.getText().toString().trim().isEmpty()){
                if (db.insertComida(nombre.getText().toString(), ingrediente_principal.getText().toString(), Integer.parseInt(peso.getText().toString()), nombreDeLaRutina)) {
                    Toast.makeText(NuevaComidaFragment.this.getContext(), "Comida guardada correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                    nombre.getText().clear();
                    ingrediente_principal.getText().clear();
                    peso.getText().clear();
                } else {
                    Toast.makeText(NuevaComidaFragment.this.getContext(), "Hubo un fallo al intenter guardar la comida en la base de datos", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(NuevaComidaFragment.this.getContext(), "Tienes que rellenar todos los campos para guardar la comida", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}