package com.example.myfitplan;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitplan.db.DataBaseHandler;


public class AjustesFragment extends Fragment {

    ImageView compartir;
    TextView irPerfil;
    TextView borrarPerfil;
    TextView borrarRutejer;
    TextView borrarRutalim;
    TextView borrarRutinas;
    TextView borrarBaseDatos;
    TextView acercaDe;

    DataBaseHandler db;

    AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        compartir = view.findViewById(R.id.faj_compartir);
        irPerfil = view.findViewById(R.id.faj_tv_ir_perfil);
        borrarPerfil = view.findViewById(R.id.faj_tv_borrar_perfil);
        borrarRutejer = view.findViewById(R.id.faj_tv_borrar_rutejer);
        borrarRutalim = view.findViewById(R.id.faj_tv_borrar_rutalim);
        borrarRutinas = view.findViewById(R.id.faj_tv_borrar_rutinas);
        borrarBaseDatos = view.findViewById(R.id.faj_tv_borrar_bd);
        acercaDe = view.findViewById(R.id.faj_tv_acerca_de);
        builder = new AlertDialog.Builder(this.getContext());

        db = new DataBaseHandler(this.getContext());
        SQLiteDatabase bd = db.getWritableDatabase();

        //Al presionar se abrirá la pestaña de compartir con otras aplicaciones
        //En este caso se compartirá un texto con un enlace al repositorio de Github del proyecto
        compartir.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "¡Hola! Prueba esta app de creación de rutinas fitness, ¡se llama MyFitPlan!\nhttps://github.com/EdWarGG/integracion-dam/releases");
            intent.setType("text/plain");
            startActivity(intent);
        });

        //Al presionar se irá al perfil de usuario
        irPerfil.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_ajustesFragment_to_perfilFragment));

        //AL presionar se abre una alerta con la opción de borrar el perfil de usuario
        borrarPerfil.setOnClickListener(v -> builder.setTitle("¿Seguro?").setMessage("¿Deseas borrar tu perfil de usuario?").setCancelable(true)
                .setPositiveButton("Sí", (dialog, which) -> {
                    bd.execSQL("DELETE FROM usuario");
                    Toast.makeText(AjustesFragment.this.getContext(), "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show());

        //Al presionar se abre una alerta con la opción de borrar todas las rutinas de entrenamiento
        borrarRutejer.setOnClickListener(v -> builder.setTitle("¿Seguro?").setMessage("¿Deseas borrar todas las rutinas de entrenamiento?").setCancelable(true)
                .setPositiveButton("Sí", (dialog, which) -> {
                    bd.execSQL("DELETE FROM rutejer");
                    bd.execSQL("DELETE FROM ejercicios");
                    Toast.makeText(AjustesFragment.this.getContext(), "Rutinas de ejercicio eliminadas correctamente", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show());

        //Al presionar se abre una alerta con la opción de borrar todas las rutinas de alimentación
        borrarRutalim.setOnClickListener(v -> builder.setTitle("¿Seguro?").setMessage("¿Deseas borrar todas las rutinas de alimentación?").setCancelable(true)
                .setPositiveButton("Sí", (dialog, which) -> {
                    bd.execSQL("DELETE FROM rutalim");
                    bd.execSQL("DELETE FROM comidas");
                    Toast.makeText(AjustesFragment.this.getContext(), "Rutinas de alimentación eliminadas correctamente", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show());

        //Al presionar se abre una alerta con la opción de borrar todas las rutinas de entrenamiento y alimentación
        borrarRutinas.setOnClickListener(v -> builder.setTitle("¿Seguro?").setMessage("¿Deseas borrar todas las rutinas de ambas categorías?").setCancelable(true)
                .setPositiveButton("Sí", (dialog, which) -> {
                    bd.execSQL("DELETE FROM rutejer");
                    bd.execSQL("DELETE FROM ejercicios");
                    bd.execSQL("DELETE FROM rutalim");
                    bd.execSQL("DELETE FROM comidas");
                    Toast.makeText(AjustesFragment.this.getContext(), "Rutinas de alimentación y de ejercicio eliminadas correctamente", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show());

        //Al presionar se abre una alerta con la opción de borrar toda la base de datos de la aplicación
        borrarBaseDatos.setOnClickListener(v -> builder.setTitle("¿Seguro?").setMessage("¿Deseas borrar todos los datos de la aplicación?").setCancelable(true)
                .setPositiveButton("Sí", (dialog, which) -> {
                    bd.execSQL("DELETE FROM usuario");
                    bd.execSQL("DELETE FROM rutejer");
                    bd.execSQL("DELETE FROM ejercicios");
                    bd.execSQL("DELETE FROM rutalim");
                    bd.execSQL("DELETE FROM comidas");
                    Toast.makeText(AjustesFragment.this.getContext(), "Todos los datos de la aplicación han sido borrados correctamente", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show());

        //Al presionar se irá al fragment de "Acerca de"
        acercaDe.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_ajustesFragment_to_acercaDeFragment));

        return view;
    }
}