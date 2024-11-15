package bg.tu_varna.sit.library.common.converters.register;

import bg.tu_varna.sit.library.common.Hasher;
import bg.tu_varna.sit.library.common.converters.base.Converter;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;

public class FromRegisterInputModelToUserCredentials implements Converter<RegisterInputModel, UserCredentials> {
    @Override
    public UserCredentials convert(RegisterInputModel source) {
        return UserCredentials.builder()
                .email(source.getEmail())
                .password(Hasher.hashPassword(source.getPassword()))
                .username(source.getUsername())
                .verified(false)
                .build();
    }
}
