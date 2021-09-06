package bogdanov.dailyweather;


//"https://api.openweathermap.org/data/2.5/onecall
//        ?lat=59.57&lon=30.19
//        &exclude=current,minutely,hourly,alerts
//        &units=metric
//        &appid=c17015e5a65898870b092e014f43434a"

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Comparator;
import java.util.Map;

public class main {

    public static void main(String[] args) {
        DailyWeatherReader reader = new OpenWeatherMapOrgDailyWeatherReader();
        try {
            reader.setLocation(59.57, 30.19);
            Map<LocalDate, LocalDateTime> sunrise, sunset;
            Map<LocalDate, Double> actual, feelsLike;
            sunrise = reader.getSunriseTime();
            sunset = reader.getSunsetTime();
            actual = reader.getActualTempAtNight();
            feelsLike = reader.getFeelsLikeTempAtNight();

            for (LocalDate d : sunrise.keySet()) {
                System.out.println(d + " : " + sunrise.get(d) + " : " + sunset.get(d));
                System.out.println(d + " : " + Duration.between(sunrise.get(d), sunset.get(d)));
            }
            for (LocalDate d : actual.keySet()) {
                System.out.println(d + " : " + actual.get(d) + " : " + feelsLike.get(d));
                System.out.println(d + " : " + Math.abs(actual.get(d) - feelsLike.get(d)));
            }

            System.out.println("\n\n");

            Comparator<LocalDate> tempComparator = new Comparator<LocalDate>() {
                @Override
                public int compare(LocalDate d1, LocalDate d2) {
                    double diff1 = Math.abs(actual.get(d1) - feelsLike.get(d1));
                    double diff2 = Math.abs(actual.get(d2) - feelsLike.get(d2));
                    if (diff1 < diff2) {
                        return -1;
                    }
                    if (diff1 > diff2) {
                        return 1;
                    }
                    return 0;
                }
            };

            Comparator<LocalDate> sunTimeComparator = new Comparator<LocalDate>() {
                @Override
                public int compare(LocalDate d1, LocalDate d2) {
                    Duration duration1 = Duration.between(sunrise.get(d1), sunset.get(d1));
                    Duration duration2 = Duration.between(sunrise.get(d2), sunset.get(d2));
                    return -duration1.compareTo(duration2);
                }
            };

            LocalDate minTempDiffDate =
                    actual.keySet()
                            .stream()
                            .sorted(tempComparator)
                            .findFirst().get();

            System.out.println("Minimal difference between actual and feels like temperatures");
            System.out.printf("%s : %.2f C\n",
                    minTempDiffDate,
                    Math.abs(actual.get(minTempDiffDate) - feelsLike.get(minTempDiffDate))
            );

            LocalDate maxSunTimeDate =
                    sunrise.keySet()
                            .stream()
                            .filter(date -> date.compareTo(LocalDate.now().plusDays(5)) < 0)
                            .sorted(sunTimeComparator)
                            .findFirst().get();

            System.out.println("Maximum sun time for next 5 days");
            System.out.printf("%s : %s\n",
                    maxSunTimeDate,
                    Duration.between(sunrise.get(maxSunTimeDate), sunset.get(maxSunTimeDate))
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
