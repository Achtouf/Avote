package fr.lyon1.avote.persistence;

import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.persistence.dao.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class UserDAOTest {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    private UserDAO userDao;

    @Before
    public void addBasicUser() {
        Session session = sessionFactory.openSession();
        User newUser = userDao.getUserByEmail("Email@email");
        if (newUser != null) {
            userDao.delete(newUser);
        }
        Query q = session.createNativeQuery("INSERT INTO users (first_name, last_name, email, password, is_activated, is_deleted, created_at)" + "VALUES (?, ?, ?, ?, ?, ?, ?)");
        q.setParameter(1, "First_Name");
        q.setParameter(2, "Last_Name");
        q.setParameter(3, "Email@email");
        q.setParameter(4, "Password");
        q.setParameter(5, 1);
        q.setParameter(6, 0);
        q.setParameter(7, "2017-11-18");
        session.getTransaction().begin();
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void getUserByEmailTest() {
        User u = userDao.getUserByEmail("Email@email");
        assertNotNull(u);
        assertEquals(u.getFirstName(), "First_Name");
        assertEquals(u.getLastName(), "Last_Name");
        assertEquals(u.getEmail(), "Email@email");
        assertEquals(u.getPassword(), "Password");
        assert ((u.isActivated()));
        assert (!(u.isDeleted()));
    }

    @After
    @Transactional
    public void finalizeTest() {
        TestDataHelper.deleteBasicUser(userDao);
    }
}
