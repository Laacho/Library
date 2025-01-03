package bg.tu_varna.sit.library.exceptions;

public class IncorrectVerificationCode extends BaseException {
    public IncorrectVerificationCode(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
