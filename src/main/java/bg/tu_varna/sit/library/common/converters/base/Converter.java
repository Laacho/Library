package bg.tu_varna.sit.library.common.converters.base;

//F=From
//T=to
public interface Converter<F, T> {
    T convert(F source);
}
