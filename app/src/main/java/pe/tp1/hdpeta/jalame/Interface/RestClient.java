package pe.tp1.hdpeta.jalame.Interface;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestClient {
    @GET("/jalame/person/login/{userEmail}/{password}/")
    Call<PersonBean> credentials(
            @Path("userEmail") String userEmail,
            @Path("password") String password
    );
}
