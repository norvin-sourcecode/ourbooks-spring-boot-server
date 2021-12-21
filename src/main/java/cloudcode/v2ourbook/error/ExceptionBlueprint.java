package cloudcode.v2ourbook.error;

public class ExceptionBlueprint extends Exception{

    private String reason;
    private String message;
    private Integer howBadIsIt;

    public ExceptionBlueprint(String reason, String message, Integer howBadIsIt) {
        super("reason: "+reason+", message: "+message+", how bad is it: "+howBadIsIt+"/10");
    }
}
