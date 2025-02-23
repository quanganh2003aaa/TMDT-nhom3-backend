package com.example.betmdtnhom3.utils;

import com.example.betmdtnhom3.entity.Size;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
@Component
public class DateUtilsHelper {
    public LocalDateTime getNowLocalDateTime(){
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDateTime currentTime = ZonedDateTime.now(zoneId).toLocalDateTime();
        return currentTime;
    }
}
