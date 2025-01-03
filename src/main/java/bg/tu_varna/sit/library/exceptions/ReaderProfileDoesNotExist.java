package bg.tu_varna.sit.library.exceptions;

public class ReaderProfileDoesNotExist extends BaseException {
    public ReaderProfileDoesNotExist(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
