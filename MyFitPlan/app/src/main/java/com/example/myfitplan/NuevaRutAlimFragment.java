package com.example.myfitplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfitplan.db.DataBaseHandler;

public class NuevaRutAlimFragment extends Fragment {

    DataBaseHandler db;

    ImageView guardarRutina;

    EditText nombreRutina;
    EditText descripcion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nueva_rut_alim, container, false);

        nombreRutina = view.findViewById(R.id.fnra_ed_nombre_rutina);
        descripcion = view.findViewById(R.id.fnra_ed_descripcion);
        guardarRutina = view.findViewById(R.id.fnra_guardar_rutina);

        db = new DataBaseHandler(this.getContext());
        db.getWritableDatabase();

        //Al presionar, si los EditText tienen texto, se crea una nueva rutina de alimentación y se vuelve al fragment anterior, si no se muestra un Toast
        guardarRutina.setOnClickListener(v -> {
            if (!nombreRutina.getText().toString().trim().isEmpty() && !descripcion.getText().toString().trim().isEmpty()){
                if (db.insertRutAlim(nombreRutina.getText().toString(), descripcion.getText().toString())) {
                    Toast.makeText(NuevaRutAlimFragment.this.getContext(), "Rutina guardada correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NuevaRutAlimFragment.this.getContext(), "Hubo un fallo al intenter guardar la rutina en la base de datos", Toast.LENGTH_SHORT).show();
                }
                Navigation.findNavController(view).navigate(R.id.action_nuevaRutAlimFragment_to_alimentacionFragment);
            }else{
                Toast.makeText(NuevaRutAlimFragment.this.getContext(), "Tienes que rellenar todos los campos para guardar la rutina", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}