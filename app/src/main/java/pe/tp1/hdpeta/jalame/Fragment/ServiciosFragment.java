package pe.tp1.hdpeta.jalame.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.tp1.hdpeta.jalame.Adapter.ServicesAdapter;
import pe.tp1.hdpeta.jalame.Bean.ServicioBean;
import pe.tp1.hdpeta.jalame.R;

public class ServiciosFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<ServicioBean> services = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_servicios, container, false);

        FillServices();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.servicesRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ServicesAdapter(services);
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    public void FillServices(){
        services.add(new ServicioBean(0,
                0,
                0,
                0,
                0,
                0,
                 new Date(),
                 new Date(),
                 new Date(),
                "Belatrix",
                "-12.100002",
                "-77.019055",
                "Universidad Peruana de Ciencias Aplicadas",
                "-12.1040605",
                "-76.9650905",
                4,
                4,
                3,
                "Finalizado",
                "",
                 new Date()));
        services.add(new ServicioBean(1,
                1,
                2,
                1,
                1,
                1,
                 new Date(),
                 new Date(),
                 new Date(),
                "Belatrix",
                "-12.100002",
                "-77.019055",
                "Universidad Peruana de Ciencias Aplicadas",
                "-12.1040605",
                "-76.9650905",
                4,
                4,
                3,
                "Finalizado",
                "",
                 new Date()));
    }


}
