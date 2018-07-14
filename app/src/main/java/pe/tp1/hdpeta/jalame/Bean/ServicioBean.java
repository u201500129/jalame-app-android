package pe.tp1.hdpeta.jalame.Bean;

import android.content.ContentValues;

import java.util.Date;

public class ServicioBean {
    private int codServicio;
    private int codConductor;
    private int codUsuario;
    private int codVehiculo;
    private int codTarifa;
    private int codFormaPago;
    private String fecRegistro;
    private String inicioServ;
    private String finServ;
    private String origenDes;
    private String origenLat;
    private String origenLon;
    private String destinoDes;
    private String destinoLat;
    private String destinoLon;
    private int calificacionUsuario;
    private int calificacionConductor;
    private int calificacionVehiculo;
    private String commtUsua;
    private String commtCond;
    private String estadoServ;
    private double importe;
    private String estadoR;
    private String tsupdate;

    //Referencias
    private String usuario;
    private String conductor;
    private String vehiculo;
    private String formaPago;

    public ServicioBean(int codServicio,
                        int codConductor,
                        int codUsuario,
                        int codVehiculo,
                        int codTarifa,
                        int codFormaPago,
                        String fecRegistro,
                        String inicioServ,
                        String finServ,
                        String origenDes,
                        String origenLat,
                        String origenLon,
                        String destinoDes,
                        String destinoLat,
                        String destinoLon,
                        int calificacionUsuario,
                        int calificacionConductor,
                        int calificacionVehiculo,
                        String commtUsua,
                        String commtCond,
                        String estadoServ,
                        double importe,
                        String estadoR,
                        String tsupdate,
                        String usuario,
                        String conductor,
                        String vehiculo,
                        String formaPago) {
        this.codServicio = codServicio;
        this.codConductor = codConductor;
        this.codUsuario = codUsuario;
        this.codVehiculo = codVehiculo;
        this.codTarifa = codTarifa;
        this.codFormaPago = codFormaPago;
        this.fecRegistro = fecRegistro;
        this.inicioServ = inicioServ;
        this.finServ = finServ;
        this.origenDes = origenDes;
        this.origenLat = origenLat;
        this.origenLon = origenLon;
        this.destinoDes = destinoDes;
        this.destinoLat = destinoLat;
        this.destinoLon = destinoLon;
        this.calificacionUsuario = calificacionUsuario;
        this.calificacionConductor = calificacionConductor;
        this.calificacionVehiculo = calificacionVehiculo;
        this.commtUsua = commtUsua;
        this.commtCond = commtCond;
        this.estadoServ = estadoServ;
        this.importe = importe;
        this.estadoR = estadoR;
        this.tsupdate = tsupdate;
        this.usuario = usuario;
        this.conductor = conductor;
        this.vehiculo = vehiculo;
        this.formaPago = formaPago;
    }

    public int getCodServicio() {
        return codServicio;
    }

    public void setCodServicio(int codServicio) {
        this.codServicio = codServicio;
    }

    public int getCodConductor() {
        return codConductor;
    }

    public void setCodConductor(int codConductor) {
        this.codConductor = codConductor;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public int getCodVehiculo() {
        return codVehiculo;
    }

    public void setCodVehiculo(int codVehiculo) {
        this.codVehiculo = codVehiculo;
    }

    public int getCodTarifa() {
        return codTarifa;
    }

    public void setCodTarifa(int codTarifa) {
        this.codTarifa = codTarifa;
    }

    public int getCodFormaPago() {
        return codFormaPago;
    }

    public void setCodFormaPago(int codFormaPago) {
        this.codFormaPago = codFormaPago;
    }

    public String getFecRegistro() {
        return fecRegistro;
    }

    public void setFecRegistro(String fecRegistro) {
        this.fecRegistro = fecRegistro;
    }

    public String getInicioServ() {
        return inicioServ;
    }

    public void setInicioServ(String inicioServ) {
        this.inicioServ = inicioServ;
    }

    public String getFinServ() {
        return finServ;
    }

    public void setFinServ(String finServ) {
        this.finServ = finServ;
    }

    public String getOrigenDes() {
        return origenDes;
    }

    public void setOrigenDes(String origenDes) {
        this.origenDes = origenDes;
    }

    public String getOrigenLat() {
        return origenLat;
    }

    public void setOrigenLat(String origenLat) {
        this.origenLat = origenLat;
    }

    public String getOrigenLon() {
        return origenLon;
    }

    public void setOrigenLon(String origenLon) {
        this.origenLon = origenLon;
    }

    public String getDestinoDes() {
        return destinoDes;
    }

    public void setDestinoDes(String destinoDes) {
        this.destinoDes = destinoDes;
    }

    public String getDestinoLat() {
        return destinoLat;
    }

    public void setDestinoLat(String destinoLat) {
        this.destinoLat = destinoLat;
    }

    public String getDestinoLon() {
        return destinoLon;
    }

    public void setDestinoLon(String destinoLon) {
        this.destinoLon = destinoLon;
    }

    public int getCalificacionUsuario() {
        return calificacionUsuario;
    }

    public void setCalificacionUsuario(int calificacionUsuario) {
        this.calificacionUsuario = calificacionUsuario;
    }

    public int getCalificacionConductor() {
        return calificacionConductor;
    }

    public void setCalificacionConductor(int calificacionConductor) {
        this.calificacionConductor = calificacionConductor;
    }

    public int getCalificacionVehiculo() {
        return calificacionVehiculo;
    }

    public void setCalificacionVehiculo(int calificacionVehiculo) {
        this.calificacionVehiculo = calificacionVehiculo;
    }

    public String getCommtUsua() {
        return commtUsua;
    }

    public void setCommtUsua(String commtUsua) {
        this.commtUsua = commtUsua;
    }

    public String getCommtCond() {
        return commtCond;
    }

    public void setCommtCond(String commtCond) {
        this.commtCond = commtCond;
    }

    public String getEstadoServ() {
        return estadoServ;
    }

    public void setEstadoServ(String estadoServ) {
        this.estadoServ = estadoServ;
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

    public String getTsupdate() {
        return tsupdate;
    }

    public void setTsupdate(String tsupdate) {
        this.tsupdate = tsupdate;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put("codServicio", this.codServicio);
        values.put("codConductor", this.codConductor);
        values.put("codUsuario", this.codUsuario);
        values.put("codVehiculo", this.codVehiculo);
        values.put("codTarifa", this.codTarifa);
        values.put("codFormaPago", this.codFormaPago);
        values.put("fecRegistro", this.fecRegistro);
        values.put("inicioServ", this.inicioServ);
        values.put("finServ", this.finServ);
        values.put("origenDes", this.origenDes);
        values.put("origenLat", this.origenLat);
        values.put("origenLon", this.origenLon);
        values.put("destinoDes", this.destinoDes);
        values.put("destinoLat", this.destinoLat);
        values.put("destinoLon", this.destinoLon);
        values.put("calificacionUsuario", this.calificacionUsuario);
        values.put("calificacionConductor", this.calificacionConductor);
        values.put("calificacionVehiculo", this.calificacionVehiculo);
        values.put("commtUsua", this.commtUsua);
        values.put("conductor", this.conductor);
        values.put("vehiculo", this.vehiculo);
        values.put("formaPago", this.formaPago);
        return values;
    }
}
