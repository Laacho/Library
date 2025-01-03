package bg.tu_varna.sit.library.exceptions;

public class UserNotFound extends BaseException {
    public UserNotFound(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
