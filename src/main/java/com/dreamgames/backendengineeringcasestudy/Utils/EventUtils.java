package com.dreamgames.backendengineeringcasestudy.Utils;
import com.dreamgames.backendengineeringcasestudy.Config.EventConfig;

import java.time.LocalTime;
import java.time.ZonedDateTime;

public class EventUtils {
    public static boolean isEventActive() {
        LocalTime now = ZonedDateTime.now(EventConfig.EVENT_ZONE).toLocalTime();
        return !now.isBefore(EventConfig.EVENT_START_TIME) && now.isBefore(EventConfig.EVENT_END_TIME);
    }
}
