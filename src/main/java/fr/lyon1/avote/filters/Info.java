package fr.lyon1.avote.filters;

public class Info {
    private String message;
    private String title;
    private String type;

    public Info(String t, String m, String tp) {
        title = t;
        message = m;
        type = tp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
