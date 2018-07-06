package pe.tp1.hdpeta.jalame.Bean;

public class VehiculoBean {
    private int codVehiculo;
    private int codPersona;
    private String polizaSoat;
    private String marca;
    private String modelo;
    private String aFabrica;
    private String matricula;
    private String color;
    private int asientosTotal;
    private int asientosDisp;
    private String latitud;
    private String longitud;
    private String visible;
    private int calificacion;
    private String estadoR;
    private int foto;
    private String tsupdate;
    private int distancia;

    public VehiculoBean(int codVehiculo, int codPersona, String polizaSoat, String marca, String modelo,
                        String aFabrica, String matricula, String color, int asientosTotal,
                        int asientosDisp, String latitud, String longitud, String visible, int calificacion,
                        String estadoR, int foto, String tsupdate, int distancia) {
        this.codVehiculo = codVehiculo;
        this.codPersona = codPersona;
        this.polizaSoat = polizaSoat;
        this.marca = marca;
        this.modelo = modelo;
        this.aFabrica = aFabrica;
        this.matricula = matricula;
        this.color = color;
        this.asientosTotal = asientosTotal;
        this.asientosDisp = asientosDisp;
        this.latitud = latitud;
        this.longitud = longitud;
        this.visible = visible;
        this.calificacion = calificacion;
        this.estadoR = estadoR;
        this.foto = foto;
        this.tsupdate = tsupdate;
        this.distancia = distancia;
    }

    public int getCodVehiculo() {
        return codVehiculo;
    }

    public void setCodVehiculo(int codVehiculo) {
        this.codVehiculo = codVehiculo;
    }

    public int getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(int codPersona) {
        this.codPersona = codPersona;
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

    public String getaFabrica() {
        return aFabrica;
    }

    public void setaFabrica(String aFabrica) {
        this.aFabrica = aFabrica;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAsientosTotal() {
        return asientosTotal;
    }

    public void setAsientosTotal(int asientosTotal) {
        this.asientosTotal = asientosTotal;
    }

    public int getAsientosDisp() {
        return asientosDisp;
    }

    public void setAsientosDisp(int asientosDisp) {
        this.asientosDisp = asientosDisp;
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

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getEstadoR() {
        return estadoR;
    }

    public void setEstadoR(String estadoR) {
        this.estadoR = estadoR;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getTsupdate() {
        return tsupdate;
    }

    public void setTsupdate(String tsupdate) {
        this.tsupdate = tsupdate;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
