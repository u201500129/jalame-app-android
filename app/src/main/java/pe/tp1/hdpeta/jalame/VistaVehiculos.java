package pe.tp1.hdpeta.jalame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.tp1.hdpeta.jalame.adapter.VehiculoAdapter;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VistaVehiculos extends AppCompatActivity {
    public CardView vehiculoCardView;
    public TextView lblplaca;
    public TextView lblmarcamodelo;
    public TextView lblcolor;
    public TextView lblconductor;

    static final String BASE_URL = "http://services.tarrillobarba.com.pe:6789/";

    private RecyclerView recicladorVehiculo;
    private List<VehiculoBean> items = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_vehiculos);

        //TODO:Datos en duro para cambiar de usuario , latitud y longitud
        loadJSON("7","-12.05165","-77.03461");
        FillVehiculo();

        initViews();
    }

    private void initViews() {
        vehiculoCardView = (CardView) findViewById(R.id.vehiculoCardView);
        lblplaca = (TextView) findViewById(R.id.lblPlaca);
        lblmarcamodelo =(TextView) findViewById(R.id.lblMarcaModelo);
        lblcolor =(TextView) findViewById(R.id.lblColor);
        lblconductor =(TextView) findViewById(R.id.lblConductor);

        recicladorVehiculo= (RecyclerView) findViewById(R.id.recicladorVehiculos);
        recicladorVehiculo.setHasFixedSize(true);
        lManager=new LinearLayoutManager(this);
        recicladorVehiculo.setLayoutManager(lManager);
        adapter=new VehiculoAdapter(items);
        recicladorVehiculo.setAdapter(adapter);
    }

    private void loadJSON(String codUsuario, String latitud, String longitud) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RestClient restClient = retrofit.create(RestClient.class);
        Call<VehiculoBean> call = restClient.getData(codUsuario,latitud,longitud);
        //MENSAJE(call.toString());
        MENSAJE("-----"+call.toString());

        call.enqueue(new Callback<VehiculoBean>() {
            @Override
            public void onResponse(Call<VehiculoBean> call, Response<VehiculoBean> response) {
                switch (response.code()) {
                    case 200:
                        VehiculoBean vehiculo = response.body();
                        items.add(vehiculo);
                        MENSAJE("-----"+response.raw());
                        MENSAJE("-----"+response.body());
                        MENSAJE("-----"+response.body().getModelo());
                        //TODO: Agregar los datos al array list?
                        //view.notifyDataSetChanged(data.getResults());
                        break;
                    case 404:
                        Log.d("Error message ", response.raw().toString());
                        Toast.makeText(VistaVehiculos.this, "Error: Error desconocido", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<VehiculoBean> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
        //TEST
    }

    private void FillVehiculo() {
        /*items.add(new VehiculoBean(11,33,"dd223","Ford",
                "Eco Sport 4x4","1988","ADV334","Morado",5,
                2,"-77.8899","-33.7810","Hide", 3, "Disponible",
                ((byte) R.drawable.common_google_signin_btn_icon_dark), new Date(), 14));*/
        items.add(new VehiculoBean(11,33,"dd223","Ford",
                "Eco Sport 4x4","1988","ADV334","Morado",5,
                2,"-77.8899","-33.7810","Hide", 3, "Disponible",
                1, "122112", 14));

    }

    private void MENSAJE(String msg){
        Toast.makeText(VistaVehiculos.this, msg, Toast.LENGTH_SHORT).show();
        Log.d("------ALF:", msg);
    }
}
