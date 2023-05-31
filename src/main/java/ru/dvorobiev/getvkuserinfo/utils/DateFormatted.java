package ru.dvorobiev.getvkuserinfo.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@UtilityClass
@Slf4j
public class DateFormatted {

    public static String getDateString(Date date) {
        String dateStr;
        String pattern;

        try{
            SimpleDateFormat simpleDateFormat;
            Locale locale = new Locale("ru", "RU");
            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
            dateFormatSymbols.setWeekdays(
                    new String[]{
                            "Не используется",
                            "Понедельник",
                            "Вторник",
                            "Среда",
                            "Четверг",
                            "Пятница",
                            "Суббота",
                            "Воскресенье"
                    });

            pattern = "dd/MM/yyyy";
            simpleDateFormat = new SimpleDateFormat(pattern, dateFormatSymbols);
            dateStr = simpleDateFormat.format(date);
            return dateStr;
        } catch (Exception e){
            return new String("01/01/1900");
        }
    }

    public Date perfomDate(String value) throws ParseException {
        try {
            value=(value == null)? "01.01.1900":value;
            Date date=new SimpleDateFormat("d.MM.yyyy").parse(value);
            return date;
        } catch (Exception e) {
            log.error("Error perfom data {}",e.getMessage());
            return null;
        }
    }
}
