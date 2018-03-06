package fr.lyon1.avote.persistence;

import fr.lyon1.avote.controller.HomeController;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.logic.service.VelocityService;
import fr.lyon1.avote.persistence.dao.ChoiceDAO;
import fr.lyon1.avote.persistence.dao.PollDAO;
import fr.lyon1.avote.persistence.dao.UserDAO;
import fr.lyon1.avote.persistence.service.PollService;
import fr.lyon1.avote.persistence.service.UserService;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class DataTestBase {
    @Mock
    protected UserService userServiceMock;

    @Mock
    protected PollService pollServiceMock;

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    protected MockMvc mockMvc;

    @Autowired
    protected PollDAO pollDAO;
    @Autowired
    protected UserDAO userDAO;
    @Autowired
    protected ChoiceDAO choiceDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void createPolls() {
        pollDAO.deleteAllPolls();
        userDAO.deleteAll();
        TestDataHelper.addBasicUser(userDAO);
        TestDataHelper.addBasicUserWithAge(userDAO, 21);
        TestDataHelper.createPolls(pollDAO, userDAO);

        // ViewResolver pour mettre une view de redirection par default

        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void closeConnection() {
        pollDAO.deleteAllPolls();
        TestDataHelper.deleteBasicUser(userDAO);

    }
}
