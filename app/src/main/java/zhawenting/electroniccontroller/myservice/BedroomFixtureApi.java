package zhawenting.electroniccontroller.myservice;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import zhawenting.electroniccontroller.entity.FixtureEntity;

public interface BedroomFixtureApi {

    @GET("/bedroom/light1")
    Call<FixtureEntity> getLight1State();

    @POST("/bedroom/light2")
    Call<FixtureEntity> getLight2State();

    @POST("/bedroom/ac")
    Call<FixtureEntity> getACState();

}
