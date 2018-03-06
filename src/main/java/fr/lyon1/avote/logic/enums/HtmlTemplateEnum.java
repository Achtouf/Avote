package fr.lyon1.avote.logic.enums;

public enum HtmlTemplateEnum {
    POLL_TEMPLATE("poll-poll-list.template.html"),
    RESULTS_TEMPLATE("results-poll-list.template.html"),
    EDITION_TEMPLATE("list-poll-edit.template.html"),
    VOTE_TEMPLATE("list-not-voted-polls.template.html"),
    USER_TEMPLATE("profile.template.html");

    private String fileName;

    HtmlTemplateEnum(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
