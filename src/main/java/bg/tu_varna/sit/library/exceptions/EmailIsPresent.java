package bg.tu_varna.sit.library.exceptions;

public class EmailIsPresent extends BaseException {
    public EmailIsPresent(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
