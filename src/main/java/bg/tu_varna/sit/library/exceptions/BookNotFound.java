package bg.tu_varna.sit.library.exceptions;

public class BookNotFound extends BaseException {
    public BookNotFound(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
