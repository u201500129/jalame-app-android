package pe.tp1.hdpeta.jalame.Activity.Dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import pe.tp1.hdpeta.jalame.Network.RetrofitInstance;
import pe.tp1.hdpeta.jalame.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfirmRequestDialogActivity extends AppCompatActivity {

    VehiculoBean driver;
    PersonBean personBean;

    private TextView lblDriverName;
    private TextView lblCardBrandModel;
    private TextView lblCalificacion;
    private TextView lblMatricula;
    private Button btnCancel;
    private Button btnAcept;

    private static int codFormaPago = 1;
    private static String origenDes = "Santiago de Surco, Monterrico";
    private static LatLng latLngDestino = new LatLng(-12.104061,-76.962902);
    private static Double importe = 5.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_request_dialog);
        setFinishOnTouchOutside(false);

        lblDriverName = (TextView) findViewById(R.id.lblDriverName);
        lblCardBrandModel = (TextView) findViewById(R.id.lblCardBrandModel);
        lblCalificacion = (TextView) findViewById(R.id.lblCalificacion);
        lblMatricula = (TextView) findViewById(R.id.lblMatricula);
        btnAcept = (Button) findViewById(R.id.btnAcept);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        setTitle("Solitar Servicio");
        driver = new VehiculoBean(getIntent().getExtras().getInt("codVehiculo"),
                getIntent().getExtras().getInt("codPersona"),
                "",
                getIntent().getExtras().getString("marca"),
                getIntent().getExtras().getString("modelo"),
                getIntent().getExtras().getString("aFabrica"),
                getIntent().getExtras().getString("matricula"),
                "",
                0,
                getIntent().getExtras().getInt("asientosDisponibles"),
                "",
                "",
                "",
                0,
                "",
                "",
                getIntent().getExtras().getInt("distancia"));

        lblMatricula.setText(driver.getMatricula());
        lblCardBrandModel.setText(driver.getMarca() + " " + driver.getModelo() + " " + driver.getaFabrica());
        String calification = String.valueOf(driver.getCalificacion()) + " estrellas";
        lblCalificacion.setText(calification);

        RestClient restClient = RetrofitInstance.getRetrofitInstance().create(RestClient.class);
        Call<PersonBean> call = restClient.getUser(driver.getCodPersona());
        call.enqueue(new Callback<PersonBean>() {
            @Override
            public void onResponse(Call<PersonBean> call, Response<PersonBean> response) {
                personBean = response.body();
                lblDriverName.setText(personBean.getNombre());
            }

            @Override
            public void onFailure(Call<PersonBean> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(ConfirmRequestDialogActivity.this, "Se envió la solicitud al conductor", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendRequestToDriver() {

    }
}
