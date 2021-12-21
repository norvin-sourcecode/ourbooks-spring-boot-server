package cloudcode.v2ourbook.enums;

public enum VisibilityType {
    USER("U"),FRIENDS("F"),BOOKCLUBS("B");

    private String code;

    private VisibilityType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}