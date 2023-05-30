package com.example.myfitplan;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitplan.db.entidades.Comida;
import com.example.myfitplan.db.DataBaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RutAlimFragment extends Fragment {

    DataBaseHandler db;
    NAAdapter adapter;

    TextView nombreRutina;
    TextView descripcion;
    TextView info;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ImageView opciones;

    AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rut_alim, container, false);

        nombreRutina = view.findViewById(R.id.fra_tv_nombre);
        descripcion = view.findViewById(R.id.fra_tv_descripcion);
        info = view.findViewById(R.id.fra_tv_info);
        recyclerView = view.findViewById(R.id.fra_recyclerview);
        fab = view.findViewById(R.id.fra_fab);
        opciones = view.findViewById(R.id.fra_options);
        builder = new AlertDialog.Builder(this.getContext());

        //Recoge el argumento enviado desde el fragment anterior
        assert getArguments() != null;
        String nombreDeLaRutina = RutAlimFragmentArgs.fromBundle(getArguments()).getNombreRutAlim();

        db = new DataBaseHandler(this.getContext());
        SQLiteDatabase bd = db.getWritableDatabase();

        //Selecciona la descripción de la rutina y si la encuentra, la pone en el TextView "descripción", si no le asigna otro texto
        Cursor cursor = bd.rawQuery("SELECT descripcion FROM rutalim WHERE nombre='" + nombreDeLaRutina + "'", null);
        if (cursor.moveToFirst()){
            descripcion.setText(cursor.getString(0));
        }else{
            descripcion.setText("No se pudo encontrar la descripción de esta rutina");
        }
        cursor.close();

        //Lista de comidas
        List<Comida> itemList = db.getComidas(nombreDeLaRutina);
        //Dependiendo del tamaño de la lista se determinará lo que pondrá el TextView "info"
        if (itemList.size() == 0){
            info.setText("Esta rutina no tiene comidas.");
        } else if (itemList.size() == 1) {
            info.setText("Esta rutina tiene " + itemList.size() + " comida.");
        }else {
            info.setText("Esta rutina tiene " + itemList.size() + " comidas.");
        }

        //Adapter para asignar las comidas al RecyclerView
        adapter = new NAAdapter(itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Al presionar se va a "NuevaComidaFragment"
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(com.example.myfitplan.RutAlimFragmentDirections.actionRutAlimFragmentToNuevaComidaFragment(nombreDeLaRutina)));

        //Al presionar sale un PopupMenu con tres opciones
        opciones.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this.getContext(), opciones);
            popup.getMenuInflater().inflate(R.menu.ra_popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.rapm_editar_rutima: //Al presionar se va a "EditRutAlimFragment"
                        Navigation.findNavController(view).navigate(RutAlimFragmentDirections.actionRutAlimFragmentToEditRutAlimFragment(nombreDeLaRutina));
                        return true;
                    case R.id.rapm_crear_evento: //Al presionar se va a la aplicación Google Calendar, donde se podrá crear un evento con la rutina
                        Intent intent = new Intent(Intent.ACTION_INSERT);
                        intent.setData(CalendarContract.Events.CONTENT_URI);
                        intent.putExtra(CalendarContract.Events.TITLE, nombreDeLaRutina);
                        intent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion.getText().toString());
                        startActivity(intent);
                        return true;
                    case R.id.rapm_borrar_rutina: //Al presionar saldrá una alerta con la opción de borrar la rutina
                        builder.setTitle("¿Seguro?").setMessage("¿Desea borrar esta rutina?").setCancelable(true)
                                .setPositiveButton("Sí", (dialog, which) -> {
                                    bd.execSQL("DELETE FROM comidas WHERE rutina='" + nombreDeLaRutina + "'");
                                    bd.execSQL("DELETE FROM rutalim WHERE nombre='" + nombreDeLaRutina + "'");
                                    Toast.makeText(RutAlimFragment.this.getContext(), "Rutina borrada de la base de datos", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.action_rutAlimFragment_to_alimentacionFragment);
                                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show();
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });

        nombreRutina.setText(nombreDeLaRutina);

        return view;
    }
}