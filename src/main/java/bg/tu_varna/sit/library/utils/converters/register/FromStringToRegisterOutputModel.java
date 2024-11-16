package bg.tu_varna.sit.library.utils.converters.register;

import bg.tu_varna.sit.library.utils.converters.base.Converter;
import bg.tu_varna.sit.library.models.register.RegisterOutputModel;

public class FromStringToRegisterOutputModel implements Converter<String, RegisterOutputModel> {
    @Override
    public RegisterOutputModel convert(String source) {
        return RegisterOutputModel.builder().message(source).build();
    }
}
