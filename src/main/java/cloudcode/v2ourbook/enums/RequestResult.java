package cloudcode.v2ourbook.enums;

public enum RequestResult {
    DENIED, APPROVED, PENDING;

    public static RequestResult init(String status) throws Exception {
        switch (status.toLowerCase()){
            case "denied":
                return DENIED;
            case "approved":
                return APPROVED;    
            case "pending":
                return PENDING;
            default:
                throw new Exception("bad status");
        }
    }

    public static RequestResult init(int status) throws Exception {
        if (status == DENIED.ordinal()) {
            return DENIED;
        } else if (status == APPROVED.ordinal()) {
            return APPROVED;
        } else if (status == PENDING.ordinal()) {
            return PENDING;
        } else {
            throw new Exception("bad status");
        }
    }
}
