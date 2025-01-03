package bg.tu_varna.sit.library.exceptions;

public class NewPasswordSameAsTheOldOne extends BaseException {
    public NewPasswordSameAsTheOldOne(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
