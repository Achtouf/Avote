package fr.lyon1.avote.logic.model;


import fr.lyon1.avote.logic.model.entity.Choice;

public class MajorVoteInfo {

    private double inferiorVotes = 0.0d;
    private double superiorVotes = 0.0d;
    private Choice choice;
    private int majorValue;

    public MajorVoteInfo() {
    }

    public double getInferiorVotes() {
        return inferiorVotes;
    }

    public void setInferiorVotes(double inferiorVotes) {
        this.inferiorVotes = inferiorVotes;
    }

    public double getSuperiorVotes() {
        return superiorVotes;
    }

    public void setSuperiorVotes(double superiosVotes) {
        this.superiorVotes = superiosVotes;
    }

    public Choice getMajorChoice() {
        return choice;
    }

    public void setMajorChoice(Choice majorValue) {
        this.choice = majorValue;
    }

    public int getMajorValue() {
        return majorValue;
    }

    public void setMajorValue(int majorValue) {
        this.majorValue = majorValue;
    }
}
