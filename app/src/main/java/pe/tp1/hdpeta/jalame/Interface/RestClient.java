package pe.tp1.hdpeta.jalame.Interface;

import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestClient {
    @GET("/jalame/vehiculo/list/{codUsuario}/{latitud}/{longitud}")
    Call<VehiculoBean> getData(
            @Path("codUsuario") String codUsuario,
            @Path("latitud") String latitud,
            @Path("longitud") String longitud
    );
}