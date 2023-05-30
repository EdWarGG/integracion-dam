package com.example.myfitplan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitplan.db.entidades.Ejercicio;

import java.util.List;

//Esta clase es el adaptador para los items del RecyclerView de RutEjerFragment
public class NEAdapter extends RecyclerView.Adapter<NEAdapter.NEViewHolder> {

    private final List<Ejercicio> ejerciciosList;

    public NEAdapter(List<Ejercicio> itemList) {
        super();
        this.ejerciciosList = itemList;
    }

    @NonNull
    @Override
    public NEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_ejercicio, parent, false);
        return new NEViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NEViewHolder holder, int position) {
        Ejercicio ejercicio = ejerciciosList.get(position);

        holder.tituloEjercicio.setText(ejercicio.getNombre());
        holder.seriesEjercicio.setText(String.valueOf(ejercicio.getSeries()));
        holder.repsEjercicio.setText(String.valueOf(ejercicio.getRepeticiones()));
        holder.pesoEjercicio.setText(String.valueOf(ejercicio.getPeso()));

    }

    @Override
    public int getItemCount() {
        return ejerciciosList.size();
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    public class NEViewHolder extends RecyclerView.ViewHolder {

        public TextView tituloEjercicio;
        public TextView seriesEjercicio;
        public TextView repsEjercicio;
        public TextView pesoEjercicio;

        public NEViewHolder(@NonNull View itemView) {
            super(itemView);

            tituloEjercicio = itemView.findViewById(R.id.lee_tv_nombre);
            seriesEjercicio = itemView.findViewById(R.id.lee_tv_series);
            repsEjercicio = itemView.findViewById(R.id.lee_tv_reps);
            pesoEjercicio = itemView.findViewById(R.id.lee_tv_peso);

            //Al presionar se irá a EditarEjercicioFragment y se le pasará el nombre del ejercicio como argumento
            itemView.setOnClickListener(v -> {
                Ejercicio ejercicio = ejerciciosList.get(getAdapterPosition());
                String nombreEjercicio = ejercicio.getNombre();
                Navigation.findNavController(itemView).navigate(com.example.myfitplan.RutEjerFragmentDirections.actionRutEjerFragmentToEditarEjercicioFragment(nombreEjercicio));
            });
        }
    }
}
