package pe.tp1.hdpeta.jalame.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.tp1.hdpeta.jalame.R;
import pe.tp1.hdpeta.jalame.model.Vehiculo;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.PersonViewHolder>{
    private List<Vehiculo> items;

    public VehiculoAdapter(List<Vehiculo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehiculo_card,parent,false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int p) {
        holder.lblplaca.setText(items.get(p).getPlaca());
        holder.lblmarcamodelo.setText(items.get(p).getMarca()+"/"+items.get(p).getModelo());
        holder.lblcolor.setText(items.get(p).getColor());
        holder.lblconductor.setText(String.valueOf(items.get(p).getIdConductor()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        public CardView vehiculoCardView;
        public TextView lblplaca;
        public TextView lblmarcamodelo;
        public TextView lblcolor;
        public TextView lblconductor;

        public PersonViewHolder(View v) {
            super(v);
            vehiculoCardView = (CardView) v.findViewById(R.id.vehiculoCardView);
            lblplaca = (TextView) v.findViewById(R.id.lblPlaca);
            lblmarcamodelo =(TextView) v.findViewById(R.id.lblMarcaModelo);
            lblcolor =(TextView) v.findViewById(R.id.lblColor);
            lblconductor =(TextView) v.findViewById(R.id.lblConductor);
        }
    }
}
