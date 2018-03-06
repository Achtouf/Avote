package fr.lyon1.avote.logic.enums;

public enum PollCreationErrorType {
    EMPTY_SUBJECT("empty subject", "Poll subject \"%s\" is not valid, poll details: %s"),
    INVALID_CHOICE("choice is not valid", "Poll has invalid choice: \"%s\", poll details: %s"),
    TOO_MANY_ROUNDS("too many rounds", "Poll has too many rounds: \"%s\", poll details: %s"),
    NO_CHOICES("no choices", "Poll has no choices: %s, poll details: %s");

    private String notificationString;
    private String logMessage;

    PollCreationErrorType(String notificationString, String logMessage) {
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
