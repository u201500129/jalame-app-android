package pe.tp1.hdpeta.jalame.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import pe.tp1.hdpeta.jalame.R;

public class NearDriversAdapter extends RecyclerView.Adapter<NearDriversAdapter.NearDriversViewHolder> {

    List<VehiculoBean> cars;

    public NearDriversAdapter(List<VehiculoBean> cars){
        this.cars = cars;
    }
    @NonNull
    @Override
    public NearDriversAdapter.NearDriversViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.near_driver_card,parent,false);
        return new NearDriversViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearDriversAdapter.NearDriversViewHolder holder, int position) {

        String marcaModel = cars.get(position).getMarca() + " - " + cars.get(position).getModelo();
        holder.txtBrandModelCar.setText(marcaModel);
        holder.txtLicenseRegistrationCar.setText(cars.get(position).getMatricula());
        String distanceKM = cars.get(position).getDistancia() + "km";
        holder.txtCarDistance.setText(distanceKM);

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class NearDriversViewHolder extends RecyclerView.ViewHolder{

        public TextView txtBrandModelCar;
        public TextView txtLicenseRegistrationCar;
        public TextView txtCarDistance;


        public NearDriversViewHolder(View itemView) {
            super(itemView);

            txtBrandModelCar = (TextView) itemView.findViewById(R.id.txtBrandModelCar);
            txtLicenseRegistrationCar = (TextView) itemView.findViewById(R.id.txtLicenseRegistrationCar);
            txtCarDistance = (TextView) itemView.findViewById(R.id.txtCarDistance);
        }
    }
}
