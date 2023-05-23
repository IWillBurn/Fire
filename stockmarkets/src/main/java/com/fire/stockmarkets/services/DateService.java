package com.fire.stockmarkets.services;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DateService {

    public static String currentDate(){
        ZonedDateTime zone = LocalDateTime.now().atZone(ZoneId.of("UTC"));
        Integer yearInt = zone.getYear();
        Integer monthInt = zone.getMonthValue();
        Integer dayInt = zone.getDayOfMonth();
        Integer hourInt = zone.getHour();
        Integer minuteInt = zone.getMinute();
        Integer secondInt = zone.getSecond();
        String year = yearInt.toString();
        String month = monthInt.toString();
        String day = dayInt.toString();
        String hour = hourInt.toString();
        String minute = minuteInt.toString();
        String second = secondInt.toString();

        year = toStandard(year, 4);
        month = toStandard(month, 2);
        day = toStandard(day, 2);
        hour = toStandard(hour, 2);
        minute = toStandard(minute, 2);
        second = toStandard(second, 2);
        return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
    }
    public static String currentDateWithDelta(Integer delta){
        ZonedDateTime zone = LocalDateTime.now().atZone(ZoneId.of("UTC"));
        Integer yearInt = zone.getYear();
        Integer monthInt = zone.getMonthValue();
        Integer dayInt = zone.getDayOfMonth();
        Integer hourInt = zone.getHour();
        Integer minuteInt = zone.getMinute();
        Integer secondInt = zone.getSecond();
        List<Integer> result = addDeltaMonth(yearInt, monthInt, delta);
        yearInt = result.get(0);
        monthInt = result.get(1);
        String year = yearInt.toString();
        String month = monthInt.toString();
        String day = dayInt.toString();
        String hour = hourInt.toString();
        String minute = minuteInt.toString();
        String second = secondInt.toString();
        year = toStandard(year, 4);
        month = toStandard(month, 2);
        day = toStandard(day, 2);
        hour = toStandard(hour, 2);
        minute = toStandard(minute, 2);
        second = toStandard(second, 2);
        return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
    }

    public static String toUnixStandard(String input){
        if (input.length() == 10){
            return input += " 00:00:00";
        }
        return input;
    }
    private static String toStandard(String value, Integer countOfDigit){
        while (value.length() < countOfDigit){
            value = "0" + value;
        }
        return value;
    }

    private static List<Integer> addDeltaMonth(Integer year, Integer month, Integer delta){
        month-=delta;
        if (month <= 0){
            month += 12;
            year-=1;
        }
        List<Integer> result = new ArrayList<>();
        result.add(year);
        result.add(month);
        return result;
    }
}
