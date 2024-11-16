package bg.tu_varna.sit.library.core.register;

import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;


public abstract class BaseProcessor {
    protected final ConversionService conversionService;

    protected BaseProcessor()  {
        this.conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
    }
}
