package pe.tp1.hdpeta.jalame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.tp1.hdpeta.jalame.adapter.VehiculoAdapter;
import pe.tp1.hdpeta.jalame.model.Vehiculo;

public class VistaVehiculos extends AppCompatActivity {
    private RecyclerView recicladorVehiculo;
    private List<Vehiculo> items = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_vehiculos);

        FillVehiculo();

        recicladorVehiculo= (RecyclerView) findViewById(R.id.recicladorVehiculos);
        recicladorVehiculo.setHasFixedSize(true);

        lManager=new LinearLayoutManager(this);
        recicladorVehiculo.setLayoutManager(lManager);

        adapter=new VehiculoAdapter(items);
        recicladorVehiculo.setAdapter(adapter);
    }

    private void FillVehiculo() {
        items.add(new Vehiculo("ASD123","Poliza1","Ford",
                "Eco Sport 4x4","Morado",5,R.drawable.icon_vehiculo01,
                true,2));
        items.add(new Vehiculo("qwe456","Poliza2","Ford",
                "Mustang","Gris",5,R.drawable.icon_vehiculo01,
                true,4));
        items.add(new Vehiculo("poi098","Poliza3","Toyota",
                "Yaris","Amarillo",4,R.drawable.icon_vehiculo01,
                true,1));
        items.add(new Vehiculo("mnb789","Poliza4","Chevrolet",
                "Spark","Celeste",3,R.drawable.icon_vehiculo01,
                true,7));
    }
}
