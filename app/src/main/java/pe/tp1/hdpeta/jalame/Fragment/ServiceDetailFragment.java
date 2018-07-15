package pe.tp1.hdpeta.jalame.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import pe.tp1.hdpeta.jalame.R;

public class ServiceDetailFragment extends Fragment {

    private TextView lblDriverName;
    private TextView lblDate;
    private TextView lblOrigin;
    private TextView lblDestiny;
    private TextView lblCalificacion;
    private TextView lblImporte;
    private TextView lblFormaPago;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service_detail, container, false);

        lblDriverName = (TextView) rootView.findViewById(R.id.lblDriverName);
        lblDate = (TextView) rootView.findViewById(R.id.lblDate);
        lblDestiny = (TextView) rootView.findViewById(R.id.lblDestiny);
        lblCalificacion = (TextView) rootView.findViewById(R.id.lblCalificacion);
        lblImporte = (TextView) rootView.findViewById(R.id.lblImporte);
        lblFormaPago = (TextView) rootView.findViewById(R.id.lblFormaPago);

        return rootView;
    }
}
