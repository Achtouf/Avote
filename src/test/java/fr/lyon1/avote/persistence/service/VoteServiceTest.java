package fr.lyon1.avote.persistence.service;

import fr.lyon1.avote.logic.model.entity.Choice;
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

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class VoteServiceTest extends DataTestBase {

    @Autowired
    private VoteService voteService;
    @Autowired
    private PollService pollService;

    @Test
    public void saveUsersVoteForPoll() throws Exception {
        List<Poll> polls = pollService.listAllPolls();
        Choice choice = polls.get(0).getChoices().get(1);
        User user = TestDataHelper.getBasicUserWithAgeOf(21, userDAO);
        int choiceId = choice.getChoiceId();
        int userId = user.getUserId();
        voteService.saveUsersVoteForPoll(userId, choiceId);
        assertEquals(true, voteService.isVotedForPoll(user, choice.getPoll()));
    }

    @Test
    public void testUserVoteForChoice() throws Exception {
        User user = TestDataHelper.getBasicUserWithAgeOf(20, userDAO);
        List<Poll> polls = pollService.listAllPollsAvailableForUser(user);
        Poll poll = polls.get(0);
        voteService.saveUsersVoteForPoll(user.getUserId(), poll.getChoices().get(0).getChoiceId());

        assertEquals(true, voteService.isVotedForPoll(user, poll));
    }

    @Test
    public void testUserAnonymousVoteForChoice() throws Exception {
        User user = TestDataHelper.getBasicUserWithAgeOf(18, userDAO);
        List<Poll> polls = pollService.getAnonymousPollsFor(user);
        Poll poll = polls.get(0);
        voteService.saveUsersVoteForPoll(user.getUserId(), poll.getChoices().get(0).getChoiceId());

        assertEquals(true, voteService.isVotedForPoll(user, poll));
    }


    @Test
    public void testNotAllowedVote() throws Exception {
        User user = TestDataHelper.getBasicUser(userDAO);
        Poll poll = pollService.listAllPollsCreatedBy(user).get(0);
        voteService.saveUsersVoteForPoll(user.getUserId(), poll.getChoices().get(0).getChoiceId());

        assertEquals(false, voteService.isVotedForPoll(user, poll));
    }

}