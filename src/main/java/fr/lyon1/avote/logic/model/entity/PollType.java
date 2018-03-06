package fr.lyon1.avote.logic.model.entity;

public enum PollType {
    STANDARD(1, 1),
    NOTE(2, 5);

    private int choiceValueLimit;
    private int id;

    PollType(int id, int numberOfVotes) {
        this.id = id;
        this.choiceValueLimit = numberOfVotes;
    }

    public int getChoiceValueLimit() {
        return choiceValueLimit;
    }
}
