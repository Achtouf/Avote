package fr.lyon1.avote.persistence.dao;

import fr.lyon1.avote.logic.model.entity.Restriction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class RestrictionDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void addRestriction(Restriction r) {
        Session session = sessionFactory.getCurrentSession();
        session.save(r);
    }
}
