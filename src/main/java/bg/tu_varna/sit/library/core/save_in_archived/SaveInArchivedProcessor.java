package bg.tu_varna.sit.library.core.save_in_archived;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.ArchivedBooks;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.enums.BookStatus;
import bg.tu_varna.sit.library.data.repositories.implementations.ArchivedRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.ArchivedRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.models.save_to_archived.SaveInArchivedInputModel;
import bg.tu_varna.sit.library.models.save_to_archived.SaveInArchivedOperationModel;
import bg.tu_varna.sit.library.models.save_to_archived.SaveToArchivedOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

@Processor
public class SaveInArchivedProcessor extends BaseProcessor implements SaveInArchivedOperationModel {
    private static final Logger log = Logger.getLogger(SaveInArchivedProcessor.class);
    private final ArchivedRepository archivedRepository;
    private final BookRepository bookRepository;

    private SaveInArchivedProcessor() {
        this.archivedRepository = SingletonFactory.getSingletonInstance(ArchivedRepositoryImpl.class);
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, SaveToArchivedOutputModel> process(SaveInArchivedInputModel input) {
        return Try.of(()->{
                    log.info("Started saving archived book");
                    ArchivedBooks convert = conversionService.convert(input, ArchivedBooks.class);
                    archivedRepository.save(convert);
                    Book book = input.getBook();
                    book.setBookStatusAfterBorrow(BookStatus.ARCHIVED);
                    book.setBookStatusBeforeBorrow(BookStatus.ARCHIVED);
                    bookRepository.update(book);
                    SaveToArchivedOutputModel output = outputBuilder();
                    log.info("Finished saving archived book");
                    return output;

        }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private  SaveToArchivedOutputModel outputBuilder() {
        return SaveToArchivedOutputModel.builder()
                .message("Successfully saved archived book")
                .build();
    }
}
