package zhawenting.electroniccontroller.entity;

import java.util.List;

/**
 * @author Zhawenting(Ada)
 * @description
 * @package zhawenting.electroniccontroller.bean
 * @date 2018/10/18 15:13
 */
public class WeatherEntity {


    private String time;
    private String sun_rise;
    private String sun_set;
    private String timezone_name;
    private ParentBean parent;
    private String title;
    private String location_type;
    private int woeid;
    private String latt_long;
    private String timezone;

    private List<ConsolidatedWeatherBean> consolidated_weather;
    private List<SourcesBean> sources;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSun_rise() {
        return sun_rise;
    }

    public void setSun_rise(String sun_rise) {
        this.sun_rise = sun_rise;
    }

    public String getSun_set() {
        return sun_set;
    }

    public void setSun_set(String sun_set) {
        this.sun_set = sun_set;
    }

    public String getTimezone_name() {
        return timezone_name;
    }

    public void setTimezone_name(String timezone_name) {
        this.timezone_name = timezone_name;
    }

    public ParentBean getParent() {
        return parent;
    }

    public void setParent(ParentBean parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public int getWoeid() {
        return woeid;
    }

    public void setWoeid(int woeid) {
        this.woeid = woeid;
    }

    public String getLatt_long() {
        return latt_long;
    }

    public void setLatt_long(String latt_long) {
        this.latt_long = latt_long;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<ConsolidatedWeatherBean> getConsolidated_weather() {
        return consolidated_weather;
    }

    public void setConsolidated_weather(List<ConsolidatedWeatherBean> consolidated_weather) {
        this.consolidated_weather = consolidated_weather;
    }

    public List<SourcesBean> getSources() {
        return sources;
    }

    public void setSources(List<SourcesBean> sources) {
        this.sources = sources;
    }

    public static class ParentBean {
        private String title;
        private String location_type;
        private int woeid;
        private String latt_long;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLocation_type() {
            return location_type;
        }

        public void setLocation_type(String location_type) {
            this.location_type = location_type;
        }

        public int getWoeid() {
            return woeid;
        }

        public void setWoeid(int woeid) {
            this.woeid = woeid;
        }

        public String getLatt_long() {
            return latt_long;
        }

        public void setLatt_long(String latt_long) {
            this.latt_long = latt_long;
        }
    }

    public static class ConsolidatedWeatherBean {
        private long id;
        private String weather_state_name;
        private String weather_state_abbr;
        private String wind_direction_compass;
        private String created;
        private String applicable_date;
        private double min_temp;
        private double max_temp;
        private double the_temp;
        private double wind_speed;
        private double wind_direction;
        private double air_pressure;
        private int humidity;
        private double visibility;
        private int predictability;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getWeather_state_name() {
            return weather_state_name;
        }

        public void setWeather_state_name(String weather_state_name) {
            this.weather_state_name = weather_state_name;
        }

        public String getWeather_state_abbr() {
            return weather_state_abbr;
        }

        public void setWeather_state_abbr(String weather_state_abbr) {
            this.weather_state_abbr = weather_state_abbr;
        }

        public String getWind_direction_compass() {
            return wind_direction_compass;
        }

        public void setWind_direction_compass(String wind_direction_compass) {
            this.wind_direction_compass = wind_direction_compass;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getApplicable_date() {
            return applicable_date;
        }

        public void setApplicable_date(String applicable_date) {
            this.applicable_date = applicable_date;
        }

        public double getMin_temp() {
            return min_temp;
        }

        public void setMin_temp(double min_temp) {
            this.min_temp = min_temp;
        }

        public double getMax_temp() {
            return max_temp;
        }

        public void setMax_temp(double max_temp) {
            this.max_temp = max_temp;
        }

        public double getThe_temp() {
            return the_temp;
        }

        public void setThe_temp(double the_temp) {
            this.the_temp = the_temp;
        }

        public double getWind_speed() {
            return wind_speed;
        }

        public void setWind_speed(double wind_speed) {
            this.wind_speed = wind_speed;
        }

        public double getWind_direction() {
            return wind_direction;
        }

        public void setWind_direction(double wind_direction) {
            this.wind_direction = wind_direction;
        }

        public double getAir_pressure() {
            return air_pressure;
        }

        public void setAir_pressure(double air_pressure) {
            this.air_pressure = air_pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public double getVisibility() {
            return visibility;
        }

        public void setVisibility(double visibility) {
            this.visibility = visibility;
        }

        public int getPredictability() {
            return predictability;
        }

        public void setPredictability(int predictability) {
            this.predictability = predictability;
        }
    }

    public static class SourcesBean {
        private String title;
        private String slug;
        private String url;
        private int crawl_rate;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getCrawl_rate() {
            return crawl_rate;
        }

        public void setCrawl_rate(int crawl_rate) {
            this.crawl_rate = crawl_rate;
        }
    }
}
