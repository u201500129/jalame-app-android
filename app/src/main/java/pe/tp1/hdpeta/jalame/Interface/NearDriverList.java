package pe.tp1.hdpeta.jalame.Interface;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import pe.tp1.hdpeta.jalame.Bean.ServicioBean;
import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;

public class NearDriverList {
    @SerializedName("vehiculo")
    private ArrayList<VehiculoBean> drivers;

    public ArrayList<VehiculoBean> getVehiculoArrayList(){
        return this.drivers;
    }

    public void setServiceArrayList(ArrayList<VehiculoBean> drivers){
        this.drivers = drivers;

    }
}

