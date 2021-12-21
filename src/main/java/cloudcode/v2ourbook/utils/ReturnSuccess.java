package cloudcode.v2ourbook.utils;

import java.util.HashMap;
import java.util.Map;

public class ReturnSuccess {
    String action;
    String message;
    Map payload = new HashMap<String, String>();
    boolean success = true; boolean error = false;

    public ReturnSuccess() {
    }

    public ReturnSuccess(String action, String message, Map payload) {
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
