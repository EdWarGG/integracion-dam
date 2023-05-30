package com.example.myfitplan;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfitplan.db.DataBaseHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class EditarPerfilFragment extends Fragment {

    DataBaseHandler db;
    Bitmap selectedImage;
    Bitmap bitmap;

    ImageView cancelarEdicion;
    ImageView aceptarEdicion;
    ImageView editarImagen;

    ImageView imagenPerfil;

    EditText nombre;
    EditText fnac;
    EditText altura;
    EditText peso;
    EditText porcentaje;
    EditText estado;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);

        nombre = view.findViewById(R.id.fep_ed_nombre);
        fnac = view.findViewById(R.id.fep_ed_fnac);
        altura = view.findViewById(R.id.fep_ed_altura);
        peso = view.findViewById(R.id.fep_ed_peso);
        porcentaje = view.findViewById(R.id.fep_ed_grasa);
        estado = view.findViewById(R.id.fep_ed_estado);
        cancelarEdicion = view.findViewById(R.id.fep_cancelar_edicion);
        aceptarEdicion = view.findViewById(R.id.fep_aceptar_edicion);
        editarImagen = view.findViewById(R.id.fep_editar_imagen);
        imagenPerfil = view.findViewById(R.id.imagen_perfil);

        db = new DataBaseHandler(this.getContext());
        SQLiteDatabase bd = db.getWritableDatabase();

        //Selecciona todos los datos del usuario
        Cursor cursor = bd.rawQuery("SELECT username, fnac, altura, peso, porcentaje, estado, imagen FROM usuario WHERE id=1", null);
        //Si existen los datos, rellena los EditText con los datos del usuario
        if (cursor.moveToFirst()){
            nombre.setText(cursor.getString(0));
            fnac.setText(cursor.getString(1));
            altura.setText(cursor.getString(2));
            peso.setText(cursor.getString(3));
            porcentaje.setText(cursor.getString(4));
            estado.setText(cursor.getString(5));
            if (cursor.getBlob(6) != null){
                byte[] imageBytes = cursor.getBlob(6);
                bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imagenPerfil.setImageBitmap(bitmap);
            }
            //Al presionar los datos del usuario se actualizarán
            aceptarEdicion.setOnClickListener(v -> {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                if (db.updateUsuario(nombre.getText().toString(), byteArray, fnac.getText().toString(), altura.getText().toString(), peso.getText().toString(), porcentaje.getText().toString(), estado.getText().toString())) {
                    Toast.makeText(EditarPerfilFragment.this.getContext(), "Usuario modificado correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditarPerfilFragment.this.getContext(), "Hubo un fallo al intenter modificar el usuario en la base de datos", Toast.LENGTH_SHORT).show();
                }

                Navigation.findNavController(view).navigate(R.id.action_editarPerfilFragment_to_perfilFragment);
            });
        //Si no existen los datos, no se rellenará ningún EditText
        }else{
            //Al presionar se creará un nuevo usuario
            aceptarEdicion.setOnClickListener(v -> {
                if (!nombre.getText().toString().trim().isEmpty() && !fnac.getText().toString().trim().isEmpty() && !altura.getText().toString().trim().isEmpty() && !peso.getText().toString().trim().isEmpty() && !porcentaje.getText().toString().trim().isEmpty() && !estado.getText().toString().trim().isEmpty()) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 25, stream);
                    byte[] byteArray = stream.toByteArray();

                    if (db.insertUsuario(nombre.getText().toString(), byteArray, fnac.getText().toString(), altura.getText().toString(), peso.getText().toString(), porcentaje.getText().toString(), estado.getText().toString())) {
                        Toast.makeText(EditarPerfilFragment.this.getContext(), "Usuario guardado correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditarPerfilFragment.this.getContext(), "Hubo un fallo al intenter guardar el usuario en la base de datos", Toast.LENGTH_SHORT).show();
                    }
                    Navigation.findNavController(view).navigate(R.id.action_editarPerfilFragment_to_perfilFragment);
                }else {
                    Toast.makeText(EditarPerfilFragment.this.getContext(), "Debes seleccionar todos los campos para crear tu perfil de usuario", Toast.LENGTH_SHORT).show();
                }
            });

        }
        cursor.close();

        //Al presionar se hará un cambio de actividad hacia la pantalla de seleccionar imagen del dispositivo
        editarImagen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        //Al presionar se irá a PerfilFragment
        cancelarEdicion.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_editarPerfilFragment_to_perfilFragment));

        //Al presionar se abrirá un dialog para seleccionar la fecha de nacimiento
        fnac.setOnClickListener(v1 -> datePicker());

        return view;
    }

    //Se encarga de procesar la imagen seleccionada y asignársela al ImageView "imagenPerfil"
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                Uri imageUri = data.getData();
                Toast.makeText(this.getContext(),"Imagen seleccionada",Toast.LENGTH_SHORT).show();
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), imageUri);
                imagenPerfil.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Se encarga de crear y mostrar e calendario
    private void datePicker(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this.getContext(), R.style.DateTimePickerTheme, (view, year, month, dayOfMonth) -> {
            if((month+1)<10 && (dayOfMonth)<10) {
                String mes = "0" + (month + 1);
                String dia = "0" + dayOfMonth;
                fnac.setText(dia + "/" + mes + "/" + year);
            } else if ((month+1)<10) {
                String mes = "0" + (month + 1);
                fnac.setText(dayOfMonth + "/" + mes + "/" + year);
            } else if ((dayOfMonth)<10) {
                String dia = "0" + dayOfMonth;
                fnac.setText(dia + "/" + (month+1) + "/" + year);
            }else {
                fnac.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dpd.show();
    }

}