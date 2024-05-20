package org.androsovich.constants;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String DATE_FORMAT = "dd.MM.yy";
    public static final String TIME_FORMAT = "H:mm";
    public static final String JSON_TICKETS_HEADER = "tickets";

    public static final String VLADIVOSTOK = "VVO";
    public static final String TEL_AVIV = "TLV";

    public static final Map<String, ZoneId> CITY_ZONE_ID;

    static {
        CITY_ZONE_ID = new HashMap<>();
        CITY_ZONE_ID.put("VVO", ZoneId.of("Asia/Vladivostok"));
        CITY_ZONE_ID.put("TLV", ZoneId.of("Asia/Tel_Aviv"));
        CITY_ZONE_ID.put("LRN", ZoneId.of("EET"));
        CITY_ZONE_ID.put("UFA", ZoneId.of("Asia/Yekaterinburg"));
    }

    private Constants() {
    }
}
