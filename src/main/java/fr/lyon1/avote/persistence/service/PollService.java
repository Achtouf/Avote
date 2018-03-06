package fr.lyon1.avote.persistence.service;

import fr.lyon1.avote.logic.enums.PollCreationErrorType;
import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.logic.model.exception.InvalidPollException;
import fr.lyon1.avote.logic.service.DateService;
import fr.lyon1.avote.persistence.dao.ChoiceDAO;
import fr.lyon1.avote.persistence.dao.DepartmentDAO;
import fr.lyon1.avote.persistence.dao.PollDAO;
import fr.lyon1.avote.persistence.dao.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class PollService {

    @Autowired
    private PollDAO pollDAO;
    @Autowired
    private ChoiceDAO choiceDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DepartmentDAO departmentDao;
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Choice> getChoicesWithVotesByPollName(String name) {
        Poll poll = pollDAO.getPollBySubject(name).get(0);

        // fetching votes of choices
        for (Choice choice : poll.getChoices()) {
            choice.getVotes().size();
        }
        return poll.getChoices();
    }

    public void addPoll(Poll poll) throws InvalidPollException {
        verifyPoll(poll);
        pollDAO.addPoll(poll);
    }

    private void verifyPoll(Poll poll) throws InvalidPollException {
        if (poll.getSubject() == null || poll.getSubject().isEmpty()) {
            InvalidPollException.throwException(PollCreationErrorType.EMPTY_SUBJECT, poll.getSubject(), poll.toString());
        }
        for (Choice c : poll.getChoices()) {
            if (!c.isValid()) {
                InvalidPollException.throwException(PollCreationErrorType.INVALID_CHOICE, c.getLabel(), poll.toString());
            }
        }
        if (poll.getNumberRounds() > 2) {
            InvalidPollException.throwException(PollCreationErrorType.TOO_MANY_ROUNDS, Integer.toString(poll.getNumberRounds()), poll.toString());
        }
    }

    @Transactional
    public List<Poll> getAllPollWithoutRestrictions() {
        List<Poll> polls = pollDAO.getAllPollWihtoutRestrictions();
        for (Poll poll : polls) {
            for (Choice choice : poll.getChoices()) {
                choice.getVotes().size();
            }
        }
        return polls;
    }

    @Transactional
    public List<Poll> listAllPollsAvailableForUser(User user) {
        List<Poll> polls = getAllPollsForUser(user);
        for (Poll poll : polls) {
            for (Choice choice : poll.getChoices()) {
                choice.getVotes().size();
            }
        }
        return polls;
    }

    @Transactional
    public List<Poll> listAllPollsCreatedBy(User user) {
        List<Poll> polls = pollDAO.getPollsCreatedBy(user);
        for (Poll poll : polls) {
            for (Choice choice : poll.getChoices()) {
                choice.getVotes().size();
            }
        }
        return polls;
    }

    @Transactional
    public List<Choice> getChoicesForPollId(String pollId) {
        return choiceDAO.getChoicesForPollId(Integer.parseInt(pollId));
    }

    @Transactional
    public Poll getPollById(int id) {
        Poll poll = pollDAO.getPollById(id);
        if (poll != null) {
            poll.getChoices().size();
        }
        return poll;
    }

    @Transactional
    public List<Poll> listAllPolls() {
        return pollDAO.getAllPolls();
    }


    public void deleteAllPolls() {
        pollDAO.deleteAllPolls();
    }

    @Transactional
    public List<Poll> listPollsVotedBy(User user) {
        List<Poll> votedPolls = pollDAO.getAllVotedPollsForUser(user);
        for (Poll poll : votedPolls) {
            for (Choice choice : poll.getChoices()) {
                choice.getVotes().size();
            }
        }
        return votedPolls;
    }

    @Transactional
    public void updatePoll(Poll poll) throws InvalidPollException {
        verifyPoll(poll);
        pollDAO.update(poll);
    }

    @Transactional
    public void deleteChoice(Choice choice) {
        choiceDAO.deleteChoice(choice);
    }

    @Transactional
    public void publishPoll(int userId, int pollId) throws InvalidPollException {
        User user = userDAO.getUserById(userId);
        Poll poll = pollDAO.getPollById(pollId);
        if (poll.getChoices().isEmpty()) {
            InvalidPollException.throwException(PollCreationErrorType.NO_CHOICES, poll.getSubject(), poll.toString());
        }
        if (poll.getOwner().equals(user)) {
            poll.setPublished(true);
            pollDAO.update(poll);
        }
    }

    @Transactional
    public List<Poll> getAllPollsForUser(User user) {
        List<Poll> allPolls = getAllAvailablePollsWithRestrictionsOf(user);
        allPolls.addAll(pollDAO.getAllPollWihtoutRestrictionsForUser(user));
        return allPolls;
    }

    @Transactional
    public List<Poll> getAllAvailablePollsWithRestrictionsOf(User user) {
        String age = "(poll.restriction.ageMin <= :age and poll.restriction.ageMax >= :age)";
        String city = "(poll.restriction.city = :city or poll.restriction.city is NULL)";
        //String department = "(poll.restriction.department = :dep or poll.restriction.department is NULL)";
        String owner = "(poll.owner is not :userInQuestion)";
        String notVotedYet = "NOT EXISTS (SELECT 1 FROM UserPoll userPoll WHERE userPoll.poll = poll and userPoll.user = :userInQuestion)";
        String published = "(poll.isPublished = :published AND poll.isClosed = :closed)";
        String hqlString = "SELECT poll FROM Poll poll WHERE " + age + " and " + city + " and " + owner + " and " + notVotedYet + " and " + published;
        return buildQuery(hqlString, user).getResultList();
    }

    @Transactional
    public boolean isUserAllowedToVote(User user, Poll poll) {
        String age = "(poll.restriction.ageMin <= :age and poll.restriction.ageMax >= :age)";
        String city = "(poll.restriction.city = :city or poll.restriction.city is NULL)";
        //String department = "(poll.restriction.department = :dep or poll.restriction.department is NULL)";
        String owner = "(poll.owner is not :userInQuestion)";
        String notVotedYet = "NOT EXISTS (SELECT 1 FROM UserPoll userPoll WHERE userPoll.poll = poll and userPoll.user = :userInQuestion)";
        String pollCheck = "(poll = :pollInQuestion)";
        String published = "(poll.isPublished = :published AND poll.isClosed = :closed)";
        String hqlString = "SELECT poll FROM Poll poll WHERE " + age + " and " + city + " and " + owner + " and " + notVotedYet + " and " + published + " and " + pollCheck;
        TypedQuery<Poll> queryAmongRestricted = buildQuery(hqlString, user);
        queryAmongRestricted.setParameter("pollInQuestion", poll);
        boolean restrictedAndAllowed = !queryAmongRestricted.getResultList().isEmpty();
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Poll> queryAmongPublic = session.createQuery("SELECT poll FROM Poll poll WHERE poll.restriction = null and poll.owner is not :userInQuestion and " + notVotedYet + "and" + " " + pollCheck, Poll.class);
        queryAmongPublic.setParameter("userInQuestion", user);
        queryAmongPublic.setParameter("pollInQuestion", poll);
        return !queryAmongPublic.getResultList().isEmpty() || restrictedAndAllowed;
    }

    private TypedQuery<Poll> buildQuery(String hqlString, User user) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Poll> q = session.createQuery(hqlString, Poll.class);
        q.setParameter("age", DateService.calculateYearPassesSince(user.getBirthday()));
        q.setParameter("userInQuestion", user);
        q.setParameter("published", true);
        q.setParameter("closed", false);
        if (user.getAddress() != null) {
            q.setParameter("city", user.getAddress().getCity());
            //q.setParameter("dep", departmentDao.getDepartmentByPostalCode(user.getAddress().getZipCode()));
        } else {
            q.setParameter("city", null);
            //q.setParameter("dep", null);
        }
        return q;
    }

    @Transactional
    public List<Poll> getAnonymousPollsFor(User user) {
        List<Poll> anonymousPolls = new ArrayList<>();
        List<Poll> polls = listAllPollsAvailableForUser(user);
        for (Poll poll : polls) {
            if (!poll.isAnonymous()) {
                anonymousPolls.add(poll);
            }
        }
        return anonymousPolls;
    }

    @Transactional
    public void deletePoll(Poll poll) throws InvalidPollException {
        verifyPoll(poll);
        pollDAO.delete(poll);
    }

    @Transactional
    public Poll loadPoll(Poll poll) {
        return pollDAO.getPollById(poll.getPollId());
    }
}

