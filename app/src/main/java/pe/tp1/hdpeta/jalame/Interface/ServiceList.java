package pe.tp1.hdpeta.jalame.Interface;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import pe.tp1.hdpeta.jalame.Bean.ServicioBean;

public class ServiceList {
    @SerializedName("servicio")
    private ArrayList<ServicioBean> serviceList;

    public ArrayList<ServicioBean> getServiceArrayList(){
        return serviceList;
    }

    public void setServiceArrayList(ArrayList<ServicioBean> serviceArrayList){
        this.serviceList = serviceArrayList;

    }
}
