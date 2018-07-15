package pe.tp1.hdpeta.jalame.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.DataBase.DBHelper;
import pe.tp1.hdpeta.jalame.Network.HttpUrlHandler;
import pe.tp1.hdpeta.jalame.R;

public class QualifyServiceFragment extends Fragment {
    private RatingBar ratingBar;
    private EditText txtComentario;
    private Button btnCalificar;
    private PersonBean currentPerson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_qualify, container, false);
        getActivity().setTitle("Calificar Servicio");

        String idUser;
        String perfilUser;
        String idService;

        ratingBar = v.findViewById(R.id.ratingBar);
        txtComentario = v.findViewById(R.id.txtComentario);
        btnCalificar = v.findViewById(R.id.btnCalificar);

        DBHelper db = new DBHelper(getContext());
        PersonBean personBean = db.personBean();

        idUser = String.valueOf(personBean.getCodPersona());
        perfilUser = String.valueOf(personBean.getPerfil());
        idService = "1";

        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rate;
                String path = "";
                String comentario;
                rate = String.valueOf(ratingBar.getRating());
                rate = rate.substring(0, rate.length() - 2);

                if (txtComentario.getText().toString().isEmpty()){
                    comentario = "Sin comentario";
                }else {
                    comentario = txtComentario.getText().toString();
                }

                if (perfilUser.equals("U")) { //Usuario califica a conductor
                    path = "servicio/rate/driver/" + idService + "/" + rate + "/" + comentario;
                }else if (perfilUser.equals("C")) { //Conductor califica a usuario
                    path = "servicio/rate/user/" + idService + "/" + rate + "/" + comentario;
                }

                HttpUrlHandler httpRest = new HttpUrlHandler("PUT", path);

                String jsonString ;

                if (httpRest.readREST()){
                    jsonString = httpRest.getJsonString();

                    if (jsonString.length() > 10) {
                        try {
                            //Creando Objeto JSON
                            JSONObject dataJson = new JSONObject(jsonString);
                            Boolean estado = dataJson.getBoolean("status");

                            if (estado) {
                                Toast.makeText(getContext(),"Calificación correcta!!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(),"Calificación incorrecta!!!", Toast.LENGTH_SHORT).show();
                            }

                            Log.w("JALAME", "INFO: " + dataJson);
                        } catch (JSONException e) {
                            Log.w("JALAME", "ERROR: JSON " + e.getMessage());
                        }
                    }
                }
            }
        });

        return v;
    }
}
