package pe.tp1.hdpeta.jalame.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import pe.tp1.hdpeta.jalame.R;
import pe.tp1.hdpeta.jalame.VistaVehiculos;


public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder>{
    private List<VehiculoBean> items;
    private Context context;
    private VistaVehiculos view;

    public VehiculoAdapter(List<VehiculoBean> items) {
        this.items = items;
    }

    public class VehiculoViewHolder extends RecyclerView.ViewHolder {
        public CardView vehiculoCardView;
        public TextView lblplaca;
        public TextView lblmarcamodelo;
        public TextView lblcolor;
        public TextView lblconductor;

        public VehiculoViewHolder(View v) {
            super(v);
            vehiculoCardView = (CardView) v.findViewById(R.id.vehiculoCardView);
            lblplaca = (TextView) v.findViewById(R.id.lblPlaca);
            lblmarcamodelo =(TextView) v.findViewById(R.id.lblMarcaModelo);
            lblcolor =(TextView) v.findViewById(R.id.lblColor);
            lblconductor =(TextView) v.findViewById(R.id.lblConductor);
        }
    }

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehiculo_card, parent, false);
        return new VehiculoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VehiculoViewHolder holder, final int p) {
        holder.lblplaca.setText(items.get(p).getMatricula());
        holder.lblmarcamodelo.setText(items.get(p).getMarca()+"/"+items.get(p).getModelo());
        holder.lblcolor.setText(items.get(p).getColor());
        holder.lblconductor.setText(String.valueOf(items.get(p).getCodPersona()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
