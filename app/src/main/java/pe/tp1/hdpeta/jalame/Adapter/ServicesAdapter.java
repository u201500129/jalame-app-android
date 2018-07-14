package pe.tp1.hdpeta.jalame.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import pe.tp1.hdpeta.jalame.Bean.ServicioBean;
import pe.tp1.hdpeta.jalame.R;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>{

    ArrayList<ServicioBean> services;

    public ServicesAdapter(ArrayList<ServicioBean> services){
        this.services = services;
    }

    @NonNull
    @Override
    public ServicesAdapter.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_card, parent, false);
        return new ServiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ServiceViewHolder holder, int position) {
        holder.imgProfile.setImageResource(R.drawable.icon_profle);
        holder.txtServiceDate.setText(services.get(position).getFecRegistro().toString());
        holder.txtOriginDescription.setText(services.get(position).getOrigenDes());
        holder.txtDestinyDescription.setText(services.get(position).getDestinoDes());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgProfile;
        public TextView txtServiceDate;
        public TextView txtOriginDescription;
        public TextView txtDestinyDescription;


        public ServiceViewHolder(View itemView) {
            super(itemView);

            imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
            txtServiceDate = (TextView) itemView.findViewById(R.id.txtServiceDate);
            txtOriginDescription = (TextView) itemView.findViewById(R.id.txtOriginDescription);
            txtDestinyDescription = (TextView) itemView.findViewById(R.id.txtDestinyDescription);

        }
    }

    public void updateNewServices(ArrayList<ServicioBean> services){
        this.services = services;
        notifyDataSetChanged();
    }
}
