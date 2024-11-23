package bg.tu_varna.sit.library.utils.converters.login;

import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
import bg.tu_varna.sit.library.utils.session.UserSession;

public class FromUserCredentialsToUserSession implements Converter<UserCredentials, UserSession> {
    @Override
    public UserSession convert(UserCredentials source) {
        return UserSession.builder()
                .admin(source.getAdmin())
                .email(source.getEmail())
                .password(source.getPassword())
                .username(source.getUsername())
                .dateOfVerification(source.getDateOfVerification())
                .verified(source.getVerified())
                .rating(source.getRating())
                .verificationCode(source.getVerificationCode())
                .build();
    }
}
