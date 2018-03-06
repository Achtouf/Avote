package fr.lyon1.avote.persistence.service;

import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.persistence.DataTestBase;
import fr.lyon1.avote.persistence.TestDataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class PollServiceTest extends DataTestBase {


    @Autowired
    private PollService pollService;
    @Autowired
    private UserService userService;

    @Test
    public void publishPoll() throws Exception {
        User user = userService.getUserByEmail("Email@email");
        Poll poll = pollService.listAllPollsCreatedBy(user).get(0);
        poll.setPublished(false);
        pollService.updatePoll(poll);
        pollService.publishPoll(user.getUserId(), poll.getPollId());
        assertEquals(true, pollService.getPollById(poll.getPollId()).isPublished());
    }

    @Test
    public void updatePoll() throws Exception {
        Poll poll = pollService.listAllPolls().get(0);

        int id = poll.getPollId();
        poll.setSubject("updated subject");

        pollService.updatePoll(poll);

        assertEquals(pollService.getPollById(id).getSubject(), "updated subject");

    }

    @Test
    public void getPollById() throws Exception {
        Poll poll = pollService.getPollById(577);
    }

    @Test
    public void listChoiceTest() throws Exception {
        List<Poll> poll = pollService.listAllPolls();
    }

    @Test
    public void getAllAvailablePollsWithRestrictionsOf() throws Exception {
        User userWithAge21 = TestDataHelper.getBasicUserWithAgeOf(21, userDAO);
        List<Poll> restrictedPollsAvailableForBasicUser = pollService.getAllAvailablePollsWithRestrictionsOf(userWithAge21);
        assertEquals(restrictedPollsAvailableForBasicUser.size(), 1);
        TestDataHelper.deleteBasicUserWithAgeOf(21, userDAO);
    }

    @Test
    public void getAllAvailablePolls() throws Exception {
        User user = TestDataHelper.getBasicUserWithAgeOf(20, userDAO);
        assertEquals(4, pollService.listAllPollsAvailableForUser(user).size());
    }

    @Test
    @Transactional
    public void getAllVotedPollsForUser() throws Exception {
        User userWithAge21 = TestDataHelper.getBasicUserWithAgeOf(21, userDAO);
        List<Poll> allPollsForUser = pollService.listAllPollsAvailableForUser(userWithAge21);
        assertEquals(allPollsForUser.size(), 4);
        TestDataHelper.deleteBasicUserWithAgeOf(21, userDAO);
    }

}