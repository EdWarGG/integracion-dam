package com.example.myfitplan;

import android.app.AlertDialog;
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

public class EditarEjercicioFragment extends Fragment {

    DataBaseHandler db;

    String nombreRutina;

    TextView nombre;
    EditText series;
    EditText repeticiones;
    EditText peso;
    ImageView guardar;
    ImageView borrar;

    AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_ejercicio, container, false);

        nombre = view.findViewById(R.id.fee_tv_info);
        series = view.findViewById(R.id.fee_ed_series);
        repeticiones = view.findViewById(R.id.fee_ed_reprticiones);
        peso = view.findViewById(R.id.fee_ed_peso);
        guardar = view.findViewById(R.id.fee_guardar_ejercicio);
        borrar = view.findViewById(R.id.fee_borrar);
        builder = new AlertDialog.Builder(this.getContext());

        //Recoge el argumento enviado desde el fragment anterior
        assert getArguments() != null;
        String nombreDelEjercicio = EditarEjercicioFragmentArgs.fromBundle(getArguments()).getNombreEjer();
        nombre.setText(nombreDelEjercicio);

        db = new DataBaseHandler(this.getContext());
        SQLiteDatabase bd = db.getWritableDatabase();

        //Selecciona los datos del ejercicio y los pone en los EdiTText correspondientes
        Cursor cursor = bd.rawQuery("SELECT series, repeticiones, peso FROM ejercicios WHERE nombre='" + nombreDelEjercicio + "'", null);
        if (cursor.moveToFirst()){
            series.setText(cursor.getString(0));
            repeticiones.setText(cursor.getString(1));
            peso.setText(cursor.getString(2));
        }else {
            Toast.makeText(EditarEjercicioFragment.this.getContext(), "No se ha podido cargar el contenido del ejercicio", Toast.LENGTH_SHORT).show();
        }
        cursor.close();

        /*Seleccioona el nombre de la rutina de este ejercicio
        Esto se hace con el objetivo de luego al volver a la rutina se le pase el nombre
        Se debe hacer así ya que RutEjerFragment requiere de un argumento con su nombre*/
        Cursor cursor2 = bd.rawQuery("SELECT rutina FROM ejercicios WHERE nombre='" + nombreDelEjercicio + "'", null);
        if (cursor2.moveToFirst()){
            nombreRutina = cursor2.getString(0);
        }else{
            Toast.makeText(EditarEjercicioFragment.this.getContext(), "No se ha podido cargar la rutina", Toast.LENGTH_SHORT).show();
        }
        cursor2.close();

        //Al presionar se actualiza el ejercicio
        guardar.setOnClickListener(v -> {
            if (db.updateEjercicio(nombreDelEjercicio, Integer.parseInt(series.getText().toString()), Integer.parseInt(repeticiones.getText().toString()), Integer.parseInt(peso.getText().toString()))){
                Toast.makeText(EditarEjercicioFragment.this.getContext(), "Ejercicio modificado correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(com.example.myfitplan.EditarEjercicioFragmentDirections.actionEditarEjercicioFragmentToRutEjerFragment(nombreRutina));
            }else{
                Toast.makeText(EditarEjercicioFragment.this.getContext(), "Hubo un fallo al intenter modificar el ejercicio en la base de datos", Toast.LENGTH_SHORT).show();
            }
        });

        //Al presionar se abre una alerta con la opción de borrar el ejercicio
        borrar.setOnClickListener(v -> builder.setTitle("¿Seguro?").setMessage("¿Deseas borrar este ejercicio?").setCancelable(true)
                .setPositiveButton("Sí", (dialog, which) -> {
                    bd.execSQL("DELETE FROM ejercicios WHERE nombre='" + nombreDelEjercicio + "'");
                    Toast.makeText(EditarEjercicioFragment.this.getContext(), "Ejercicio borrado de la base de datos", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(EditarEjercicioFragmentDirections.actionEditarEjercicioFragmentToRutEjerFragment(nombreRutina));
                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show());
        return view;
    }
}