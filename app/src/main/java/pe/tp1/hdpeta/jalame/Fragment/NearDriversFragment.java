package pe.tp1.hdpeta.jalame.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import pe.tp1.hdpeta.jalame.Interface.RestClient;
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
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near_drivers, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.nearDriversRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NearDriversAdapter(cars);
        recyclerView.setAdapter(adapter);

        FillNearDrivers();
        return rootView;
    }

    private void FillNearDrivers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RestClient restClient = retrofit.create(RestClient.class);
        Call<List<VehiculoBean>> call = restClient.nearDrivers(7,"-12.05165","-77.03461");

        call.enqueue(new Callback<List<VehiculoBean>>() {
            @Override
            public void onResponse(Call<List<VehiculoBean>> call, Response<List<VehiculoBean>> response) {
                switch (response.code()){
                    case 200:

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<VehiculoBean>> call, Throwable t) {

            }
        });
    }

}
