package bg.tu_varna.sit.library.core.save_to_discard;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.DiscardedBooks;
import bg.tu_varna.sit.library.data.enums.BookStatus;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.DiscardedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.DiscardedBooksRepository;
import bg.tu_varna.sit.library.models.save_to_discard.SaveToDiscardInputModel;
import bg.tu_varna.sit.library.models.save_to_discard.SaveToDiscardOperationModel;
import bg.tu_varna.sit.library.models.save_to_discard.SaveToDiscardOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

@Processor
public class SaveInDiscardProcessor extends BaseProcessor implements SaveToDiscardOperationModel {
    private static final Logger log = Logger.getLogger(SaveInDiscardProcessor.class);

    private final DiscardedBooksRepository discardedBooksRepository;
    private final BookRepository bookRepository;

    private SaveInDiscardProcessor() {
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
        this.discardedBooksRepository = SingletonFactory.getSingletonInstance(DiscardedBooksRepositoryImpl.class);
    }

    @Override
    public Either<Exception, SaveToDiscardOutputModel> process(SaveToDiscardInputModel input) {
        return Try.of(()->{
                log.info("Started saving book in discarded");
                    DiscardedBooks convert = conversionService.convert(input, DiscardedBooks.class);
                    discardedBooksRepository.save(convert);
                    Book book = input.getBook();
                    book.setBookStatusBeforeBorrow(BookStatus.DISCARDED);
                    book.setBookStatusAfterBorrow(BookStatus.DISCARDED);
                    bookRepository.update(book);
                    SaveToDiscardOutputModel output = outputBuilder();
                    log.info("Finished saving book in discarded");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
    private SaveToDiscardOutputModel outputBuilder() {
        return SaveToDiscardOutputModel.builder()
                .message("Successfully saved discarded book")
                .build();
    }
}
