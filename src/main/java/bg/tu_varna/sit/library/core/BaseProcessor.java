package bg.tu_varna.sit.library.core;

import bg.tu_varna.sit.library.models.ExceptionManager;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;


public abstract class BaseProcessor {
    protected final ConversionService conversionService;
    protected final ExceptionManager exceptionManager;

    protected BaseProcessor()  {
        this.conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
        this.exceptionManager = SingletonFactory.getSingletonInstance(ExceptionManager.class);
    }
}
