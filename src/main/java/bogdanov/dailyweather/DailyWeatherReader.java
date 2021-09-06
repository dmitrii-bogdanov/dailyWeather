package bogdanov.dailyweather;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public interface DailyWeatherReader {

    DailyWeatherReader setLocation(double lat, double lon) throws IOException;

    DailyWeatherReader update() throws IOException;

    Map<LocalDate, LocalDateTime> getSunriseTime();

    Map<LocalDate, LocalDateTime> getSunsetTime();

    Map<LocalDate, Double> getActualTempAtNight();

    Map<LocalDate, Double> getFeelsLikeTempAtNight();

}
