package bg.tu_varna.sit.library.utils.converters.base;

import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class ConversionService {
    private Map<Class<?>, Map<Class<?>, Converter<?, ?>>> converters;
    private ConversionService(){
        converters = new HashMap<>();
    }


    public <F, T> void addConverter(Class<F> from, Class<T> to, Converter<F, T> converter) {
        converters.putIfAbsent(from, new HashMap<>());
        converters.get(from).put(to, converter);
    }
    public void init() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("bg.tu_varna.sit.library");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Mapper.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            Mapper annotation = aClass.getAnnotation(Mapper.class);
            Class<?> from = annotation.from();
            Class<?> to = annotation.to();
            Converter<?, ?> converter = (Converter<?, ?>) aClass.getConstructor().newInstance();
            converters.putIfAbsent(from,new HashMap<>());
            converters.get(from).put(to,converter);
        }
    }
    public <F, T> T convert(F from, Class<T> to) {
        if (from == null) {
            //todo
            throw new RuntimeException();
        }
        Map<Class<?>, Converter<?, ?>> classConverterMap = converters.get(from.getClass());
        if (classConverterMap == null) {
            //todo
            throw new RuntimeException();
        }
        Converter<F, T> converter = (Converter<F, T>) classConverterMap.get(to);
        if (converter == null)
            //todo
            throw new RuntimeException();
        return converter.convert(from);
    }
}
