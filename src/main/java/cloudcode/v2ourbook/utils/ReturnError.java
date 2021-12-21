package cloudcode.v2ourbook.utils;

import java.util.HashMap;
import java.util.Map;

public class ReturnError {
    String action;
    String message;
    Map payload = new HashMap<String, String>();
    boolean error = true;

    public ReturnError() {
    }

    public ReturnError(String action, String message, Map payload) {
        this.action = action;
        this.message = message;
        this.payload = payload != null ? payload : new HashMap();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map getPayload() {
        return payload;
    }

    public void setPayload(Map payload) {
        this.payload = payload;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
