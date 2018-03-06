package fr.lyon1.avote.persistence;

import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.persistence.dao.PollDAO;
import fr.lyon1.avote.persistence.dao.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class PollDAOTest extends DataTestBase {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PollDAO pollDAO;
    @Autowired
    private UserDAO userDAO;

    @Test
    public void addPoll() throws Exception {
        Session session = sessionFactory.openSession();
        String testSubject = "adding new poll";
        pollDAO.addPoll(TestDataHelper.createTestPoll(userDAO, testSubject, false, "yes", "no"));
        Query q = session.createQuery("SELECT poll from Poll poll where subject = :topic", Poll.class);
        q.setParameter("topic", testSubject);
        Poll poll = (Poll) (q.getResultList()).get(0);
        assertEquals(poll.getSubject(), testSubject);
        session.close();
    }


    @Test
    @Transactional
    public void getAllPollWihtoutRestrictions() throws Exception {
        List<Poll> allPollsWithoutRestrictions = pollDAO.getAllPollWihtoutRestrictions();
        assertEquals(3, allPollsWithoutRestrictions.size());
    }


    @Test
    public void getPollBySubject() throws Exception {

        List<Poll> poll = pollDAO.getPollBySubject("Test poll");
        if (!poll.isEmpty()) {
            assertEquals(((ArrayList<Choice>) poll.get(0).getChoices()).get(0).getLabel(), "Choice1");
        }

    }

}
