package pe.tp1.hdpeta.jalame.Bean;

import java.util.Date;

public class BloqueoBean {
    private int codPerSolicita;
    private int codPerBloquea;
    private String motivo;
    private Date fecha;

    public BloqueoBean(int codPerSolicita, int codPerBloquea, String motivo, Date fecha) {
        this.codPerSolicita = codPerSolicita;
        this.codPerBloquea = codPerBloquea;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public int getCodPerSolicita() {
        return codPerSolicita;
    }

    public void setCodPerSolicita(int codPerSolicita) {
        this.codPerSolicita = codPerSolicita;
    }

    public int getCodPerBloquea() {
        return codPerBloquea;
    }

    public void setCodPerBloquea(int codPerBloquea) {
        this.codPerBloquea = codPerBloquea;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
