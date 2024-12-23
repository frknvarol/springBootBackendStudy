package com.dreamgames.backendengineeringcasestudy.Config;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class EventConfig {
    public static final LocalTime EVENT_START_TIME = LocalTime.of(8, 0); // 08:00 UTC
    public static final LocalTime EVENT_END_TIME = LocalTime.of(22, 0);  // 22:00 UTC
    public static final ZoneOffset EVENT_ZONE = ZoneOffset.UTC;
}
