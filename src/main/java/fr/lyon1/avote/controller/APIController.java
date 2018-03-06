package fr.lyon1.avote.controller;

import fr.lyon1.avote.logic.model.StringResponse;
import fr.lyon1.avote.logic.model.api.ApiRequestRestriction;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.logic.model.entity.Vote;
import fr.lyon1.avote.persistence.service.APIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/API")
public class APIController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ApiRequestRestriction.class);

    @Autowired
    private APIService apiService;

    @RequestMapping("/{key}/polls")
    public List<Poll> getPollsWithRestrictions(@PathVariable(name = "key") String keyParam, @RequestParam(name = "ageMin", defaultValue = "") String ageMinParam, @RequestParam(name = "ageMax", defaultValue = "") String ageMaxParam, @RequestParam(name = "city", defaultValue = "") String cityParam, @RequestParam(name = "department", defaultValue = "") String departmentParam, @RequestParam(name = "anonymity", defaultValue = "") String anonymityParam, @RequestParam(name = "createdAfter", defaultValue = "") String createdAfterParam, @RequestParam(name = "createdBefore", defaultValue = "") String createdBeforeParam, HttpServletResponse response) throws Exception {
        ApiRequestRestriction apiRequest;
        try {
            apiRequest = ApiRequestRestriction.buildApiRequestRestriction(ageMinParam, ageMaxParam, cityParam, departmentParam, anonymityParam, createdAfterParam, createdBeforeParam);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOGGER.error("Invalid parameters for request : ", e);
            return null;
        }
        List<Poll> polls;
        try {
            polls = apiService.getPollsWithRequestRestriction(keyParam, apiRequest);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOGGER.error("Error while retrieving polls : ", e);
            return null;
        }
        return polls;
    }

    @RequestMapping("/{key}/myPolls/votes")
    public List<Vote> getVotesForOwnPolls(@PathVariable(name = "key") String keyParam, HttpServletResponse response) {
        List<Vote> votes;
        try {
            votes = apiService.getVotesForUser(keyParam);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOGGER.error("Error while retrieving votes : ", e);
            return null;
        }
        return votes;
    }

    @RequestMapping(value = "/get-key", produces = "application/json")
    public @ResponseBody
    StringResponse getKey(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return new StringResponse(apiService.getApiKeyFor(user));
    }

}
