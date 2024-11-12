package bg.tu_varna.sit.library.common.converters.common;

//F=From
//T=to
public interface Converter<F, T> {
    T convert(F source);
}
