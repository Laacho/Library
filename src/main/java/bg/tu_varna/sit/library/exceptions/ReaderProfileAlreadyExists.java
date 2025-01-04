package bg.tu_varna.sit.library.exceptions;

public class ReaderProfileAlreadyExists extends BaseException {
    public ReaderProfileAlreadyExists(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
