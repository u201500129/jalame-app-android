package pe.tp1.hdpeta.jalame.Bean;

import android.content.ContentValues;

public class PersonBean {

    private int codPersona;
    private String nombre;
    private String apellido;
    private String sexo;
    private String dni;
    private String perfil;
    private String carrera;
    private String correo;
    private String telefono;
    private int calificacion;
    private String clave;
    private String estadoR;

    public PersonBean(){}

    public PersonBean(int codPersona, String nombre, String apellido, String sexo, String dni, String perfil, String carrera, String correo, String telefono, int calificacion, String clave, String estadoR) {
        this.codPersona = codPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.dni = dni;
        this.perfil = perfil;
        this.carrera = carrera;
        this.correo = correo;
        this.telefono = telefono;
        this.calificacion = calificacion;
        this.clave = clave;
        this.estadoR = estadoR;
    }

    public int getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(int codPersona) {
        this.codPersona = codPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstadoR() {
        return estadoR;
    }

    public void setEstadoR(String estadoR) {
        this.estadoR = estadoR;
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put("codPersona", this.codPersona);
        values.put("nombre", this.nombre);
        values.put("apellido", this.apellido);
        values.put("sexo", this.sexo);
        values.put("dni", this.dni);
        values.put("perfil", this.perfil);
        values.put("carrera", this.carrera);
        values.put("correo", this.correo);
        values.put("telefono", this.telefono);
        values.put("calificacion", this.calificacion);
        values.put("clave", this.clave);
        values.put("estadoR", this.estadoR);
        return values;
    }
}
