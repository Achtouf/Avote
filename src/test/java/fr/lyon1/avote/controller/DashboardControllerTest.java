package fr.lyon1.avote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lyon1.avote.filters.Md5;
import fr.lyon1.avote.logic.enums.HtmlTemplateEnum;
import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.logic.service.VelocityService;
import fr.lyon1.avote.persistence.DataTestBase;
import fr.lyon1.avote.persistence.service.UserService;
import org.hibernate.type.AnyType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.omg.CORBA.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mvc-servlet.xml"})
@WebAppConfiguration
public class DashboardControllerTest extends DataTestBase{
    @InjectMocks
    @Autowired
    private DashboardController dashboardController = new DashboardController();

    private User user = new User();
    private ObjectMapper mapperObj = new ObjectMapper();
    private List<Poll> listPoll = new ArrayList<>();
    private Poll pollObj = new Poll();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();

        user.setEmail("test@test");
        user.setPassword("test");
        Md5 Password = new Md5(user.getPassword());
        user.setPassword(Password.getWord());
        user.setFirstName("Ftest");
        user.setLastName("Ltest");
        user.setActivated(true);
        user.setDeleted(false);
        //userServiceMock.addUser(user);

        pollObj.setSubject("Poulet ?");
        Choice choice = new Choice();
        choice.setLabel("Choix 1");
        ArrayList<Choice> listChoice = new ArrayList<>();
        listChoice.add(choice);
        pollObj.setChoices(listChoice);
        //anObject.set

        listPoll.add(pollObj);
    }

    @Test
    public void createPollTest() throws Exception {

        Mockito.when(pollServiceMock.listAllPollsCreatedBy(user)).thenReturn(listPoll);
        String jsonStr = mapperObj.writeValueAsString(pollObj);

        System.out.println("JSON:"+ jsonStr);

        MvcResult result = mockMvc.perform(get("/Dashboard/PollCreationForm")
                .sessionAttr("user", user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        System.out.println("RESULT:" + result.getResponse().getContentAsString());
    }

    @Test
    public void updatePollTest() throws Exception {
        String jsonStr = mapperObj.writeValueAsString(pollObj);

        System.out.println("JSON:"+ jsonStr);

        MvcResult result = mockMvc.perform(get("/Dashboard/UpdatePoll")
                .sessionAttr("user", user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void listCreatedPollsTest() throws Exception {
        String jsonStr = mapperObj.writeValueAsString(pollObj);

        Mockito.when(pollServiceMock.listAllPollsCreatedBy(user)).thenReturn(listPoll);

        System.out.println("JSON:"+ jsonStr);

        MvcResult result = mockMvc.perform(get("/Dashboard/CreatedPolls")
                .sessionAttr("user", user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void listAllPollsTest() throws Exception {
        String jsonStr = mapperObj.writeValueAsString(pollObj);

        Mockito.when(pollServiceMock.listAllPollsAvailableForUser(user)).thenReturn(listPoll);


        System.out.println("JSON:"+ jsonStr);

        MvcResult result = mockMvc.perform(get("/Dashboard/CreatedPolls")
                .sessionAttr("user", user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void listVotedPollsTest() throws Exception {
        String jsonStr = mapperObj.writeValueAsString(pollObj);

        Mockito.when(pollServiceMock.listPollsVotedBy(user)).thenReturn(listPoll);


        System.out.println("JSON:"+ jsonStr);

        MvcResult result = mockMvc.perform(get("/Dashboard/CreatedPolls")
                .sessionAttr("user", user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

}
