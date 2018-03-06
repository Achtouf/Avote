package fr.lyon1.avote.persistence;

import fr.lyon1.avote.filters.Md5;
import fr.lyon1.avote.logic.model.entity.*;
import fr.lyon1.avote.logic.model.exception.InvalidPollException;
import fr.lyon1.avote.persistence.service.PollService;
import fr.lyon1.avote.persistence.service.UserService;
import fr.lyon1.avote.persistence.service.VoteService;
import org.apache.commons.lang.RandomStringUtils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class DataGeneration {
    @Autowired
    private PollService pollService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteService voteService;

    private int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    private Date randomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2016);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return new Date(gc.getTime().getTime());
    }

    private User user_generation(){

        DataFactory dataFactory = new DataFactory();
        User user = new User();
        String password = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";

        // User configuration
        user.setFirstName(dataFactory.getFirstName());
        user.setLastName(dataFactory.getLastName());
        user.setPassword(( new Md5(RandomStringUtils.random( 15, password))).getWord());
        user.setPhoneNumber(dataFactory.getNumberText(10));
        user.setBirthday(randomDate());
        user.setEmail(dataFactory.getRandomWord(5, 15)+"."+dataFactory.getRandomWord(5,15)+"@"+dataFactory.getBusinessName()+"."+dataFactory.getRandomChars(2, 2));
        user.setActivated(true);
        user.setCreatedAt(randomDate());
        user.setDeleted(false);

        return user;
    }

    private Poll poll_generation(User owner, Restriction restriction) {
        Random random = new Random();
        DataFactory dataFactory = new DataFactory();
        Poll poll = new Poll();

        poll.setPublished(true);
        poll.setAnonymous(random.nextBoolean());
        poll.setNumberRounds(1);
        poll.setPollType(PollType.STANDARD);
        poll.setCreatedAt(randomDate());
        poll.setPublishedAt(randomDate());
        poll.setOwner(owner);
        poll.setRestriction(restriction);

        if(random.nextBoolean()) {
            poll.setClosed(true);
            poll.setClosedAt(randomDate());
        } else {
            poll.setClosed(false);
        }

        String subject = "";
        for(int i = 0 ; i < randBetween(5, 15) ; ++i) subject = subject + " " + dataFactory.getRandomWord();
        subject = subject + " ?";
        poll.setSubject(subject);

        return poll;
    }

    private Restriction restriction_generation(){

        Random random = new Random();
        DataFactory dataFactory = new DataFactory();
        Restriction restriction = new Restriction();

        if(random.nextBoolean()){
            restriction.setCity(dataFactory.getCity());
        }

        int min = randBetween(0, 30);
        int max = randBetween(min, 80);

        if(random.nextBoolean()){
            restriction.setAgeMin(randBetween(min, max + 1));
            restriction.setAgeMax(randBetween(restriction.getAgeMin(), max + 1));
        }

        return restriction;
    }

    private ArrayList<Choice> choice_generation(){
        DataFactory dataFactory = new DataFactory();
        ArrayList<Choice> choices = new ArrayList<>();

        String[] colors = {"blue", "blue-azure", "green", "dark-green", "yellow", "dark-yellow", "orange",
                "dark-orange", "dark-red", "red", "pink", "dark-pink", "purple", "dark-purple",
                "dark-blue", "dark-black"};

        for(int i = 0 ; i < randBetween(2, 10) ; ++i) {
            Choice choice = new Choice();
            choice.setColor(dataFactory.getItem(colors));
            choice.setLabel(dataFactory.getRandomWord());
            choices.add(choice);
        }

        return choices;
    }

    public void generate_admin_user(){
        User user = new User();
        user.setFirstName("a");
        user.setLastName("a");
        user.setPassword("0cc175b9c0f1b6a831c399e269772661");
        user.setActivated(true);
        user.setDeleted(false);

        user.setEmail("a@a");
        user.setBirthday(randomDate());
        userService.addUser(user);
    }

    @Before
    public void generateData() {

        int NB_USER = 20;
        int NB_VOTE = 100;

        pollService.deleteAllPolls();
        userService.deleteAll();

        User user;
        Poll poll;

        ArrayList<Choice> choice;

        //Init du premier user
        generate_admin_user();

        //Generation des user et d'un poll par user
        for(int i = 0 ; i < NB_USER ; ++i) {

            // On genère et on ajoute un user
            user = user_generation();
            userService.addUser(user);

            //restriction = restriction_generation();
            //restrictionDAO.addRestriction(restriction);

            // On génère un poll associer au user
            if(randBetween(0, 10) == 8){
                poll = poll_generation(user, restriction_generation());
            } else {
                poll = poll_generation(user, null);
            }

            // On genère les choix
            choice = choice_generation();

            for(Choice choices : choice) {
                poll.addChoice(choices);
            }

            try {
                pollService.addPoll(poll);
            } catch (InvalidPollException e) {
                e.printStackTrace();
            }
        }

        int nbVote = 0;

        User userVote;
        Choice choiceVote;
        List<User> userList;
        List<Poll> pollList;

        userList  = userService.getAllUsersWithVotes();

        int select_poll;
        int select_choice;
        while(nbVote < NB_VOTE) {

            select_poll = randBetween(0, userList.size()-1);
            // On selectionne un user au hasard
            userVote = userList.get(select_poll);

            // On selectionne tout les pol en rapport avec le user
            pollList = pollService.listAllPollsAvailableForUser(userVote);

            select_poll = randBetween(0, pollList.size()-1);
            select_choice = randBetween(0, pollList.get(select_poll).getChoices().size()-1);

            choiceVote = pollList.get(select_poll).getChoices().get(randBetween(0, select_choice));
            voteService.saveUsersVoteForPoll(userVote.getUserId(), choiceVote.getChoiceId());

            System.out.println("Generation :" + nbVote + "/" + NB_VOTE);
            nbVote++ ;
        }
    }

    @After
    public void closeConnection() {

    }

    @Test
    public void test(){

    }
}

