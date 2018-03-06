package fr.lyon1.avote.persistence;

import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.PollType;
import fr.lyon1.avote.logic.model.entity.Restriction;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.persistence.dao.PollDAO;
import fr.lyon1.avote.persistence.dao.UserDAO;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class TestDataHelper {

    public static void addBasicUser(UserDAO userDAO) {
        User newUser = userDAO.getUserByEmail("Email@email");
        if (newUser != null) {
            userDAO.delete(newUser);
        }
        newUser = new User();
        newUser.setFirstName("First_Name");
        newUser.setLastName("Last_Name");
        newUser.setEmail("Email@email");
        newUser.setPassword("Password");
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
            newUser.setBirthday(formatter.parse("19660323"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newUser.setActivated(true);
        newUser.setDeleted(false);
        userDAO.addUser(newUser);
    }

    public static void addBasicUserWithAge(UserDAO userDAO, int age) {
        User newUser = userDAO.getUserByEmail("Emailwithage" + age + "@email");
        if (newUser != null) {
            userDAO.delete(newUser);
        }
        newUser = new User();
        newUser.setFirstName("First_Name");
        newUser.setLastName("Last_Name");
        newUser.setEmail("Emailwithage" + age + "@email");
        newUser.setPassword("Password");
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int now = Integer.parseInt(formatter.format(new Date()));
        int birthDay = now - age * 10000;
        try {
            newUser.setBirthday(formatter.parse(Integer.toString(birthDay)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userDAO.addUser(newUser);
    }

    public static void deleteBasicUser(UserDAO userDAO) {
        User basicUser = userDAO.getUserByEmail("Email@email");
        if (basicUser != null) {
            userDAO.delete(basicUser);
        }
    }

    public static void deleteBasicUserWithAgeOf(int age, UserDAO userDAO) {
        User basicUser = userDAO.getUserByEmail("Emailwithage" + age + "@email");
        if (basicUser != null) {
            userDAO.delete(basicUser);
        }
    }

    public static User getBasicUser(UserDAO userDAO) {
        User basicUser = userDAO.getUserByEmail("Email@email");
        return basicUser;
    }

    public static User getBasicUserWithAgeOf(int age, UserDAO userDAO) {
        User basicUser = userDAO.getUserByEmail("Emailwithage" + age + "@email");
        if (basicUser == null) {
            addBasicUserWithAge(userDAO, age);
            basicUser = userDAO.getUserByEmail("Emailwithage" + age + "@email");
        }
        return basicUser;
    }


    protected static Poll createTestPoll(UserDAO userDAO, String subject, boolean isRestricted, String... choices) {
        Poll poll = new Poll();
        poll.setSubject(subject);
        poll.setAnonymous(false);
        poll.setOwner(getBasicUser(userDAO));
        poll.setPollType(PollType.STANDARD);
        poll.setNumberRounds(1);
        poll.setPublished(true);
        for (String choiceLabel : choices) {
            poll.addChoice(new Choice(poll, choiceLabel));
        }
        if (isRestricted) {
            Restriction restriction = new Restriction();
            restriction.setAgeMax(25);
            restriction.setAgeMin(5);
            poll.setRestriction(restriction);
        }
        return poll;
    }

    public static void createPolls(PollDAO pollDAO, UserDAO userDAO) {
        pollDAO.addPoll(createTestPoll(userDAO, "Test poll1", true, "choice 1", "choice 2", "choice 3"));
        pollDAO.addPoll(createTestPoll(userDAO, "test poll 2", false, "linux", "unix", "windows"));
        pollDAO.addPoll(createTestPoll(userDAO, "test poll 3", false));
        Poll poll = createTestPoll(userDAO, "anonymous poll", false, "anonymous choice1", "choice 2");
        poll.setAnonymous(true);
        pollDAO.addPoll(poll);
    }

}
