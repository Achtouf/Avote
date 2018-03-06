package fr.lyon1.avote.persistence.dao;

import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class PollDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void addPoll(Poll poll) {
        Session session = sessionFactory.getCurrentSession();
        poll.setCreatedAt(new Date());
        session.save(poll);
    }

    @Transactional
    public List<Poll> getPollBySubject(String subject) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Poll> q = session.createQuery("FROM Poll poll WHERE poll.subject = :question");
        q.setParameter("question", subject);
        return q.getResultList();
    }


    public List<Poll> getAllPollWihtoutRestrictions() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Poll> q = session.createQuery("FROM Poll poll WHERE poll.restriction = null", Poll.class);
        return q.getResultList();
    }

    public List<Poll> getAllPollWihtoutRestrictionsForUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        String notVotedYet = "NOT EXISTS (SELECT 1 FROM UserPoll userPoll WHERE userPoll.poll = poll and userPoll.user = :user)";
        String published = "(poll.isPublished = :published AND poll.isClosed = :closed)";
        TypedQuery<Poll> q = session.createQuery("SELECT poll FROM Poll poll WHERE poll.restriction = null and poll.owner is not :user and " + notVotedYet + " and " + published, Poll.class);
        q.setParameter("user", user);
        q.setParameter("published", true);
        q.setParameter("closed", false);
        return q.getResultList();
    }

    public List<Poll> getAllVotedPollsForUser(User u) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Poll> q = session.createQuery("SELECT userPoll.poll FROM UserPoll userPoll WHERE userPoll.user = :userObject", Poll.class);
        q.setParameter("userObject", u);
        return q.getResultList();
    }


    public List<Poll> getPollsCreatedByUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Poll> q = session.createQuery("SELECT poll FROM Poll poll WHERE poll.owner = ?1", Poll.class);
        q.setParameter(1, user);
        return q.getResultList();
    }

    @Transactional
    public void deleteAllPolls() {
        Session session = sessionFactory.getCurrentSession();
        for (Poll poll : getAllPolls()) {
            session.remove(poll);
        }
    }

    public Poll getPollById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Poll.class, id);
    }

    public List<Poll> getAllPolls() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Poll").list();
    }

    public List<Poll> getPollsCreatedBy(User creator) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Poll> q = session.createQuery("FROM Poll poll WHERE poll.owner = :user", Poll.class);
        q.setParameter("user", creator);
        return q.getResultList();
    }

    public void update(Poll poll) {
        Session session = sessionFactory.getCurrentSession();
        session.update(poll);
    }

    public void delete(Poll poll) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(poll);
    }
}