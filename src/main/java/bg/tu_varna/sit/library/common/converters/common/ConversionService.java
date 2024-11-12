package bg.tu_varna.sit.library.common.converters.common;

import java.util.HashMap;
import java.util.Map;

public class ConversionService {
    private Map<Class<?>, Map<Class<?>, Converter<?, ?>>> converters;

    public ConversionService() {
        this.converters = new HashMap<>();
    }

    public<F, T> void addConverter(Class<F> from,Class<T> to,Converter<F,T> converter){
        converters.putIfAbsent(from,new HashMap<>());
        converters.get(from).put(to,converter);
    }

    public <F,T> T convert(F from, Class<T> to){
        if(from==null){
            //todo
            throw new RuntimeException();
        }
        Map<Class<?>, Converter<?, ?>> classConverterMap = converters.get(from.getClass());
        if(classConverterMap==null){
            //todo
            throw new RuntimeException();
        }
        Converter<F, T> converter = (Converter<F, T>) classConverterMap.get(to);
        if(converter==null)
            //todo
            throw new RuntimeException();
        return converter.convert(from);
    }
}