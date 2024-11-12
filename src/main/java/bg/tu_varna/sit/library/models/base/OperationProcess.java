package bg.tu_varna.sit.library.models.base;


import bg.tu_varna.sit.library.models.ExceptionManager;
import io.vavr.control.Either;
public interface OperationProcess<I extends OperationInput, O extends OperationOutput> {
    Either<Exception,O> process(I input);
}
