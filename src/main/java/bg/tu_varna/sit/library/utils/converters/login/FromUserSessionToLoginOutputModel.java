package bg.tu_varna.sit.library.utils.converters.login;

import bg.tu_varna.sit.library.models.login.LoginOutputModel;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
import bg.tu_varna.sit.library.utils.session.UserSession;
@Mapper(from = UserSession.class, to = LoginOutputModel.class)
public class FromUserSessionToLoginOutputModel implements Converter<UserSession, LoginOutputModel> {
    @Override
    public LoginOutputModel convert(UserSession source) {
       return LoginOutputModel.builder()
                .userSession(source)
                .build();
    }
}
