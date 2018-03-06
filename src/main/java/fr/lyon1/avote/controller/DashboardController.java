package fr.lyon1.avote.controller;

import fr.lyon1.avote.logic.enums.HtmlTemplateEnum;
import fr.lyon1.avote.logic.model.StringResponse;
import fr.lyon1.avote.logic.model.entity.Address;
import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.logic.model.entity.Vote;
import fr.lyon1.avote.logic.model.exception.InvalidPollException;
import fr.lyon1.avote.logic.model.exception.InvalidVoteException;
import fr.lyon1.avote.logic.service.VelocityService;
import fr.lyon1.avote.persistence.service.ChoiceService;
import fr.lyon1.avote.persistence.service.PollService;
import fr.lyon1.avote.persistence.service.UserService;
import fr.lyon1.avote.persistence.service.VoteService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = {"/dashboard", "/Dashboard"})
public class DashboardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
    private static final String SUCCESSFUL_VOTE = "Vote enregistré";

    @Autowired
    private PollService pollService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChoiceService choiceService;

    @RequestMapping(value = "/PollCreationForm", produces = "application/json")
    public @ResponseBody
    StringResponse createPoll(@RequestBody Poll poll, HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        try {
            user = (User) request.getSession().getAttribute("user");
            poll.setOwner(user);
            pollService.addPoll(poll);
        } catch (InvalidPollException pollException) {
            String errorLog = String.format("poll creation initiated by user with id: %s", StringUtils.defaultIfBlank(String.valueOf(user.getUserId()), "unkown"));
            LOGGER.error(errorLog, pollException);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StringResponse(pollException.getErrorType().getNotificationString());
        }
        String htmlCode = "";
        htmlCode = VelocityService.createPollListHtml(pollService.listAllPollsCreatedBy(user), HtmlTemplateEnum.POLL_TEMPLATE);
        return new StringResponse(htmlCode);
    }

    @RequestMapping(value = "/UpdatePoll", produces = "application/json")
    public @ResponseBody
    StringResponse updatePoll(@RequestBody Poll poll, HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        try {
            user = (User) request.getSession().getAttribute("user");
            poll.setOwner(user);
            pollService.updatePoll(poll);
        } catch (InvalidPollException pollException) {
            String errorLog = String.format("poll update initiated by user with id: %s", StringUtils.defaultIfBlank(String.valueOf(user.getUserId()), "unkown"));
            LOGGER.error(errorLog, pollException);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StringResponse(pollException.getErrorType().getNotificationString());
        }
        String htmlCode = "";
        htmlCode = VelocityService.createPollListHtml(pollService.listAllPollsCreatedBy(user), HtmlTemplateEnum.POLL_TEMPLATE);
        return new StringResponse(htmlCode);
    }

    @RequestMapping(value = "/DeleteChoice", produces = "application/json")
    public @ResponseBody
    StringResponse deleteChoice(@RequestBody Choice choice, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (choiceService.getOwnerIdOf(choice) != user.getUserId()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new Exception("Not owner of poll : " + user.getUserId() + " - " + user.getFirstName() + " " + user.getLastName());
        }
        pollService.deleteChoice(choice);
        return new StringResponse("Choice successfully deleted!");

    }

    @RequestMapping(value = "/DeletePoll", produces = "application/json")
    public @ResponseBody
    StringResponse deletePoll(@RequestBody Poll poll, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        poll = pollService.loadPoll(poll);
        if (poll.getOwner().getUserId() != user.getUserId()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new Exception("Not owner of poll : " + user.getUserId() + " - " + user.getFirstName() + " " + user.getLastName());
        }
        pollService.deletePoll(poll);
        String htmlCode = "";
        htmlCode = VelocityService.createPollListHtml(pollService.listAllPollsCreatedBy(user), HtmlTemplateEnum.POLL_TEMPLATE);
        return new StringResponse(htmlCode);
    }

    @RequestMapping(value = "/CreatedPolls", produces = "application/json")
    public @ResponseBody
    StringResponse listCreatedPolls(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String htmlCode = "";
        htmlCode = VelocityService.createPollListHtml(pollService.listAllPollsCreatedBy(user), HtmlTemplateEnum.POLL_TEMPLATE);
        return new StringResponse(htmlCode);
    }

    @RequestMapping(value = "/NotYetVotedPolls", produces = "application/json")
    public @ResponseBody
    StringResponse listAllPolls(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String htmlCode = "";
        htmlCode = VelocityService.createPollListHtml(pollService.listAllPollsAvailableForUser(user), HtmlTemplateEnum.VOTE_TEMPLATE);
        return new StringResponse(htmlCode);
    }

    @RequestMapping(value = "/VotedPolls", produces = "application/json")
    public @ResponseBody
    StringResponse listVotedPolls(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String htmlCode = "";
        htmlCode = VelocityService.createPollListHtml(pollService.listPollsVotedBy(user), HtmlTemplateEnum.RESULTS_TEMPLATE);
        return new StringResponse(htmlCode);
    }

    @RequestMapping(value = "/Vote", produces = "application/json")
    public @ResponseBody
    StringResponse vote(@RequestBody List<Vote> votes, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        for (Vote vote : votes) {
            vote.setUser(user);
        }
        try {
            voteService.vote(votes);
        } catch (InvalidVoteException e) {
            String errorLog = String.format("forbidden vote attempt by user with id: %s", StringUtils.defaultIfBlank(String.valueOf(user.getUserId()), "unkown"));
            LOGGER.error(errorLog, e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StringResponse(e.getErrorType().getNotificationString());
        }
        return new StringResponse(SUCCESSFUL_VOTE);
    }

    @RequestMapping(value = "/PublishPoll", produces = "application/json")
    public @ResponseBody
    StringResponse publishPoll(@RequestParam String pollId, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        try {
            pollService.publishPoll(user.getUserId(), Integer.parseInt(pollId));
        } catch (InvalidPollException e) {
            String errorLog = String.format("user with id: %s tried to create a poll without choices", StringUtils.defaultIfBlank(String.valueOf(user.getUserId()), "unkown"));
            LOGGER.error(errorLog, e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StringResponse(e.getErrorType().getNotificationString());
        }
        return new StringResponse("Robert");
    }

    @RequestMapping(value = "/ChangeAddress", produces = "application/json")
    public @ResponseBody
    StringResponse changeAddress(@RequestBody Address address, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        userService.updateAddressForUser(user, address);
        String htmlCode = "";
        htmlCode = VelocityService.createUserHtml(user, HtmlTemplateEnum.USER_TEMPLATE);
        return new StringResponse(htmlCode);
    }

    @RequestMapping(value = "/GetUserProfile", produces = "application/json")
    public @ResponseBody
    StringResponse getUserProfile(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String htmlCode = "";
        htmlCode = VelocityService.createUserHtml(user, HtmlTemplateEnum.USER_TEMPLATE);
        return new StringResponse(htmlCode);
    }

}
