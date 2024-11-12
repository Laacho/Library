package bg.tu_varna.sit.library.common.converters.register;

import bg.tu_varna.sit.library.common.converters.common.Converter;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;

public class FromRegisterInputModelToUser implements Converter<RegisterInputModel, User> {
    @Override
    public User convert(RegisterInputModel source) {
        return User.builder()
                .admin(false)
                .birthdate(source.getBirthdate())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
