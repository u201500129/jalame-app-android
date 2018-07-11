package pe.tp1.hdpeta.jalame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegisterActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtLastName;
    private EditText txtEmail;
    private EditText txtPhone;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        txtName = (EditText) findViewById(R.id.txtName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

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

        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
        CleanForm();
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
