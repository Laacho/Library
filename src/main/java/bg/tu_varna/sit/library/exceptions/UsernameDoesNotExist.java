package bg.tu_varna.sit.library.exceptions;

public class UsernameDoesNotExist extends BaseException {
    public UsernameDoesNotExist(String header, String message) {
    super(header,message);
    }
}
