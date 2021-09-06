package bogdanov.dailyweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@JsonIgnoreProperties({"current", "minutely", "hourly", "alerts"})
public class WeatherDTO {
    @JsonProperty(value = "lat")
    private double lat;
    @JsonProperty(value = "lon")
    private double lon;
    @JsonProperty(value = "timezone")
    private String timezone;
    @JsonProperty(value = "timezone_offset")
    private long timezoneOffset;
    @JsonProperty(value = "daily")
    List<DailyWeatherDTO> dailyWeather = new LinkedList<>();
}
