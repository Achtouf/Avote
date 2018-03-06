package fr.lyon1.avote.persistence.dao;


import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.logic.model.entity.UserPoll;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserPollDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveUserPoll(User basicUser, Poll poll) {
        Session session = sessionFactory.getCurrentSession();
        UserPoll userPoll = new UserPoll();
        userPoll.setPoll(poll);
        userPoll.setUser(basicUser);
        session.save(userPoll);
    }

    public UserPoll getUserPoll(User user, Poll poll) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserPoll userPoll where userPoll.poll = :pollInQuestion and userPoll.user = :userInQuestion");
        query.setParameter("pollInQuestion", poll);
        query.setParameter("userInQuestion", user);
        query.setMaxResults(1);
        List<UserPoll> userPollList = query.getResultList();
        return userPollList.isEmpty() ? null : userPollList.get(0);
    }

}
