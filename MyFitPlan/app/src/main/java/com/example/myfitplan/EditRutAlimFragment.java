package com.example.myfitplan;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitplan.db.DataBaseHandler;

public class EditRutAlimFragment extends Fragment {

    DataBaseHandler db;

    TextView nombreRutina;
    EditText descripcion;
    ImageView aceptarEdicion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_rut_alim, container, false);

        nombreRutina = view.findViewById(R.id.fera_tv_nombre);
        descripcion = view.findViewById(R.id.fera_ed_descripcion);
        aceptarEdicion = view.findViewById(R.id.fera_guardar_rutina);

        //Recoge el argumento enviado desde el fragment anterior
        assert getArguments() != null;
        String nombreDeLaRutina = EditRutAlimFragmentArgs.fromBundle(getArguments()).getNomRutAlim();
        nombreRutina.setText(nombreDeLaRutina);

        db = new DataBaseHandler(this.getContext());
        SQLiteDatabase bd = db.getWritableDatabase();

        //Selecciona la descripción de la rutina y la asigna al EditText "descripcion"
        Cursor cursor = bd.rawQuery("SELECT descripcion FROM rutalim WHERE nombre='" + nombreDeLaRutina + "'", null);
        if (cursor.moveToFirst()){
            descripcion.setText(cursor.getString(0));
        }else {
            Toast.makeText(EditRutAlimFragment.this.getContext(), "No se ha podido cargar el contenido de la descripción", Toast.LENGTH_SHORT).show();
        }
        cursor.close();

        //Al presionar se actualiza la rutina
        aceptarEdicion.setOnClickListener(v -> {
            if (db.updateRutAlim(nombreDeLaRutina, descripcion.getText().toString())){
                Toast.makeText(EditRutAlimFragment.this.getContext(), "Rutina modificada correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(EditRutAlimFragmentDirections.actionEditRutAlimFragmentToRutAlimFragment(nombreDeLaRutina));
            }else{
                Toast.makeText(EditRutAlimFragment.this.getContext(), "Hubo un fallo al intenter modificar la rutina en la base de datos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}