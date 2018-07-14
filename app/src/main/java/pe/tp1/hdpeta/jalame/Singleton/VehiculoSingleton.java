package pe.tp1.hdpeta.jalame.Singleton;

import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;

public class VehiculoSingleton {
    private static VehiculoSingleton ourInstance;
    private VehiculoBean vehiculoBean;
    public static VehiculoSingleton getInstance() {
        if (ourInstance == null){
            ourInstance = new VehiculoSingleton();
        }
        return ourInstance;
    }

    private VehiculoSingleton() {
    }

    public VehiculoBean getVehiculoBean(){
        return vehiculoBean;
    }
    public void setVehiculoBean(VehiculoBean vehiculoBean){
        this.vehiculoBean = vehiculoBean;
    }
}
