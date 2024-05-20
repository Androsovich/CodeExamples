package org.androsovich.util;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DurationUtil {

    private DurationUtil() {
    }

    public static String humanReadableFormat(Duration duration) {
        return Objects.isNull(duration) ? null : String.format("%s days and %sh %sm %ss", duration.toDays(),
                duration.toHours() - TimeUnit.DAYS.toHours(duration.toDays()),
                duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours()),
                duration.getSeconds() - TimeUnit.MINUTES.toSeconds(duration.toMinutes()));
    }
}
