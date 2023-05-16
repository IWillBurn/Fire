package com.fire.stockmarkets.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DateService {
    public static String currentDate(Integer delta){
        Integer yearInt = LocalDateTime.now().getYear();
        Integer monthInt = LocalDateTime.now().getMonthValue();
        Integer dayInt = LocalDateTime.now().getDayOfMonth();
        List<Integer> result = addDeltaMonth(yearInt, monthInt, delta);
        yearInt = result.get(0);
        monthInt = result.get(1);
        String year = yearInt.toString();
        String month = monthInt.toString();
        String day = dayInt.toString();
        year = toStandard(year, 4);
        month = toStandard(month, 2);
        day = toStandard(day, 2);
        return year+"-"+month+"-"+day;
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
