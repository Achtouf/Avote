package fr.lyon1.avote.logic.model.major;

import fr.lyon1.avote.logic.model.entity.Vote;

import java.util.Comparator;

public class VoteValueComparator implements Comparator<Vote> {
    @Override
    public int compare(Vote voteOne, Vote voteTwo) {
        return voteOne.getValue() - voteTwo.getValue();
    }
}
