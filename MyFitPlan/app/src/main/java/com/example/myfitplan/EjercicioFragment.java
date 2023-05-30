package com.example.myfitplan;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitplan.db.DataBaseHandler;
import com.example.myfitplan.db.entidades.RutinaEjercicio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EjercicioFragment extends Fragment {

    DataBaseHandler db;
    NREAdapter adapter;
    RecyclerView recyclerView;

    Bitmap bitmap;

    ImageView imagenPerfil;
    TextView perfilText;
    TextView aviso;
    FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejercicio, container, false);

        imagenPerfil = view.findViewById(R.id.fe_imagen_perfil);
        perfilText = view.findViewById(R.id.fe_tv_perfil);
        aviso = view.findViewById(R.id.fe_tv_aviso);
        recyclerView = view.findViewById(R.id.fe_recyclerview);
        fab = view.findViewById(R.id.fe_fab);

        db = new DataBaseHandler(this.getContext());
        SQLiteDatabase bd = db.getWritableDatabase();

        //Lista con las rutinas de entrenamiento
        List<RutinaEjercicio> itemList = db.getRutinasEjer();

        //Dependiendo del tamaño de la lista se determinará los que pondrá el aviso
        if (itemList.size() == 0){
            aviso.setText("Aún no has creado ninguna rutina de entrenamiento.");
        } else if (itemList.size() == 1) {
            aviso.setText("Has creado " + itemList.size() + " rutina de entrenamiento.");
        }else {
            aviso.setText("Has creado " + itemList.size() + " rutinas de entrenamiento.");
        }

        //Adapter para asignar las rutinas al RecyclerView
        adapter = new NREAdapter(itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Para asignar la foto al icono de perfil de usuario
        Cursor cursor = bd.rawQuery("SELECT imagen FROM usuario WHERE id=1", null);
        if (cursor.moveToFirst()) {
            byte[] imageBytes = cursor.getBlob(0);
            bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imagenPerfil.setImageBitmap(bitmap);
        }
        cursor.close();

        //Para asignar el nombre de debajo del icono de perfil de usuario
        Cursor cursor2 = bd.rawQuery("SELECT username FROM usuario WHERE id=1", null);
        if (cursor2.moveToFirst()) {
            perfilText.setText(cursor2.getString(0));
        }else{
            perfilText.setText("");
        }
        cursor2.close();

        //Al pulsar en la imagen de perfil, se irá a la pantalla del perfil
        imagenPerfil.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_ejercicioFragment_to_perfilFragment));

        //Al pulsar se irá a la pantalla de creación de una nueva rutina
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_ejercicioFragment_to_nuevaRutEjerFragment));

        return view;
    }
}