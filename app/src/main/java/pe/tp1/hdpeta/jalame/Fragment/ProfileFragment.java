package pe.tp1.hdpeta.jalame.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.DataBase.DBHelper;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import pe.tp1.hdpeta.jalame.Network.RetrofitInstance;
import pe.tp1.hdpeta.jalame.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private EditText txtName;
    private EditText txtLastName;
    private EditText txtPhone;
    private RadioGroup radioButtonsGroupSexo;
    private EditText txtCorreo;
    private EditText txtDNI;
    private EditText txtCarrera;
    private EditText lblCalificacion;
    private EditText txtPassword;
    private RadioButton radioActivadoMan;
    private RadioButton radioDesactivadoFemale;
    private Button btnSave;

    private PersonBean currentPerson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        getActivity().setTitle("Mi Perfil");

        DBHelper db = new DBHelper(getContext());
        //currentPerson = db.personBean();

        txtName = (EditText) rootView.findViewById(R.id.txtName);
        txtLastName = (EditText) rootView.findViewById(R.id.txtLastName);
        txtPhone = (EditText) rootView.findViewById(R.id.txtPhone);
        radioButtonsGroupSexo = (RadioGroup) rootView.findViewById(R.id.radioButtonsGroupSexo);
        txtCorreo = (EditText) rootView.findViewById(R.id.txtCorreo);
        txtDNI = (EditText) rootView.findViewById(R.id.txtDNI);
        txtCarrera = (EditText) rootView.findViewById(R.id.txtCarrera);
        lblCalificacion = (EditText) rootView.findViewById(R.id.lblCalificacion);
        txtPassword = (EditText) rootView.findViewById(R.id.txtPassword);
        radioActivadoMan = (RadioButton) rootView.findViewById(R.id.radioActivadoMan);
        radioDesactivadoFemale = (RadioButton) rootView.findViewById(R.id.radioDesactivadoFemale);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);

        setCurrenPerson();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePersonBean();
            }
        });

        return rootView;
    }

    private void setCurrenPerson() {

        txtName.setText(currentPerson.getNombre());
        txtLastName.setText(currentPerson.getApellido());
        txtPhone.setText(currentPerson.getTelefono());
        txtCorreo.setText(currentPerson.getCorreo());
        txtDNI.setText(currentPerson.getDni());
        txtCarrera.setText(currentPerson.getCarrera());
        lblCalificacion.setText(String.valueOf(currentPerson.getCalificacion()));

        if (currentPerson.getSexo() == "M"){
            radioActivadoMan.setChecked(true);
        } else {
            radioDesactivadoFemale.setChecked(true);
        }
    }

    private void updatePersonBean() {

        if (!IsValidForm()){
            return;
        }

        String sexo;
        if (radioActivadoMan.isChecked()) {
            sexo = "M";
        } else {
            sexo = "F";
        }

        PersonBean updatedPerson = new PersonBean(
                currentPerson.getCodPersona(),
                txtName.getText().toString(),
                txtLastName.getText().toString(),
                sexo,
                txtDNI.getText().toString(),
                "",
                txtCarrera.getText().toString(),
                txtCorreo.getText().toString(),
                txtPhone.getText().toString(),
                currentPerson.getCalificacion(),
                txtPassword.getText().toString().isEmpty() ? currentPerson.getClave() : txtPassword.getText().toString(),
                ""
                );

        RestClient restClient = RetrofitInstance.getRetrofitInstance().create(RestClient.class);
        Call<PersonBean> call = restClient.updateUser(updatedPerson);
        
        call.enqueue(new Callback<PersonBean>() {
            @Override
            public void onResponse(Call<PersonBean> call, Response<PersonBean> response) {
                userUpdated(response.body());
            }

            @Override
            public void onFailure(Call<PersonBean> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    private void userUpdated(PersonBean updatedPerson) {
        DBHelper db = new DBHelper(getContext());
        //db.savePerson(updatedPerson);
        Toast.makeText(getContext(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
    }

    private boolean IsValidForm() {
        if (txtName.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Ingrese sus nombres correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtName.getText().toString().length() < 2 || txtName.getText().toString().length() > 40){
            Toast.makeText(getContext(), "Sus nombres deben contener 2 a 40 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtLastName.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Ingrese sus apellidos correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtName.getText().toString().length() < 2 || txtName.getText().toString().length() > 40){
            Toast.makeText(getContext(), "Sus Apellidos debe contener 2 a 40 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtCorreo.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Ingrese su email correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtPhone.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Ingrese número de celular correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtCarrera.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Ingrese su carrera correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtPhone.getText().toString().length() > 9){
            Toast.makeText(getContext(), "Su número de celular debe contener 9 dígitos como máximo", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (txtPassword.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Ingrese contraseña correctamente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtPassword.getText().toString().length() > 8){
            Toast.makeText(getContext(), "Su contraseña debe contener 8 dígitos como máximo", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }
}
