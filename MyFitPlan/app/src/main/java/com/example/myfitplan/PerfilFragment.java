package com.example.myfitplan;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfitplan.R;
import com.example.myfitplan.db.DataBaseHandler;

public class PerfilFragment extends Fragment {

    DataBaseHandler db;
    Bitmap bitmap;

    ImageView imagenPerfil;
    TextView nombre;
    TextView estado;
    TextView fnac;
    TextView altura;
    TextView peso;
    TextView grasa;
    Button buttonEditar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        imagenPerfil = view.findViewById(R.id.imagen_perfil);
        nombre = view.findViewById(R.id.fp_tv_nombre);
        estado = view.findViewById(R.id.fp_tv_estado);
        fnac = view.findViewById(R.id.fp_tv_fnac);
        altura = view.findViewById(R.id.fp_tv_altura);
        peso = view.findViewById(R.id.fp_tv_peso);
        grasa = view.findViewById(R.id.fp_tv_grasa);
        buttonEditar = view.findViewById(R.id.fp_button_editar);

        db = new DataBaseHandler(this.getContext());
        SQLiteDatabase bd = db.getWritableDatabase();

        //Selecciona los datos del usuario desde la base de datos
        Cursor cursor = bd.rawQuery("SELECT username, fnac, altura, peso, porcentaje, estado, imagen FROM usuario WHERE id=1", null);
        //Si hay datos, se muestran en los TextView
        if (cursor.moveToFirst()) {
            nombre.setText(cursor.getString(0));
            fnac.setText(cursor.getString(1));
            altura.setText(cursor.getString(2));
            peso.setText(cursor.getString(3));
            grasa.setText(cursor.getString(4));
            estado.setText(cursor.getString(5));
            if (cursor.getBlob(6) != null) {
                byte[] imageBytes = cursor.getBlob(6);
                bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imagenPerfil.setImageBitmap(bitmap);
            }
        //Si no hay datos, el botón en vez de poner "EDITAR PERFIL", pondrá "CREAR PERFIL"
        }else{
            buttonEditar.setText("CREAR PERFIL");
        }
        cursor.close();

        //Al presionar se irá a "EditarPerfilFragment"
        buttonEditar.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_perfilFragment_to_editarPerfilFragment));

        return view;
    }

}
