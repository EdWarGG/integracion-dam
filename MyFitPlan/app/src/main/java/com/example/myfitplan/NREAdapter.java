package com.example.myfitplan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitplan.db.entidades.RutinaEjercicio;

import java.util.List;

//Esta clase es el adaptador para los items del RecyclerView de EjercicioFragment
public class NREAdapter extends RecyclerView.Adapter<NREAdapter.NREViewHolder> {

    private final List<RutinaEjercicio> rutEjerList;

    public NREAdapter(List<RutinaEjercicio> itemList) {
        super();
        this.rutEjerList = itemList;
    }

    @NonNull
    @Override
    public NREViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_rutejer, parent, false);
        return new NREViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NREViewHolder holder, int position) {
        RutinaEjercicio rutinaEjercicio = rutEjerList.get(position);

        holder.tituloRutina.setText(rutinaEjercicio.getNombre());
        holder.descripcion.setText(rutinaEjercicio.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return rutEjerList.size();
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    public class NREViewHolder extends RecyclerView.ViewHolder {

        public TextView tituloRutina;
        public TextView descripcion;

        public NREViewHolder(@NonNull View itemView) {
            super(itemView);

            tituloRutina = itemView.findViewById(R.id.lere_tv_nombre);
            descripcion = itemView.findViewById(R.id.lere_tv_descripcion);

            //Al presionar se irá a RutEjerFragment y se le pasará el nombre de la rutina como argumento
            itemView.setOnClickListener(v -> {
                RutinaEjercicio rutinaEjercicio = rutEjerList.get(getAdapterPosition());
                String nombreDeLaRutina = rutinaEjercicio.getNombre();
                Navigation.findNavController(itemView).navigate(com.example.myfitplan.EjercicioFragmentDirections.actionEjercicioFragmentToRutEjerFragment(nombreDeLaRutina));
            });
        }
    }
}
