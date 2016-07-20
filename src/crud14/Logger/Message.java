package crud14.Logger;

import javax.faces.application.FacesMessage;

public class Message {
    FacesMessage.Severity severity;
    String summary;
    String message;

    public Message(FacesMessage.Severity severity, String summary, String message) {
        this.severity = severity;
        this.summary = summary;
        this.message = message;
    }

    public FacesMessage.Severity getSeverity() {
        return severity;
    }

    public String getSummary() {
        return summary;
    }

    public String getMessage() {
        return message;
    }

    public void setSeverity(FacesMessage.Severity severity) {
        this.severity = severity;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
