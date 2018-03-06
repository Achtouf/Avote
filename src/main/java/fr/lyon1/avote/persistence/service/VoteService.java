package fr.lyon1.avote.persistence.service;

import fr.lyon1.avote.logic.enums.VoteErrorType;
import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.logic.model.entity.UserPoll;
import fr.lyon1.avote.logic.model.entity.Vote;
import fr.lyon1.avote.logic.model.exception.InvalidVoteException;
import fr.lyon1.avote.persistence.dao.ChoiceDAO;
import fr.lyon1.avote.persistence.dao.UserPollDAO;
import fr.lyon1.avote.persistence.dao.VoteDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VoteService {

    private static final String FORBIDDEN_VOTE_ATTEMPT = "User with id: %d tried to vote for poll wth id: %d";
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteService.class);
    @Autowired
    private UserService userService;
    @Autowired
    private VoteDAO voteDAO;
    @Autowired
    private UserPollDAO userPollDAO;
    @Autowired
    private PollService pollService;
    @Autowired
    private ChoiceDAO choiceDAO;

    @Transactional
    public void saveUsersVoteForPoll(int userId, int choiceId) {
        User user = userService.getUserById(userId);
        Choice choice = voteDAO.getChoiceById(choiceId);
        if (!pollService.isUserAllowedToVote(user, choice.getPoll())) {
            LOGGER.warn(String.format(FORBIDDEN_VOTE_ATTEMPT, userId, choice.getPoll().getPollId()));
            return;
        }
        if (choice.getPoll().isAnonymous()) {
            registerVoteEvent(user, choice);
            saveVote(null, choice, 1);
        } else {
            userVoteForChoice(user, choice, 1);
        }
    }

    private void userVoteForChoice(User user, Choice choice, int value) {
        registerVoteEvent(user, choice);
        saveVote(user, choice, value);
    }

    private void registerVoteEvent(User basicUser, Choice choice) {
        userPollDAO.saveUserPoll(basicUser, choice.getPoll());
    }

    private void saveVote(User basicUser, Choice choice, int value) {
        Vote vote = new Vote();
        vote.setUser(basicUser);
        vote.setChoice(choice);
        vote.setValue(value);
        voteDAO.saveVote(vote);
    }

    @Transactional
    public boolean isVotedForPoll(User user, Poll poll) {
        UserPoll userPoll = userPollDAO.getUserPoll(user, poll);
        return userPoll != null;
    }

    private void verifyVotes(List<Vote> votes) throws InvalidVoteException {
        int pollId = 0;
        if (!votes.isEmpty()) {
            pollId = votes.get(0).getChoice().getPoll().getPollId();
        } else {
            InvalidVoteException.throwException(VoteErrorType.VOTE_NULL, 0, "null");
        }
        for (Vote vote : votes) {
            int maxValue = votes.get(0).getChoice().getPoll().getPollType().getChoiceValueLimit();
            if (vote.getValue() > maxValue || vote.getValue() < 0) {
                InvalidVoteException.throwException(VoteErrorType.INVALID_VOTE_VALUE, 0, "null");
            }
            if (vote.getChoice().getPoll().getPollId() != pollId) {
                InvalidVoteException.throwException(VoteErrorType.WRONG_ROUND_ASSOCIATION, vote.getUser().getUserId(), vote.getChoice().getPoll().toString());
            }
            if (!pollService.isUserAllowedToVote(vote.getUser(), vote.getChoice().getPoll())) {
                InvalidVoteException.throwException(VoteErrorType.UNAUTHORIZED_USER, vote.getUser().getUserId(), vote.getChoice().getPoll().toString());
            }
        }
    }

    @Transactional
    public void vote(List<Vote> votes) throws InvalidVoteException {
        loadChoices(votes);
        verifyVotes(votes);
        registerVoteEvents(votes);
        for (Vote vote : votes) {
            if (vote.getChoice().getPoll().isAnonymous()) {
                vote.setUser(null);
            }
            voteDAO.saveVote(vote);
        }
    }

    private void registerVoteEvents(List<Vote> votes) {
        Vote vote = votes.get(0);
        userPollDAO.saveUserPoll(vote.getUser(), vote.getChoice().getPoll());
    }

    private void loadChoices(List<Vote> votes) {
        for (Vote vote : votes) {
            Choice loadedChoice = choiceDAO.loadChoice(vote.getChoice());
            loadedChoice.getPoll().isAnonymous();
            vote.setChoice(loadedChoice);
        }
    }
}
