package fr.lyon1.avote.persistence.dao;

import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.Vote;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VoteDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Transactional
    public int getTotalValueForChoice(Choice c) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Integer> q = session.createQuery("SELECT sum(vote.value) FROM Vote vote WHERE vote.choice = ?1", Integer.class);
        q.setParameter(1, c);
        return q.getSingleResult();
    }

    public void saveVote(Vote vote) {
        Session session = sessionFactory.getCurrentSession();
        session.save(vote);
    }

    public Choice getChoiceById(int choiceId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Choice.class, choiceId);
    }

    public List<Vote> getVotesForPolls(List<Poll> polls) {
        List<Vote> votes = new ArrayList<>();
        for (Poll p : polls) {
            for (Choice c : p.getChoices()) {
                votes.addAll(c.getVotes());
            }
        }
        return votes;
    }
}
