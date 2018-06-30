package pe.tp1.hdpeta.jalame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class VehiculoFragment extends Fragment {
    private EditText txtPolizaSOAT;
    private EditText txtMarca;
    private EditText txtModelo;
    private EditText txtColor;
    private EditText txtNumeroAsientos;
    private Spinner cboEstado;
    private Button btnGuardar;

    public VehiculoFragment() {
        // Required empty public constructor
    }

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
        View v = inflater.inflate(R.layout.fragment_vehiculo, container, false);

        ArrayAdapter<CharSequence> estadoAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Estado, android.R.layout.simple_spinner_item);
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        txtPolizaSOAT = v.findViewById(R.id.txtPolizaSOAT);
        txtMarca = v.findViewById(R.id.txtMarca);
        txtModelo = v.findViewById(R.id.txtModelo);
        txtColor = v.findViewById(R.id.txtColor);
        txtNumeroAsientos = v.findViewById(R.id.txtNumeroAsientos);
        cboEstado = v.findViewById(R.id.cboEstado);

        btnGuardar = v.findViewById(R.id.btnGuardar);

        cboEstado.setAdapter(estadoAdapter);

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (txtPolizaSOAT.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingrese el SOAT.", Toast.LENGTH_SHORT).show();
                    txtPolizaSOAT.setFocusable(true);
                    return;
                }
                if (txtMarca.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingrese la Marca.", Toast.LENGTH_SHORT).show();
                    txtMarca.setFocusable(true);
                    return;
                }
                if (txtModelo.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingrese el Modelo.", Toast.LENGTH_SHORT).show();
                    txtModelo.setFocusable(true);
                    return;
                }
                if (txtColor.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingrese el Color.", Toast.LENGTH_SHORT).show();
                    txtColor.setFocusable(true);
                    return;
                }
                if (txtNumeroAsientos.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingrese el Nro de Asiento.", Toast.LENGTH_SHORT).show();
                    txtNumeroAsientos.setFocusable(true);
                    return;
                }
            }
        });

        return v;
    }
}
