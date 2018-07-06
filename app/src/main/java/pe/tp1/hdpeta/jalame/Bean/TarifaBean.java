package pe.tp1.hdpeta.jalame.Bean;

import java.util.Date;

public class TarifaBean {
    private int codTarifa;
    private String tarifa;
    private int distanciaBase;
    private int distanciaTope;
    private double importe;
    private String estadoR;
    private Date tsupdate;

    public TarifaBean(int codTarifa, String tarifa, int distanciaBase, int distanciaTope, double importe, String estadoR, Date tsupdate) {
        this.codTarifa = codTarifa;
        this.tarifa = tarifa;
        this.distanciaBase = distanciaBase;
        this.distanciaTope = distanciaTope;
        this.importe = importe;
        this.estadoR = estadoR;
        this.tsupdate = tsupdate;
    }

    public int getCodTarifa() {
        return codTarifa;
    }

    public void setCodTarifa(int codTarifa) {
        this.codTarifa = codTarifa;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public int getDistanciaBase() {
        return distanciaBase;
    }

    public void setDistanciaBase(int distanciaBase) {
        this.distanciaBase = distanciaBase;
    }

    public int getDistanciaTope() {
        return distanciaTope;
    }

    public void setDistanciaTope(int distanciaTope) {
        this.distanciaTope = distanciaTope;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
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
