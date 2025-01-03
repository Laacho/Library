package bg.tu_varna.sit.library.exceptions;

public class BaseException extends Exception{
    private String baseMessage;
    private String header;
    public BaseException(String header,String baseMessage) {
        this.baseMessage = baseMessage;
        this.header = header;
    }

    public String getBaseMessage() {
        return baseMessage;
    }

    public String getHeader() {
        return header;
    }
}
