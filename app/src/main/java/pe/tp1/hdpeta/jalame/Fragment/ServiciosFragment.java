package pe.tp1.hdpeta.jalame.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.tp1.hdpeta.jalame.Activity.LoginActivity;
import pe.tp1.hdpeta.jalame.Adapter.ServicesAdapter;
import pe.tp1.hdpeta.jalame.Bean.ServicioBean;
import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import pe.tp1.hdpeta.jalame.Interface.ServiceList;
import pe.tp1.hdpeta.jalame.Network.RetrofitInstance;
import pe.tp1.hdpeta.jalame.R;
import pe.tp1.hdpeta.jalame.Singleton.PersonSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static pe.tp1.hdpeta.jalame.Fragment.NearDriversFragment.BASE_URL;

public class ServiciosFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<ServicioBean> services = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_servicios, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.servicesRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ServicesAdapter(services);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        FillServices();

        return rootView;
    }

    public void FillServices(){

        RestClient restClient = RetrofitInstance.getRetrofitInstance().create(RestClient.class);

        Call<ServiceList> call = restClient.services(1);

        Log.d("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ServiceList>() {
            @Override
            public void onResponse(Call<ServiceList> call, Response<ServiceList> response) {
                generateServiceList(response.body().getServiceArrayList());
            }

            @Override
            public void onFailure(Call<ServiceList> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    private void generateServiceList(ArrayList<ServicioBean> serviceArrayList) {
        Log.d("Nombre: ", serviceArrayList.get(0).getUsuario());
        adapter = new ServicesAdapter(serviceArrayList);
        adapter.notifyDataSetChanged();
    }


}
