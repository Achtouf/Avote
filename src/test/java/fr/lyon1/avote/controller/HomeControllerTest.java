package fr.lyon1.avote.controller;

import fr.lyon1.avote.filters.Md5;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.persistence.DataTestBase;
import fr.lyon1.avote.persistence.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class HomeControllerTest extends DataTestBase {

    @InjectMocks
    @Autowired
    private HomeController homeController = new HomeController();

    private User user = new User();

    @Before
    public void init(){

        user.setEmail("test@test");
        user.setPassword("test");
        Md5 Password = new Md5(user.getPassword());
        user.setPassword(Password.getWord());
        user.setFirstName("Ftest");
        user.setLastName("Ltest");
        user.setActivated(true);
        user.setDeleted(false);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void LogoutTest() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Logout").sessionAttr("user", user))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/Login"))
                .andReturn();

        //.andExpect(model().attribute("user", isNotNull()));
        // On vérifie que la fonction in est appeller une fois

        //
        //verifyNoMoreInteractions(mockMvc);
    }

    @Test
    public void showPageTest() throws Exception {

        // Avec user
        mockMvc.perform(get("/Signin").sessionAttr("user", user))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/Dashboard"));

        // Sans user
        mockMvc.perform(get("/Signin"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void loginTest() throws  Exception {
        // Avec user
        mockMvc.perform(get("/Login").sessionAttr("user", user))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/Dashboard"));

        // Sans user
        mockMvc.perform(get("/Login"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void signinFormTestIf() throws  Exception {

        Mockito.when(this.userServiceMock.getUserByEmail(user.getEmail())).thenReturn(user);

        // Si l'utilisateur n'existe pas
        mockMvc.perform(post("/SigninForm")
                .param("lastName", user.getLastName())
                .param("firstName", user.getFirstName())
                .param("email", "fakeEmail")
                .param("birthday", "10/10/1996")
                .param("password", user.getPassword()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/Login"))
                .andDo(print());

        userServiceMock.addUser(user);
    }

    @Test
    public void signinFormTestElse() throws  Exception {

        Mockito.when(this.userServiceMock.getUserByEmail(user.getEmail())).thenReturn(user);

        // Si l'utilisateur n'existe pas
        mockMvc.perform(post("/SigninForm")
                .param("lastName", user.getLastName())
                .param("firstName", user.getFirstName())
                .param("email", user.getEmail())
                .param("birthday", "10/10/1996")
                .param("password", user.getPassword()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/Login"))
                .andDo(print());
    }

    @Test
    public void dashboardTest() throws  Exception {
        mockMvc.perform(get("/Dashboard"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/Login"));

        mockMvc.perform(get("/Dashboard")
                .sessionAttr("user", user))
                .andExpect(status().isOk());
    }

    @Test
    public void loginFormTest() throws  Exception {
        Mockito.when(this.userServiceMock.getUserByEmail(user.getEmail())).thenReturn(user);

        // Réussi
        mockMvc.perform(post("/LoginForm")
                .param("password", "test")
                .param("email", user.getEmail()))
                .andExpect(redirectedUrl("/Dashboard"));

        // Mauvais password
        mockMvc.perform(post("/LoginForm")
                .param("password", "BadPassword")
                .param("email", user.getEmail()))
                .andExpect(redirectedUrl("/Login"));

        // compte supprimé
        user.setDeleted(true);
        mockMvc.perform(post("/LoginForm")
                .param("password", "Don't care")
                .param("email", user.getEmail()))
                .andExpect(redirectedUrl("/Signin"));
        user.setDeleted(false);

        // Non activé
        user.setActivated(false);
        mockMvc.perform(post("/LoginForm")
                .param("password", "Don't care")
                .param("email", user.getEmail()))
                .andExpect(redirectedUrl("/Login"));
        user.setActivated(true);
    }

    @After
    public void cleanUp(){
        userServiceMock.deleteAll();
    }
}
