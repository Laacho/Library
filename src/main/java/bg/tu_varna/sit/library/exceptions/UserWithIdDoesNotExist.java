package bg.tu_varna.sit.library.exceptions;

public class UserWithIdDoesNotExist extends BaseException {
    public UserWithIdDoesNotExist(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
