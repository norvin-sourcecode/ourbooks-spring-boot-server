package cloudcode.v2ourbook.enums;

public enum GetBookStatus {
        STARTED, AGREED, RECEIVED, RETURNED, CANCELLED, DUE, AVAILABLE;



        public static GetBookStatus init(String status) throws Exception {
                switch (status.toLowerCase()){
                        case "started":
                                return STARTED;
                        case "agreed":
                                return AGREED;
                        case "received":
                                return RECEIVED;
                        case "returned":
                                return RETURNED;
                        case "cancelled":
                                return CANCELLED;
                        case "due":
                                return DUE;
                        case "available":
                                return AVAILABLE;
                        default:
                                throw new Exception("bad status");

                }
        }

        public static GetBookStatus init(int status) throws Exception {
                if (status == STARTED.ordinal()) {
                        return STARTED;
        } else if (status == AGREED.ordinal()) {
                        return AGREED;
                } else if (status == RECEIVED.ordinal()) {
                                return RECEIVED;
                } else if (status == RETURNED.ordinal()) {
                                return RETURNED;
                } else if (status == CANCELLED.ordinal()) {
                                return CANCELLED;
                } else if (status == DUE.ordinal()) {
                                return DUE;
                } else if (status == AVAILABLE.ordinal()) {
                        return AVAILABLE;
                } else {
                        throw new Exception("bad status");
                }


        }

}
