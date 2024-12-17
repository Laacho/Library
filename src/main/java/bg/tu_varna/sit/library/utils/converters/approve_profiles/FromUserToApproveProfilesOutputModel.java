package bg.tu_varna.sit.library.utils.converters.approve_profiles;

import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksOutputModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOutputModel;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

@Mapper(from = User.class, to = ApproveProfilesOutputModel.class)
public class FromUserToApproveProfilesOutputModel implements Converter<User, ApproveProfilesOutputModel> {
    @Override
    public ApproveProfilesOutputModel convert(User source) {
        return ApproveProfilesOutputModel.builder()
                .birthdate(source.getBirthdate())
                .email(source.getUserCredentials().getEmail())
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .username(source.getUserCredentials().getUsername())
                .build();
    }
}
