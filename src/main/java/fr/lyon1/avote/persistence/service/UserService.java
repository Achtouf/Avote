package fr.lyon1.avote.persistence.service;

import fr.lyon1.avote.logic.model.entity.Address;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.persistence.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Transactional
    public void updateAddressForUser(User user, Address address) {
        if (address.getAddressId() == 0 || user.getAddress() == null) {
            userDAO.addAddress(address);
            user.setAddress(address);
            userDAO.updateUser(user);
        } else {
            userDAO.updateAddress(address);
            user.setAddress(address);
            userDAO.updateUser(user);
        }

    }

    @Transactional
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public void deleteAll() {
        userDAO.deleteAll();
    }

    @Transactional
    public User getRandomUser() {
        return userDAO.getRandomUser();
    }


    @Transactional
    public List<User> getAllUsersWithVotes() {
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            user.getVotes().size();
        }
        return users;
    }


}
