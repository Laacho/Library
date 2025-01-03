package bg.tu_varna.sit.library.core.user.home_view;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.exceptions.BookNotFound;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdInputModel;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdOperationModel;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

@Processor
public class FindBookByIdProcessor extends BaseProcessor implements FindBookByIdOperationModel {
    private final BookRepository bookRepository;
    private static final Logger log= Logger.getLogger(FindBookByIdProcessor.class);
    private FindBookByIdProcessor() {
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, FindBookByIdOutputModel> process(FindBookByIdInputModel input) {
        return Try.of(() -> {
                    log.info("Started finding book by ID process");
                    Book book = bookRepository.findById(input.getId())
                            .orElseThrow(() -> new BookNotFound("Book Not Found","Book with "+input.getId()+" id has not been found"));
                    CommonBooksProperties convert = conversionService.convert(book, CommonBooksProperties.class);
                    FindBookByIdOutputModel output = FindBookByIdOutputModel.builder().book(convert).build();
                    log.info("Finished finding book by ID process");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
