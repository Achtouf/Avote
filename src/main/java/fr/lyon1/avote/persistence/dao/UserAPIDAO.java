package fr.lyon1.avote.persistence.dao;

import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.logic.model.entity.UserAPI;
import fr.lyon1.avote.logic.service.RandomAESKeyGen;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.security.NoSuchAlgorithmException;

@Repository
public class UserAPIDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(UserAPI userAPI) {
        Session session = sessionFactory.getCurrentSession();
        session.save(userAPI);
    }

    public UserAPI getUserAPIbyAPIKey(String APIKey) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<UserAPI> query = session.createQuery("from UserAPI api where api.apiKey = :key");
        query.setParameter("key", APIKey);
        if (query.getResultList().isEmpty()) {
            return null;
        } else {
            return query.getResultList().get(0);
        }
    }

    public UserAPI getApiKeyOf(User user) {
        Session session = sessionFactory.getCurrentSession();
        UserAPI userAPI = session.get(UserAPI.class, user.getUserId());
        String apiKey = "";
        boolean keyPresent = true;
        if (userAPI == null) {
            userAPI = new UserAPI();
            while (keyPresent) {
                try {
                    apiKey = RandomAESKeyGen.generate(128);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                TypedQuery<UserAPI> userAPITypedQuery = session.createQuery("from UserAPI userApi where userApi.apiKey = :key");
                userAPITypedQuery.setParameter("key", apiKey);
                keyPresent = !userAPITypedQuery.getResultList().isEmpty();
            }
            userAPI.setApiKey(apiKey);
            userAPI.setUser(user);
            session.save(userAPI);
        }
        return userAPI;
    }
}
