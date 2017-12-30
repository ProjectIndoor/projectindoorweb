package de.hftstuttgart.projectindoorweb.web.internal;

public class ResponseWrapper {

    private long id;
    private String message;

    /*For JSON deserialization by Jackson*/
    protected ResponseWrapper() {
    }

    public ResponseWrapper(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
