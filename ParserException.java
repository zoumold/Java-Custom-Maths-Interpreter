package ce326.hw1;

public class ParserException extends Exception{
    String message;

    /*For REL mode */
    public ParserException(String baseMessage){
        this.message = baseMessage;
    }

    /*For File mode */
    public ParserException(String baseMessage, int line){
        this.message = "Line " + line + ": " + baseMessage;
    }

    public String getMessage(){
        return this.message;
    }
}