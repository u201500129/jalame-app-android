package pe.tp1.hdpeta.jalame.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import pe.tp1.hdpeta.jalame.Network.RetrofitInstance;
import pe.tp1.hdpeta.jalame.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearDriversAdapter extends RecyclerView.Adapter<NearDriversAdapter.NearDriversViewHolder> {

    ArrayList<VehiculoBean> cars;

    public NearDriversAdapter(ArrayList<VehiculoBean> cars){
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
    public void onBindViewHolder(@NonNull final NearDriversAdapter.NearDriversViewHolder holder, int position) {

        RestClient restClient = RetrofitInstance.getRetrofitInstance().create(RestClient.class);

        Call<PersonBean> call = restClient.getUser(cars.get(position).getCodPersona());

        call.enqueue(new Callback<PersonBean>() {
            @Override
            public void onResponse(Call<PersonBean> call, Response<PersonBean> response) {
                holder.txtDriverName.setText(response.body().getNombre());
            }

            @Override
            public void onFailure(Call<PersonBean> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        String marcaModel = cars.get(position).getMarca() + " " + cars.get(position).getModelo();
        holder.txtBrandModelCar.setText(marcaModel);
        holder.txtLicenseRegistrationCar.setText(cars.get(position).getMatricula());
        String distanceKM = cars.get(position).getDistancia() + "m";
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
        public TextView txtDriverName;


        public NearDriversViewHolder(View itemView) {
            super(itemView);

            txtBrandModelCar = (TextView) itemView.findViewById(R.id.txtBrandModelCar);
            txtLicenseRegistrationCar = (TextView) itemView.findViewById(R.id.txtLicenseRegistrationCar);
            txtCarDistance = (TextView) itemView.findViewById(R.id.txtCarDistance);
            txtDriverName = (TextView) itemView.findViewById(R.id.txtDriverName);
        }
    }
}
