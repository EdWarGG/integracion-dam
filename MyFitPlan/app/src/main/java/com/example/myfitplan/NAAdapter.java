package com.example.myfitplan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitplan.db.entidades.Comida;

import java.util.List;

//Esta clase es el adaptador para los items del RecyclerView de RutAlimFragment
public class NAAdapter extends RecyclerView.Adapter<NAAdapter.NAViewHolder> {

    private final List<Comida> comidasList;

    public NAAdapter(List<Comida> itemList) {
        super();
        this.comidasList = itemList;
    }

    @NonNull
    @Override
    public NAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_comida, parent, false);
        return new NAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NAViewHolder holder, int position) {
        Comida comida = comidasList.get(position);
        holder.tituloComida.setText(comida.getNombre());
        holder.ingredienteComida.setText(comida.getIngrediente_principal());
        holder.pesoComida.setText(String.valueOf(comida.getPeso()));
    }

    @Override
    public int getItemCount() {
        return comidasList.size();
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    public class NAViewHolder extends RecyclerView.ViewHolder {

        public TextView tituloComida;
        public TextView ingredienteComida;
        public TextView pesoComida;

        public NAViewHolder(@NonNull View itemView) {
            super(itemView);

            tituloComida = itemView.findViewById(R.id.lec_tv_nombre);
            ingredienteComida = itemView.findViewById(R.id.lec_tv_ingrediente);
            pesoComida = itemView.findViewById(R.id.lec_tv_peso);

            //Al presionar se irá a EditarComidaFragment y se le pasará el nombre de la comida como argumento
            itemView.setOnClickListener(v -> {
                Comida comida = comidasList.get(getAdapterPosition());
                String nombreComida = comida.getNombre();
                Navigation.findNavController(itemView).navigate(com.example.myfitplan.RutAlimFragmentDirections.actionRutAlimFragmentToEditarComidaFragment(nombreComida));
            });
        }
    }
}
