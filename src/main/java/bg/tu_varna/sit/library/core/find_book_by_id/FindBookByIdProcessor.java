package bg.tu_varna.sit.library.core.find_book_by_id;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdInputModel;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdOperationModel;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;

@Processor
public class FindBookByIdProcessor extends BaseProcessor implements FindBookByIdOperationModel {
    private final BookRepository bookRepository;

    private FindBookByIdProcessor() {
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, FindBookByIdOutputModel> process(FindBookByIdInputModel input) {
        return Try.of(() -> {
                    Book book = bookRepository.findById(input.getId()).orElseThrow(() -> new RuntimeException());//todo;
                    CommonBooksProperties convert = conversionService.convert(book, CommonBooksProperties.class);
                    return FindBookByIdOutputModel.builder().book(convert).build();
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
