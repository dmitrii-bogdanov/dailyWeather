package bogdanov.dailyweather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DailyTempDTO {

    @JsonProperty(value = "morn")
    private Double morning;
    @JsonProperty(value = "day")
    private Double day;
    @JsonProperty(value = "eve")
    private Double eve;
    @JsonProperty(value = "night")
    private Double night;

}
