package edu.wgu.d387_sample_code;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * B3: Converts times between eastern time (ET), mountain time (MT),
 * and coordinated universal time (UTC).
 */
public class TimeZoneConverter {

    public static final ZoneId EASTERN  = ZoneId.of("America/New_York");
    public static final ZoneId MOUNTAIN = ZoneId.of("America/Denver");
    public static final ZoneId UTC      = ZoneId.of("UTC");

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm a");

    /**
     * B3a: Converts the given time into the target time zone.
     */
    public static ZonedDateTime convert(ZonedDateTime time, ZoneId targetZone) {
        return time.withZoneSameInstant(targetZone);
    }

    /**
     * B3b: Builds the live presentation message showing the time
     * in all three time zones, in hours and minutes.
     */
    public static String getPresentationTimes(ZonedDateTime presentationTime) {
        String et  = convert(presentationTime, EASTERN).format(TIME_FORMAT);
        String mt  = convert(presentationTime, MOUNTAIN).format(TIME_FORMAT);
        String utc = convert(presentationTime, UTC).format(TIME_FORMAT);

        return "The Landon Hotel online live presentation begins at "
                + et + " ET, " + mt + " MT, and " + utc + " UTC.";
    }
}