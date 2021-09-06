package bogdanov.dailyweather;

import bogdanov.dailyweather.dto.WeatherDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;

@RequiredArgsConstructor
public class OpenWeatherMapOrgDailyWeatherUpdater {

    private File file;
    private final double lat;
    private final double lon;
    private static final String EXCLUDE = "current,minutely,hourly,alerts";
    private static final String APP_ID = "c17015e5a65898870b092e014f43434a";
    private static final String UNITS = "metric";

    public WeatherDTO getWeather() throws IOException {
        LocalDate today = LocalDate.now();
        String fileName = String.format("%.2f", lat) + "-" + String.format("%.2f", lon) + "-" + today + ".json";
        if (file == null || !file.getName().equals(fileName)) {
            file = new File(fileName);
            URL url = new URL("https://api.openweathermap.org/data/2.5/onecall"
                    + "?lat=" + String.format("%.2f", lat)
                    + "&lon=" + String.format("%.2f", lon)
                    + "&exclude=" + EXCLUDE
                    + "&units=" + UNITS
                    + "&appid=" + APP_ID);
            read(url.openStream());
        }
        return convert();
    }

    private WeatherDTO convert() throws IOException {
        return (new ObjectMapper().readValue(file, WeatherDTO.class));
    }

    private void read(InputStream in) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(in);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        while ((bytesRead = bin.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
    }

}
