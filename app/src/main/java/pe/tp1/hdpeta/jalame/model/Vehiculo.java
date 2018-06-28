package pe.tp1.hdpeta.jalame.model;

public class Vehiculo {
    private String placa; // columna en codVehiculo
    private String polizaSoat;
    private String marca;
    private String modelo;
    private String color;
    private int nAsientos;
    private int idFoto;
    private boolean vEstado;
    private int idConductor;

    public Vehiculo(String codVehiculo, String polizaSoat, String marca, String modelo, String color, int nAsientos, int idFoto, boolean vEstado, int idConductor) {
        this.placa = placa;
        this.polizaSoat = polizaSoat;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.nAsientos = nAsientos;
        this.idFoto = idFoto;
        this.vEstado = vEstado;
        this.idConductor = idConductor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String codVehiculo) {
        this.placa = placa;
    }

    public String getPolizaSoat() {
        return polizaSoat;
    }

    public void setPolizaSoat(String polizaSoat) {
        this.polizaSoat = polizaSoat;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getnAsientos() {
        return nAsientos;
    }

    public void setnAsientos(int nAsientos) {
        this.nAsientos = nAsientos;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public boolean isvEstado() {
        return vEstado;
    }

    public void setvEstado(boolean vEstado) {
        this.vEstado = vEstado;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }
}
