package fr.lyon1.avote.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class HibernateSpringConfigTest {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void basicTestSpringSetup() {
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("select 1 from Poll");
        q.setFetchSize(1);
        q.list();
        session.close();
    }


}
