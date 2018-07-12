package pe.tp1.hdpeta.jalame.Bean;

import java.util.Date;

public class ServicioBean {
    private int codServicio;
    private int codConductor;
    private int codUsuario;
    private int codVehiculo;
    private int codTarifa;
    private int codFormaPago;
    private Date fecRegistro;
    private Date inicioServ;
    private Date finServ;
    private String origenDes;
    private String origenLat;
    private String origenLon;
    private String destinoDes;
    private String destinoLat;
    private String destinoLon;
    private int calificacionUsuario;
    private int calificacionConductor;
    private int calificacionVehiculo;
    private String estadoServ;
    private String estadoR;
    private Date tsupdate;

    public ServicioBean(int codServicio,
                        int codConductor,
                        int codUsuario,
                        int codVehiculo,
                        int codTarifa,
                        int codFormaPago,
                        Date fecRegistro,
                        Date inicioServ,
                        Date finServ,
                        String origenDes,
                        String origenLat,
                        String origenLon,
                        String destinoDes,
                        String destinoLat,
                        String destinoLon,
                        int calificacionUsuario,
                        int calificacionConductor,
                        int calificacionVehiculo,
                        String estadoServ,
                        String estadoR,
                        Date tsupdate) {
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
        this.estadoServ = estadoServ;
        this.estadoR = estadoR;
        this.tsupdate = tsupdate;
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

    public Date getFecRegistro() {
        return fecRegistro;
    }

    public void setFecRegistro(Date fecRegistro) {
        this.fecRegistro = fecRegistro;
    }

    public Date getInicioServ() {
        return inicioServ;
    }

    public void setInicioServ(Date inicioServ) {
        this.inicioServ = inicioServ;
    }

    public Date getFinServ() {
        return finServ;
    }

    public void setFinServ(Date finServ) {
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

    public String getEstadoServ() {
        return estadoServ;
    }

    public void setEstadoServ(String estadoServ) {
        this.estadoServ = estadoServ;
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
