package bg.tu_varna.sit.library.exceptions;

public class AuthorDoesNotExist extends BaseException {
    public AuthorDoesNotExist(String header, String message) {
        super(header, message);
    }
}
