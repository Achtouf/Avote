package fr.lyon1.avote.logic.service;

import fr.lyon1.avote.logic.model.entity.User;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class DateServiceTest {

    @Test
    public void calculateYearPassesSince() throws Exception {
        User user = new User();
        user.setFirstName("Robert");
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        user.setBirthday(formatter.parse("19920531"));
        // needs to be updated every year
        assertEquals(25, DateService.calculateYearPassesSince(user.getBirthday()));
    }


}