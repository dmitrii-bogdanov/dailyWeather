package bogdanov.dailyweather;

import bogdanov.dailyweather.dto.WeatherDTO;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor
public class OpenWeatherMapOrgDailyWeatherReader implements DailyWeatherReader {

    private WeatherDTO weather;
    private Double lat, lon;
    private OpenWeatherMapOrgDailyWeatherUpdater updater;

    @Override
    public DailyWeatherReader setLocation(double lat, double lon) throws IOException{
        if ( lat < -180 || lat > 180 || lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Coordinates should belong to [-180, 180] range");
        }
        this.lat = lat;
        this.lon = lon;
        updater = new OpenWeatherMapOrgDailyWeatherUpdater(lat, lon);
        update();
        return this;
    }

    @Override
    public DailyWeatherReader update() throws IOException{
        if (updater == null) {
            throw new IllegalArgumentException("Location coordinates should be set first");
        }
            weather = updater.getWeather();
            return this;
    }

    @Override
    public Map<LocalDate, LocalDateTime> getSunriseTime() {
        Map<LocalDate, LocalDateTime> tmp = new TreeMap<>();
        weather.getDailyWeather().forEach(dw -> tmp.put(dw.getDateTime().toLocalDate(), dw.getSunrise()));
        return tmp;
    }

    @Override
    public Map<LocalDate, LocalDateTime> getSunsetTime() {
        Map<LocalDate, LocalDateTime> tmp = new TreeMap<>();
        weather.getDailyWeather().forEach(dw -> tmp.put(dw.getDateTime().toLocalDate(), dw.getSunset()));
        return tmp;
    }

    @Override
    public Map<LocalDate, Double> getActualTempAtNight() {
        Map<LocalDate, Double> tmp = new TreeMap<>();
        weather.getDailyWeather()
                .forEach(dw -> tmp.put(dw.getDateTime().toLocalDate(), dw.getActualTemp().getNight()));
        return tmp;
    }

    @Override
    public Map<LocalDate, Double> getFeelsLikeTempAtNight() {
        Map<LocalDate, Double> tmp = new TreeMap<>();
        weather.getDailyWeather()
                .forEach(dw -> tmp.put(dw.getDateTime().toLocalDate(), dw.getFeelsLikeTemp().getNight()));
        return tmp;
    }
}
