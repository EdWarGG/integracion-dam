package com.example.myfitplan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitplan.db.entidades.RutinaAlimentacion;

import java.util.List;

//Esta clase es el adaptador para los items del RecyclerView de AlimentaciónFragment
public class NRAAdapter extends RecyclerView.Adapter<NRAAdapter.NRAViewHolder> {

    private final List<RutinaAlimentacion> rutAlimList;

    public NRAAdapter(List<RutinaAlimentacion> itemList) {
        super();
        this.rutAlimList = itemList;
    }

    @NonNull
    @Override
    public NRAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_rutalim, parent, false);
        return new NRAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NRAViewHolder holder, int position) {
        RutinaAlimentacion rutinaAlimentacion = rutAlimList.get(position);
        holder.tituloRutina.setText(rutinaAlimentacion.getNombre());
        holder.descripcion.setText(rutinaAlimentacion.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return rutAlimList.size();
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    public class NRAViewHolder extends RecyclerView.ViewHolder {

        public TextView tituloRutina;
        public TextView descripcion;

        public NRAViewHolder(@NonNull View itemView) {
            super(itemView);

            tituloRutina = itemView.findViewById(R.id.lera_tv_nombre);
            descripcion = itemView.findViewById(R.id.lera_tv_descripcion);

            //Al presionar se irá a RutAlimFragment y se le pasará el nombre de la rutina como argumento
            itemView.setOnClickListener(v -> {
                RutinaAlimentacion rutinaAlimentacion = rutAlimList.get(getAdapterPosition());
                String nombreDeLaRutina = rutinaAlimentacion.getNombre();
                Navigation.findNavController(itemView).navigate(com.example.myfitplan.AlimentacionFragmentDirections.actionAlimentacionFragmentToRutAlimFragment(nombreDeLaRutina));
            });
        }
    }
}
