package pe.tp1.hdpeta.jalame.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.Bean.ServicioBean;
import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Jalame.bd";
    private List<ServicioBean> services = new ArrayList<>();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.services = services;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS SERVICIO (" +
                "codServicio INTEGER, " +
                "codConductor INTEGER, " +
                "codUsuario INTEGER, " +
                "codVehiculo INTEGER, " +
                "codTarifa INTEGER, " +
                "codFormaPago INTEGER, " +
                "fecRegistro VARCHAR(120), " +
                "inicioServ VARCHAR(120), " +
                "finServ VARCHAR(120), " +
                "origenDes VARCHAR(120), " +
                "origenLat VARCHAR(120), " +
                "origenLon VARCHAR(120), " +
                "destinoDes VARCHAR(120), " +
                "destinoLat VARCHAR(120), " +
                "destinoLon VARCHAR(120), " +
                "calificacionUsuario INTEGER, " +
                "calificacionConductor INTEGER, " +
                "calificacionVehiculo INTEGER, " +
                "commtUsua VARCHAR(120), " +
                "commtCond VARCHAR(120), " +
                "estadoServ VARCHAR(120),  " +
                "importe DOUBLE, " +
                "estadoR VARCHAR(120),  " +
                "tsupdate VARCHAR(120),  " +
                "usuario VARCHAR(120),  " +
                "conductor VARCHAR(120),  " +
                "vehiculo VARCHAR(120),  " +
                "formaPago VARCHAR(120)) ");

        db.execSQL("CREATE TABLE IF NOT EXISTS PERSONA (" +
                "codPersona INTEGER, " +
                "nombre VARCHAR(120), " +
                "apellido VARCHAR(120), " +
                "sexo VARCHAR(120), " +
                "dni VARCHAR(120), " +
                "perfil VARCHAR(120), " +
                "carrera VARCHAR(120), " +
                "correo VARCHAR(120), " +
                "telefono VARCHAR(120), " +
                "calificacion INTEGER, " +
                "clave VARCHAR(120), " +
                "estadoR VARCHAR(120)) ");

        db.execSQL("CREATE TABLE IF NOT EXISTS VEHICULO (" +
                "codVehiculo INTEGER, " +
                "codPersona INTEGER, " +
                "polizaSoat VARCHAR(120), " +
                "marca VARCHAR(120), " +
                "modelo VARCHAR(120), " +
                "aFabrica VARCHAR(120), " +
                "matricula VARCHAR(120), " +
                "color VARCHAR(120), " +
                "asientosTotal INTEGER, " +
                "asientosDisp INTEGER, " +
                "latitud VARCHAR(120), " +
                "longitud VARCHAR(120), " +
                "visible VARCHAR(120), " +
                "calificacion INTEGER, " +
                "estadoR VARCHAR(120), " +
                "tsupdate VARCHAR(120), " +
                "distancia INTEGER) ");

    }

    public VehiculoBean vehiculoBean(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("VEHICULO",
                null,
                null,
                null,
                null,
                null,
                null);

        return new VehiculoBean(c.getInt(0),
                c.getInt(1),
                c.getString(2),
                c.getString(3),
                c.getString(4),
                c.getString(5),
                c.getString(6),
                c.getString(7),
                c.getInt(8),
                c.getInt(9),
                c.getString(10),
                c.getString(11),
                c.getString(12),
                c.getInt(13),
                c.getString(14),
                c.getString(15),
                c.getInt(16));
    }

    public PersonBean personBean(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("PERSONA",
                null,
                null,
                null,
                null,
                null,
                null);

        List<PersonBean> personBeans = new ArrayList<>();
        while (c.moveToNext()){
            personBeans.add(new PersonBean(c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getInt(9),
                    c.getString(10),
                    c.getString(11))
            );
        }

        if (personBeans.size() > 0) {
            return personBeans.get(0);
        } else {
            return null;
        }
    }

    public void saveVehiculo(VehiculoBean vehiculoBean){
        SQLiteDatabase db = getWritableDatabase();
        db.insert("VEHICULO", null, vehiculoBean.toContentValues());
    }
    public void savePerson(PersonBean personBean){
        SQLiteDatabase db = getWritableDatabase();
        db.insert("PERSONA",null, personBean.toContentValues());
    }

    public int deletePerson(String codPersona){
        return getWritableDatabase().delete("PERSONA","codPersona=?",new String[]{codPersona});
    }

    public int deleteVehiculo(String codVehiculo){
        return getWritableDatabase().delete("VEHICULO","codVehiculo=?", new String[]{codVehiculo});
    }

    public List<ServicioBean> getAllServices(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("SERVICIO",
                null,
                null,
                null,
                null,
                null,
                "codServicio DESC");

        List<ServicioBean> services = new ArrayList<>();
        while (c.moveToNext()){
            services.add(new ServicioBean(c.getInt(0),
                    c.getInt(1),
                    c.getInt(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getString(9),
                    c.getString(10),
                    c.getString(11),
                    c.getString(12),
                    c.getString(13),
                    c.getString(14),
                    c.getInt(15),
                    c.getInt(16),
                    c.getInt(17),
                    c.getString(18),
                    c.getString(19),
                    c.getString(20),
                    c.getDouble(21),
                    c.getString(22),
                    c.getString(23),
                    c.getString(24),
                    c.getString(25),
                    c.getString(26),
                    c.getString(27)
                    ));
        }

        return services;
    }

    public void SaveServices(ServicioBean service){
        SQLiteDatabase db = getWritableDatabase();
        db.insert("SERVICIO", null, service.toContentValues());
    }

    public int deleteService(String codServicio){
        return getWritableDatabase().delete("SERVICIO","codServicio=?", new String[]{codServicio});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
