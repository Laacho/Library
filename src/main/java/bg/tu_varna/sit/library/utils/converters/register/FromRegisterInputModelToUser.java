package bg.tu_varna.sit.library.utils.converters.register;

import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;
@Mapper(from = RegisterInputModel.class ,to = User.class)
public class FromRegisterInputModelToUser implements Converter<RegisterInputModel, User> {
    @Override
    public User convert(RegisterInputModel source) {
        return User.builder()
                .birthdate(source.getBirthdate())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
