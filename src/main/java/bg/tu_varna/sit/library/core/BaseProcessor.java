package bg.tu_varna.sit.library.core;

import bg.tu_varna.sit.library.exceptions.ValidatorException;
import bg.tu_varna.sit.library.models.ExceptionManager;
import bg.tu_varna.sit.library.models.base.OperationInput;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public abstract class BaseProcessor {
    protected final ConversionService conversionService;
    protected final ExceptionManager exceptionManager;
    private final Validator validator=createValidator();
    protected BaseProcessor()  {
        this.conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
        this.exceptionManager = SingletonFactory.getSingletonInstance(ExceptionManager.class);
    }

    private Validator createValidator() {
            ValidatorFactory factory = Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory();
            return factory.getValidator();
    }
        protected <I extends OperationInput> void validate(I input) throws ValidatorException{
            Set<ConstraintViolation<I>> violationSet = validator.validate(input);
        if(!violationSet.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<I> iConstraintViolation : violationSet) {
                String message = iConstraintViolation.getMessage();
                String fields = iConstraintViolation.getPropertyPath().toString();
                sb.append(fields)
                        .append(": ")
                        .append(message)
                        .append("\n");
            }
           throw new ValidatorException("Invalid input",sb.toString());
        }
    }
}
