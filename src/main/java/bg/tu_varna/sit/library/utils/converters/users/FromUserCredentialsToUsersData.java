package bg.tu_varna.sit.library.utils.converters.users;

import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.models.users_table_view.UsersData;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

public class FromUserCredentialsToUsersData implements Converter<UserCredentials, UsersData> {
    @Override
    public UsersData convert(UserCredentials source) {
        return UsersData.builder()
                .email(source.getEmail())
                .dateOfVerification(source.getDateOfVerification())
                .firstName(source.getUser().getFirstName())
                .username(source.getUsername())
                .lastName(source.getUser().getLastName())
                .rating(source.getRating())
                .birthdate(source.getUser().getBirthdate())
                .verified(source.getVerified())
                .build();
    }
}
