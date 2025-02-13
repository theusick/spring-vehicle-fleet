package com.theusick.fleet.service.mapper.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@UtilityClass
public class MappingTimeUtil {

    public static OffsetDateTime convertInstantToTimezone(Instant timestamp,
                                                          ZoneId timezone) {
        if (timestamp == null) {
            return null;
        }
        ZonedDateTime enterpriseDateTime = timestamp.atZone(timezone);
        return enterpriseDateTime.toOffsetDateTime();
    }

}
