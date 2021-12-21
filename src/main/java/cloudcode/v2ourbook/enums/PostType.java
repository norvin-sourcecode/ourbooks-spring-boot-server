package cloudcode.v2ourbook.enums;

public enum PostType {
    NEWBOOK("N"),BOOKMARKED("B"),LIKED("L"),FAVOURITE("F");

    private String code;

    private PostType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}