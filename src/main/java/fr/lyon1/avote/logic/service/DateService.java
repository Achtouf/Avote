package fr.lyon1.avote.logic.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateService {

    private DateService() {
    }

    public static int calculateYearPassesSince(Date birthday) {
        Date date1 = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(date1));
        int d2 = Integer.parseInt(formatter.format(birthday));
        int age = (d1 - d2) / 10000;
        return age;
    }
}
