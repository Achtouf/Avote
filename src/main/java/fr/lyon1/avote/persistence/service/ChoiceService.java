package fr.lyon1.avote.persistence.service;

import fr.lyon1.avote.logic.model.entity.*;
import fr.lyon1.avote.persistence.dao.ChoiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChoiceService {

    @Autowired
    private ChoiceDAO choiceDAO;

    @Transactional
    public Choice[] loadChoices(Choice... choices) {
        for (Choice choice : choices) {
            Choice loadedChoice = choiceDAO.loadChoice(choice);
            Poll poll  = loadedChoice.getPoll();
            poll.getChoices().size();
        }

        return choices;
    }

    @Transactional
    public int getOwnerIdOf(Choice choice) {
        choice = choiceDAO.loadChoice(choice);
        if (choice == null) {
            return  0;
        } else {
            return choice.getPoll().getOwner().getUserId();
        }
    }
}
