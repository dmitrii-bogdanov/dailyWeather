package bogdanov.dailyweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties({"moonrise", "moonset", "moon_phase", "pressure", "humidity", "dew_point", "wind_speed",
        "wind_gust", "wind_deg", "clouds", "uvi", "pop", "rain", "snow", "weather"})
public class DailyWeatherDTO {

    @JsonIgnore
    private static final LocalDateTime EPOCH_TIME = LocalDateTime.of(
            1970,1,1,0,0,0);

    @JsonProperty(value = "dt")
    private LocalDateTime dateTime;
    @JsonProperty(value = "sunrise")
    private LocalDateTime sunrise;
    @JsonProperty(value = "sunset")
    private LocalDateTime sunset;
    @JsonProperty(value = "temp")
    private DailyTempDTO actualTemp;
    @JsonProperty(value = "feels_like")
    private DailyTempDTO feelsLikeTemp;


    public void setDateTime(Long seconds) {
        this.dateTime = setTime(seconds);
    }

    public void setSunrise(Long seconds) {
        this.sunrise = setTime(seconds);
    }

    public void setSunset(Long seconds) {
        this.sunset = setTime(seconds);
    }

    private LocalDateTime setTime(Long seconds) {
        return EPOCH_TIME.plusSeconds(seconds);
    }
}
