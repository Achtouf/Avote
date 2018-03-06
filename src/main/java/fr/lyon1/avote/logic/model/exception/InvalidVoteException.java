package fr.lyon1.avote.logic.model.exception;

import fr.lyon1.avote.logic.enums.VoteErrorType;

public class InvalidVoteException extends Exception {

    private final VoteErrorType errorType;

    public InvalidVoteException(String message, VoteErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public static void throwException(VoteErrorType errorType, int id, String objectDetails) throws InvalidVoteException {
        // TODO find a good way to summerize poll details that doesn't cause lazy fetching errors
        String message;
        switch (errorType) {
            case FORBIDDEN_CHOICE:
                message = String.format(errorType.getLogMessage(), id, objectDetails);
                break;
            case WRONG_ROUND_ASSOCIATION:
                message = String.format(errorType.getLogMessage(), id, objectDetails);
                break;

            default:
                message = "Undefined error occured with value: " + id;
        }
        throw new InvalidVoteException(message, errorType);
    }

    public VoteErrorType getErrorType() {
        return errorType;
    }
}
