package fr.lyon1.avote.persistence.service;

import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.persistence.DataTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class UserServiceTest extends DataTestBase {

    @Autowired
    private UserService userService;

    @Test
    public void getUserById() throws Exception {
        User user = userService.getRandomUser();
        userService.getUserById(user.getUserId());
    }

}