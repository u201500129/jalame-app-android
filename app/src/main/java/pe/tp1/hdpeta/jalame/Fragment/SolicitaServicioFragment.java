package pe.tp1.hdpeta.jalame.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pe.tp1.hdpeta.jalame.R;

public class SolicitaServicioFragment extends DialogFragment {

    private TextView lblNombre;
    private Button btnAceptar;
    private Button btnCancelar;

    public SolicitaServicioFragment() {
    }

    public static SolicitaServicioFragment newInstance(String title) {
        SolicitaServicioFragment fdSolicit = new SolicitaServicioFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fdSolicit.setArguments(args);
        return fdSolicit;
    }

    public interface SolicitaServicioListener {
        void onFinishDialog(String respuesta);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_solicita_servicio, container);
        getDialog().setTitle("Solicitar Servicio");
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Acceso a controles
        lblNombre = (TextView) view.findViewById(R.id.lblNombreValue);
        btnAceptar = (Button) view.findViewById(R.id.dfBtnSolicitAceptar);
        btnCancelar = (Button) view.findViewById(R.id.dfBtnSolicitCancelar);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on success



                Toast.makeText(getContext(), "Registró solicitud de servicio", Toast.LENGTH_LONG).show();
                sendBackResult("R");
            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackResult("C");
            }
        });


        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }



    public void sendBackResult(String respuesta) {
        // Enviamos el resultado al fragment que llama.
        SolicitaServicioFragment.SolicitaServicioListener listener = (SolicitaServicioFragment.SolicitaServicioListener) getTargetFragment();
        listener.onFinishDialog(respuesta);
        dismiss();
    }

}
