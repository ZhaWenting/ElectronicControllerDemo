package zhawenting.electroniccontroller.webservice;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import zhawenting.electroniccontroller.bean.FixtureBean;
import zhawenting.electroniccontroller.bean.WeatherBean;

public interface WeatherApi {

    @GET("api/location/2165352/")
    Call<WeatherBean> getWeather();

}
