package fr.lyon1.avote.persistence.service;

import fr.lyon1.avote.logic.model.api.ApiRequestRestriction;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.logic.model.entity.UserAPI;
import fr.lyon1.avote.logic.model.entity.Vote;
import fr.lyon1.avote.persistence.dao.UserAPIDAO;
import fr.lyon1.avote.persistence.dao.VoteDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Service
public class APIService {

    @Autowired
    private UserAPIDAO userAPIDAO;
    @Autowired
    private VoteDAO voteDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Poll> getPollsWithRequestRestriction(String apiKey, ApiRequestRestriction request) {
        UserAPI api = userAPIDAO.getUserAPIbyAPIKey(apiKey);
        if (api == null) return Collections.emptyList();

        String hqlString = "SELECT poll FROM Poll poll WHERE poll.pollId >= :pollId";
        if (request.getRestriction().getAgeMin() != 0) {
            hqlString += " AND poll.restriction.ageMin = :ageMin";
        }
        if (request.getRestriction().getAgeMax() != 0) {
            hqlString += " AND poll.restriction.ageMax = :ageMax";
        }
        if (!request.getRestriction().getCity().isEmpty()) {
            hqlString += " AND poll.restriction.city = :city";
        }
        if (!request.getRestriction().getDepartment().isEmpty()) {
            hqlString += " AND poll.restriction.department = :dep";
        }
        if (request.getAnonymity() == 0 || request.getAnonymity() == 1) {
            hqlString += " AND poll.anonymity is :anon";
        }
        if (request.getCreatedAfter() != null) {
            hqlString += " AND poll.createdAt >= :createdAfter";
        }
        if (request.getCreatedBefore() != null) {
            hqlString += " AND poll.createdAt <= :createdBefore";
        }

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Poll> query = session.createQuery(hqlString, Poll.class);

        query.setParameter("pollId", 0);
        if (request.getRestriction().getAgeMin() != 0) {
            query.setParameter("ageMin", request.getRestriction().getAgeMin());
        }
        if (request.getRestriction().getAgeMax() != 0) {
            query.setParameter("ageMax", request.getRestriction().getAgeMax());
        }
        if (!request.getRestriction().getCity().isEmpty()) {
            query.setParameter("city", request.getRestriction().getCity());
        }
        if (!request.getRestriction().getDepartment().isEmpty()) {
            query.setParameter("dep", request.getRestriction().getDepartment());
        }
        if (request.getAnonymity() == 0) {
            query.setParameter("anon", false);
        } else if (request.getAnonymity() == 1) {
            query.setParameter("anon", true);
        }
        if (request.getCreatedAfter() != null) {
            query.setParameter("createdAfter", request.getCreatedAfter());
        }
        if (request.getCreatedBefore() != null) {
            query.setParameter("createdBefore", request.getCreatedBefore());
        }

        return query.getResultList();
    }

    @Transactional
    public List<Vote> getVotesForUser(String apiKey) {
        UserAPI api = userAPIDAO.getUserAPIbyAPIKey(apiKey);
        if (api == null) return Collections.emptyList();

        return voteDAO.getVotesForPolls(api.getUser().getPolls());
    }

    @Transactional
    public String getApiKeyFor(User user) {
        UserAPI userAPI = userAPIDAO.getApiKeyOf(user);
        return userAPI.getApiKey();
    }

}
