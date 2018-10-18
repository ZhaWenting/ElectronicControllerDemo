package zhawenting.electroniccontroller.webservice;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import zhawenting.electroniccontroller.bean.FixtureBean;

public interface BedroomFixtureApi {

    @GET("/bedroom/light1")
    Call<FixtureBean> getLight1State();

    @POST("/bedroom/light2")
    Call<FixtureBean> getLight2State();

    @POST("/bedroom/ac")
    Call<FixtureBean> getACState();

}
