package pe.tp1.hdpeta.jalame.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.DataBase.DBHelper;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import pe.tp1.hdpeta.jalame.Network.RetrofitInstance;
import pe.tp1.hdpeta.jalame.R;
import pe.tp1.hdpeta.jalame.Singleton.PersonSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSignUpActivity extends AppCompatActivity {
    static final String BASE_URL = "http://services.tarrillobarba.com.pe:6789/";
    private EditText txtName;
    private EditText txtLastName;
    private EditText txtEmail;
    private EditText txtPhone;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private EditText txtDNI;
    private CheckBox ckbHaveCar;
    private Button btnRegister;

    private ProgressDialog progressDialog;

    private HashMap<String, PersonBean> userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        txtName = (EditText) findViewById(R.id.txtName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtDNI = (EditText) findViewById(R.id.txtDNI);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        ckbHaveCar = (CheckBox) findViewById(R.id.ckbHaveCar);
        ckbHaveCar.setChecked(false);
        ckbHaveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ckbHaveCar.isChecked()) {
                    btnRegister.setText("Continuar");
                } else {
                    btnRegister.setText("Registrar");
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
    }

    private void RegisterUser() {
        if (!IsValidForm()) {
            return;
        }

        PersonBean newPerson = new PersonBean();
        newPerson.setCodPersona(0);
        newPerson.setNombre(txtName.getText().toString());
        newPerson.setApellido(txtLastName.getText().toString());
        newPerson.setClave(txtPassword.getText().toString());
        newPerson.setCorreo(txtEmail.getText().toString());
        newPerson.setTelefono(txtPhone.getText().toString());
        newPerson.setCalificacion(0);
        newPerson.setCarrera("");
        newPerson.setDni(txtDNI.getText().toString());
        newPerson.setEstadoR("");
        if (ckbHaveCar.isChecked()){
            newPerson.setPerfil("C");
        }else {
            newPerson.setPerfil("U");
        }

        newPerson.setSexo("");

        saveUserInformation(newPerson);

    }

    private void validateCheckBoxWithPersonBean(PersonBean personBean) {

        CleanForm();

        DBHelper bd = new DBHelper(this);
        bd.savePerson(personBean);

        if (ckbHaveCar.isChecked()) {
            Intent driverSignUpIntent = new Intent(this, DriverSignUpActivity.class);
            startActivity(driverSignUpIntent);
        } else {
            openMainActivity();
            finish();
        }
    }


    private void saveUserInformation(PersonBean newPerson) {

        RestClient restClient = RetrofitInstance.getRetrofitInstance().create(RestClient.class);
        Call<PersonBean> call = restClient.createUser(newPerson);

        call.enqueue(new Callback<PersonBean>() {
            @Override
            public void onResponse(Call<PersonBean> call, Response<PersonBean> response) {
                switch (response.code()){
                    case 200:
                        validateCheckBoxWithPersonBean(response.body());
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<PersonBean> call, Throwable t) {

            }
        });
    }

    private void openMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    private void CleanForm(){
        txtName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
    }

    private boolean IsValidForm() {
        if (txtName.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese sus nombres correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtName.getText().toString().length() < 2 || txtName.getText().toString().length() > 40){
            Toast.makeText(this, "Sus nombres deben contener 2 a 40 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtLastName.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese sus apellidos correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtName.getText().toString().length() < 2 || txtName.getText().toString().length() > 40){
            Toast.makeText(this, "Sus Apellidos debe contener 2 a 40 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese su email correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtPhone.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese número de celular correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtPhone.getText().toString().length() > 9){
            Toast.makeText(this, "Su número de celular debe contener 9 dígitos como máximo", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (txtPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese contraseña correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtPassword.getText().toString().length() > 8){
            Toast.makeText(this, "Su contraseña debe contener 8 dígitos como máximo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtConfirmPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Debe confirmar su contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
            Toast.makeText(this, "La contraseña ingresada no son iguales", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
