package bg.tu_varna.sit.library.exceptions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsernameAlreadyExists extends BaseException {
    public UsernameAlreadyExists(String header, String baseMessage) {
        super(header, baseMessage);
    }
}
