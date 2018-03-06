package fr.lyon1.avote.logic.enums;

public enum VoteErrorType {
    FORBIDDEN_CHOICE("this choice is not available for this poll", "Choice with id : %s is not available for poll with details: %s"),
    WRONG_ROUND_ASSOCIATION("Choices have different poll associations", "Choice with id: %s has wrong poll association. Poll details: %s"),
    VOTE_NULL("vote is null", "Trying to save a Vote object that is null"),
    UNAUTHORIZED_USER("user is not allowed to vote", "User with user id: %s has no right to vote to choice with id: %s"),
    INVALID_VOTE_VALUE("invalid vote value", "User with user id: %s tried to vote with wrong value for poll with id: %s");

    private String notificationString;
    private String logMessage;

    VoteErrorType(String notificationString, String logMessage) {
        this.notificationString = notificationString;
        this.logMessage = logMessage;
    }

    public String getNotificationString() {
        return notificationString;
    }

    public String getLogMessage() {
        return logMessage;
    }
}
