package bg.tu_varna.sit.library.exceptions;

public class NoBooksPresent extends BaseException {
    public NoBooksPresent(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
