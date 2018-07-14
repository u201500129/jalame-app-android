package pe.tp1.hdpeta.jalame.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.tp1.hdpeta.jalame.Adapter.NearDriversAdapter;
import pe.tp1.hdpeta.jalame.Adapter.ServicesAdapter;
import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.Bean.ServicioBean;
import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import pe.tp1.hdpeta.jalame.Interface.NearDriverList;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import pe.tp1.hdpeta.jalame.Network.RetrofitInstance;
import pe.tp1.hdpeta.jalame.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NearDriversFragment extends Fragment {

    static final String BASE_URL = "http://services.tarrillobarba.com.pe:6789/";

    private RecyclerView recyclerView;
    private List<VehiculoBean> cars = new ArrayList<>();
    private NearDriversAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near_drivers, container, false);

        /*recyclerView = (RecyclerView) rootView.findViewById(R.id.servicesRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);*/

        recyclerView = (RecyclerView) rootView.findViewById(R.id.nearDriversRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = ProgressDialog.show(getContext(),"Cargando conductores cercanos", "Porfavor espere..");
        FillNearDrivers();
        return rootView;
    }

    private void FillNearDrivers() {

        RestClient retrofit = RetrofitInstance.getRetrofitInstance().create(RestClient.class);
//restClient.nearDrivers(7,"-12.05165","-77.03461");
        Call<NearDriverList> call = retrofit.nearDrivers(7,"-12.05165","-77.03461");
        call.enqueue(new Callback<NearDriverList>() {
            @Override
            public void onResponse(Call<NearDriverList> call, Response<NearDriverList> response) {
                progressDialog.dismiss();
                generateNearDriverList(response.body().getVehiculoArrayList());
            }

            @Override
            public void onFailure(Call<NearDriverList> call, Throwable t) {
                Log.d("Error: ", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }
    private void generateNearDriverList(ArrayList<VehiculoBean> nearDriverArrayList) {
        adapter = new NearDriversAdapter(nearDriverArrayList);
        recyclerView.setAdapter(adapter);
    }

}
