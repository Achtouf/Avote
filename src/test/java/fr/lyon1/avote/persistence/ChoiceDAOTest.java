package fr.lyon1.avote.persistence;

import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.persistence.dao.ChoiceDAO;
import fr.lyon1.avote.persistence.dao.PollDAO;
import fr.lyon1.avote.persistence.dao.UserDAO;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class ChoiceDAOTest extends DataTestBase {

    @Autowired
    SessionFactory sessionFactory;

    List<Choice> choiceList = new ArrayList<>();
    Poll poll = new Poll();

    @Before
    public void init(){
        Choice choice = new Choice();
        choice.setLabel("choix1");
        choiceList.add(choice);
    }

    @Test
    public void addChoicesForPollTest(){
        TestDataHelper.addBasicUserWithAge(userDAO, 30);
        poll = TestDataHelper.createTestPoll(userDAO, "choice1", false, "choix1");
        poll.setOwner(TestDataHelper.getBasicUserWithAgeOf(30, userDAO));
        pollDAO.addPoll(poll);
        choiceDAO.addChoicesForPoll(poll, choiceList);
        assertEquals(choiceList.get(0).getPoll(), poll);
    }
}
