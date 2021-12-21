package cloudcode.v2ourbook.enums;

public enum BookStatus {
    AVAILABLE, UNAVAILABLE;

    public static BookStatus init(String status) throws Exception {
        switch (status.toLowerCase()){
            case "available":
                return AVAILABLE;
            case "unavailable":
                return UNAVAILABLE;
            default:
                throw new Exception("bad status");

        }
    }

    public static BookStatus init(int status) throws Exception {
        if (status == AVAILABLE.ordinal()) {
            return AVAILABLE;
        } else if (status == UNAVAILABLE.ordinal()) {
            return UNAVAILABLE;
        } else {
            throw new Exception("bad status");
        }


    }
}
