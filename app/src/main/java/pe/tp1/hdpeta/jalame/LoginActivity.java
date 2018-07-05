package pe.tp1.hdpeta.jalame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    static final String BASE_URL = "http://services.tarrillobarba.com.pe:6789/";

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button ingresarButton;
    private TextView lblNoAccount;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        ingresarButton = (Button) findViewById(R.id.ingresarButton);
        lblNoAccount = (TextView) findViewById(R.id.lblNoAccount);

        lblNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenUserSignUpActivity();
            }
        });

        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
            }
        });

    }

    private void OpenUserSignUpActivity() {
        Intent userRegisterActivity = new Intent(this, UserRegisterActivity.class);
        startActivity(userRegisterActivity);
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void validateInput() {
        if (emailEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Inserse su email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Inserse su password", Toast.LENGTH_SHORT).show();
            return;
        }
        hideSoftKeyBoard();
        progressDialog = ProgressDialog.show(this,"Iniciando Sesión", "Porfavor espere..");
        callLogin(emailEditText.getText().toString(), passwordEditText.getText().toString());
    }

    private void callLogin(String userEmail, String password) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        RestClient restClient = retrofit.create(RestClient.class);
        Call<PersonBean> call = restClient.credentials(userEmail,password);

        call.enqueue(new Callback<PersonBean>() {
            @Override
            public void onResponse(Call<PersonBean> call, Response<PersonBean> response) {
                
                progressDialog.dismiss();
                switch (response.code()) {
                    case 200:
                        PersonBean data = response.body();
                        Log.d("Data", data.getNombre());
                        openMainActivity();
                        break;
                    case 404:
                        Log.d("Error message ", response.raw().toString());
                        Toast.makeText(LoginActivity.this, "Error: Usuario o Clave Incorrecto", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<PersonBean> call, Throwable t) {
                Log.e("error on login", t.toString());
                progressDialog.dismiss();
            }
        });


    }

    private void openMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }


}
