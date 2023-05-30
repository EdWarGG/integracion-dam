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

public class EditarComidaFragment extends Fragment {

    DataBaseHandler db;

    String nombreRutina;

    TextView nombre;
    EditText ingrediente_principal;
    EditText peso;
    ImageView guardar;
    ImageView borrar;

    AlertDialog.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_comida, container, false);

        nombre = view.findViewById(R.id.fec_tv_info);
        ingrediente_principal = view.findViewById(R.id.fec_ed_ingrediente);
        peso = view.findViewById(R.id.fec_ed_peso);
        guardar = view.findViewById(R.id.fec_guardar_comida);
        borrar = view.findViewById(R.id.fec_borrar);
        builder = new AlertDialog.Builder(this.getContext());

        //Recoge el argumento enviado desde el fragment anterior
        assert getArguments() != null;
        String nombreDeLaComida = EditarComidaFragmentArgs.fromBundle(getArguments()).getNombreCom();
        nombre.setText(nombreDeLaComida);

        db = new DataBaseHandler(this.getContext());
        SQLiteDatabase bd = db.getWritableDatabase();

        //Selecciona los datos de la comida y los pone en los EdiTText correspondientes
        Cursor cursor = bd.rawQuery("SELECT ingrediente_principal, peso FROM comidas WHERE nombre='" + nombreDeLaComida + "'", null);
        if (cursor.moveToFirst()){
            ingrediente_principal.setText(cursor.getString(0));
            peso.setText(cursor.getString(1));
        }else {
            Toast.makeText(EditarComidaFragment.this.getContext(), "No se ha podido cargar el contenido de la comida", Toast.LENGTH_SHORT).show();
        }
        cursor.close();

        /*Seleccioona el nombre de la rutina de esta comida
        Esto se hace con el objetivo de luego al volver a la rutina se le pase el nombre
        Se debe hacer así ya que RutAlimFragment requiere de un argumento con su nombre*/
        Cursor cursor2 = bd.rawQuery("SELECT rutina FROM comidas WHERE nombre='" + nombreDeLaComida + "'", null);
        if (cursor2.moveToFirst()){
            nombreRutina = cursor2.getString(0);
        }else{
            Toast.makeText(EditarComidaFragment.this.getContext(), "No se ha podido cargar la rutina", Toast.LENGTH_SHORT).show();
        }
        cursor2.close();

        //Al presionar se actualiza la comida
        guardar.setOnClickListener(v -> {
            if (db.updateComida(nombreDeLaComida, ingrediente_principal.getText().toString(), Integer.parseInt(peso.getText().toString()))){
                Toast.makeText(EditarComidaFragment.this.getContext(), "Comida modificada correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(com.example.myfitplan.EditarComidaFragmentDirections.actionEditarComidaFragmentToRutAlimFragment(nombreRutina));
            }else{
                Toast.makeText(EditarComidaFragment.this.getContext(), "Hubo un fallo al intenter modificar la comida en la base de datos", Toast.LENGTH_SHORT).show();
            }
        });

        //Al presionar se abre una alerta con la opción de borrar la comida
        borrar.setOnClickListener(v -> builder.setTitle("¿Seguro?").setMessage("¿Deseas borrar esta comida?").setCancelable(true)
                .setPositiveButton("Sí", (dialog, which) -> {
                    bd.execSQL("DELETE FROM comidas WHERE nombre='" + nombreDeLaComida + "'");
                    Toast.makeText(EditarComidaFragment.this.getContext(), "Comida borrada de la base de datos", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(EditarComidaFragmentDirections.actionEditarComidaFragmentToRutAlimFragment(nombreRutina));
                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show());


        return view;
    }
}