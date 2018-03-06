package fr.lyon1.avote.persistence.dao;

import fr.lyon1.avote.logic.model.entity.Address;
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
public class UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public User getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> q = session.createQuery("SELECT u FROM User u WHERE u.email = ?1", User.class);
        q.setParameter(1, email);
        User u = null;
        if (!q.getResultList().isEmpty()) {
            u = q.getSingleResult();
        }
        return u;
    }

    @Transactional
    public void addUser(User u) {
        Session session = sessionFactory.getCurrentSession();
        u.setCreatedAt(new Date());
        session.save(u);
    }

    @Transactional
    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    @Transactional
    public void deleteAll() {
        Session session = sessionFactory.getCurrentSession();
        for (User user : getAllUsers()) {
            session.remove(user);
        }
    }

    public User getUserById(int id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User").list();
    }

    public User getRandomUser() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> q = session.createQuery("from User");
        q.setMaxResults(1);
        return q.getSingleResult();
    }

    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public void addAddress(Address a) {
        Session session = sessionFactory.getCurrentSession();
        session.save(a);
    }

    public void updateAddress(Address a) {
        Session session = sessionFactory.getCurrentSession();
        session.update(a);
    }
}
