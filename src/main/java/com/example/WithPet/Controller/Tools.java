package com.example.WithPet.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

public class Tools {
    public boolean isUserLogined(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userLogined") != null)
            return true;
        return false;
    }

    public List<LocalDateTime> convertToLDT(String startDate, String endDate) {
        TimeZone tzSeoul = TimeZone.getTimeZone("Asia/Seoul");
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("[yyyy-MM-dd]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter()
                .withZone(tzSeoul.toZoneId());

        LocalDateTime localDateTime1 = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(tzSeoul.toZoneId())).atStartOfDay();
        LocalDateTime localDateTime2 = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(tzSeoul.toZoneId())).atTime(LocalTime.MAX);
        List<LocalDateTime> list = new ArrayList<>();
        list.add(localDateTime1);
        list.add(localDateTime2);

        return list;
    }

    public LocalDateTime getLDTnow() {
        return LocalDateTime.now();
    }

}

