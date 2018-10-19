package zhawenting.electroniccontroller.myservice;


import retrofit2.Call;
import retrofit2.http.GET;
import zhawenting.electroniccontroller.bean.WeatherBean;

public interface WeatherApi {

    @GET("api/location/2165352/")
    Call<WeatherBean> getWeather();

}
