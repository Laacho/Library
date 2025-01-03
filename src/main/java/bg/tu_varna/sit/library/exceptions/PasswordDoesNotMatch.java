package bg.tu_varna.sit.library.exceptions;

public class PasswordDoesNotMatch extends BaseException {

    public PasswordDoesNotMatch(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
