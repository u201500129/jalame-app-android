package pe.tp1.hdpeta.jalame.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import pe.tp1.hdpeta.jalame.DataBase.DBHelper;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import pe.tp1.hdpeta.jalame.Network.RetrofitInstance;
import pe.tp1.hdpeta.jalame.R;
import pe.tp1.hdpeta.jalame.Singleton.VehiculoSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverSignUpActivity extends AppCompatActivity {

    static final String BASE_URL = "http://services.tarrillobarba.com.pe:6789/";
    private EditText txtCarBrand;
    private EditText txtCarModel;
    private EditText txtCarColor;
    private EditText txtNumberSits;
    private Spinner spnDriverState;
    private Button btnSave;

    List<String> spnStateArray;
    List<String> spnStateValuesArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        txtCarBrand = (EditText) findViewById(R.id.txtCarBrand);
        txtCarModel = (EditText) findViewById(R.id.txtCarModel);
        txtCarColor = (EditText) findViewById(R.id.txtCarColor);
        txtNumberSits = (EditText) findViewById(R.id.txtNumberSits);
        spnDriverState = (Spinner) findViewById(R.id.spnDriverState);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDriverInfo();
            }
        });

        spnStateArray = new ArrayList<>();
        spnStateArray.add("Activo");
        spnStateArray.add("Inactivo");

        spnStateValuesArray = new ArrayList<>();
        spnStateValuesArray.add("A");
        spnStateValuesArray.add("I");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnStateArray);
        spnDriverState.setAdapter(dataAdapter);

    }


    private void saveDriverInfo() {

        if (txtCarBrand.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese la marca del vehículo", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtCarColor.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese el color del vehículo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (txtNumberSits.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese la cantidad de asientos disponibles de su vehículo", Toast.LENGTH_SHORT).show();
            return;
        }
        DBHelper db = new DBHelper(this);
        PersonBean personBean = db.personBean();


        VehiculoBean newCar = new VehiculoBean(0,
                personBean.getCodPersona(),
                "",
                txtCarBrand.getText().toString(),
                txtCarModel.getText().toString(),
                "",
                "",
                txtCarColor.getText().toString(),
                Integer.valueOf(txtNumberSits.getText().toString()),
                0,
                "",
                "",
                "",
                0,
                spnStateValuesArray.get(spnDriverState.getSelectedItemPosition()),
                "",
                0
                );

        RestClient restClient = RetrofitInstance.getRetrofitInstance().create(RestClient.class);
        Call<VehiculoBean> call = restClient.createCar(newCar);

        call.enqueue(new Callback<VehiculoBean>() {
            @Override
            public void onResponse(Call<VehiculoBean> call, Response<VehiculoBean> response) {
                openMainActivityWithVehiculoBean(response.body());
            }

            @Override
            public void onFailure(Call<VehiculoBean> call, Throwable t) {
                Log.d("Error:", t.getMessage());
            }
        });

    }
    private void openMainActivityWithVehiculoBean(VehiculoBean vehiculoBean) {
        DBHelper db = new DBHelper(this);
        db.saveVehiculo(vehiculoBean);

        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}
