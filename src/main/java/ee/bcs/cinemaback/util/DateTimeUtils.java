package ee.bcs.cinemaback.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    public static final ZoneId TIME_ZONE = ZoneId.of("Europe/Tallinn");

    public static Instant dateTimeToInstant(String dateTime) {
        return dateTime == null ? null : LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER)
                .atZone(TIME_ZONE)
                .toInstant();
    }
}
