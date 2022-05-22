package br.com.bff.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class DateUtil {

    private Integer randomNumber(Integer init, Integer end){
        int i = (int) ((Math.random() * (end - init)) + init);
        return i;
    }

    public LocalDateTime randomLocalDateTime(){
        Integer seconds = randomNumber(0,60);
        Integer minus = randomNumber(0,60);
        Integer hours = randomNumber(0,24);
        Integer months = randomNumber(Month.JANUARY.getValue(), Month.DECEMBER.getValue());
        Integer days = randomNumber(1, Month.of(months).maxLength());
        Integer years = randomNumber(1980, LocalDate.now().getYear());

        return LocalDateTime.of(years, months, days, hours, minus, seconds);
    }

}
