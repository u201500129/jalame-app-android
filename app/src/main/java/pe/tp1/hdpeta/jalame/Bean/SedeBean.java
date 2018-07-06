package pe.tp1.hdpeta.jalame.Bean;

import java.util.Date;

public class SedeBean {
    private int codSede;
    private String sede;
    private String latitud;
    private String longitud;
    private String direccion;
    private String estadoR;
    private Date tsupdate;

    public SedeBean(int codSede, String sede, String latitud, String longitud, String direccion, String estadoR, Date tsupdate) {
        this.codSede = codSede;
        this.sede = sede;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.estadoR = estadoR;
        this.tsupdate = tsupdate;
    }

    public int getCodSede() {
        return codSede;
    }

    public void setCodSede(int codSede) {
        this.codSede = codSede;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstadoR() {
        return estadoR;
    }

    public void setEstadoR(String estadoR) {
        this.estadoR = estadoR;
    }

    public Date getTsupdate() {
        return tsupdate;
    }

    public void setTsupdate(Date tsupdate) {
        this.tsupdate = tsupdate;
    }
}
