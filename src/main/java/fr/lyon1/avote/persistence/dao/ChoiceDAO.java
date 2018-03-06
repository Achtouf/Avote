package fr.lyon1.avote.persistence.dao;

import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Poll;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class ChoiceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void addChoicesForPoll(Poll poll, List<Choice> choices) {
        Session session = sessionFactory.getCurrentSession();
        for (Choice c : choices) {
            c.setPoll(poll);
        }
        for (Choice c : choices) {
            session.save(c);
        }
    }

    public void deleteChoice(Choice choice) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(choice);
    }

    public List<Choice> getChoicesForPollId(int pollId) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Poll> q = session.createQuery("SELECT p FROM Poll p WHERE p.pollId = ?1", Poll.class);
        q.setParameter(1, pollId);
        Poll poll = null;
        if (!q.getResultList().isEmpty()) {
            poll = q.getSingleResult();
        }
        return poll == null ? Collections.<Choice>emptyList() : poll.getChoices();
    }

    public Choice getChoiceById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Choice.class, id);
    }


    public Choice loadChoice(Choice choice) {
        Session session = sessionFactory.getCurrentSession();
        return session.load(Choice.class, choice.getChoiceId());
    }
}
