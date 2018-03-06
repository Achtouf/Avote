package fr.lyon1.avote.logic.model.exception;

import fr.lyon1.avote.logic.enums.PollCreationErrorType;
import org.apache.commons.lang.StringUtils;

public class InvalidPollException extends Exception {

    private final PollCreationErrorType errorType;

    public InvalidPollException(String message, PollCreationErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public static void throwException(PollCreationErrorType errorType, String value, String objectDetails) throws InvalidPollException {
        // TODO find a good way to summarize poll details that doesn't cause lazy fetching errors
        String message;
        switch (errorType) {
            case EMPTY_SUBJECT:
                message = String.format(errorType.getLogMessage(), StringUtils.defaultIfBlank(value, "null"), objectDetails);
                break;
            case INVALID_CHOICE:
                message = String.format(errorType.getLogMessage(), value, objectDetails);
                break;
            case TOO_MANY_ROUNDS:
                message = String.format(errorType.getLogMessage(), StringUtils.defaultIfBlank(value, "null"), objectDetails);
                break;
            default:
                message = "Undefined error occured with value: " + StringUtils.defaultIfBlank(value, "null");
        }
        throw new InvalidPollException(message, errorType);
    }

    public PollCreationErrorType getErrorType() {
        return errorType;
    }
}
