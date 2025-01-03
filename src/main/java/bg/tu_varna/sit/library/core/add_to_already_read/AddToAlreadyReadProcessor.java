package bg.tu_varna.sit.library.core.add_to_already_read;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.add_to_already_read.AddToAlreadyReadInputModel;
import bg.tu_varna.sit.library.models.add_to_already_read.AddToAlreadyReadOperationModel;
import bg.tu_varna.sit.library.models.add_to_already_read.AddToAlreadyReadOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.Set;

@Processor
public class AddToAlreadyReadProcessor extends BaseProcessor implements AddToAlreadyReadOperationModel {
    private static final Logger log= Logger.getLogger(AddToAlreadyReadProcessor.class);
    private final ReaderProfileRepository readerProfileRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final BookRepository bookRepository;

    public AddToAlreadyReadProcessor() {
        this.readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }
    @Override
    public Either<Exception, AddToAlreadyReadOutputModel> process(AddToAlreadyReadInputModel input) {
        return Try.of(()->{
                    log.info("Started adding book to already read");
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    UserCredentials loggedUser = userCredentialsRepository.findByUsername(userSession.getUsername()).get();
                    ReaderProfile readerProfile = readerProfileRepository.
                            findByUser(loggedUser.getUser())
                            .orElseThrow(()->new RuntimeException("No reader profile found!"));
                    //todo
                    Set<Book> readBooks = readerProfile.getReadBooks();
                    Book book = bookRepository.findByInventoryNumber(input.getCommonBooksProperties()
                                    .getInventoryNumber())
                            .orElseThrow(()->new RuntimeException("No book found!"));
                    if(input.getWantToDelete()){
                        readBooks.removeIf(book1->book1.getInventoryNumber()
                                .equals(input.getCommonBooksProperties()
                                        .getInventoryNumber()));
                    }
                    else {
                        readBooks.add(book);
                    }
                    readerProfile.setReadBooks(readBooks);
                    readerProfileRepository.update(readerProfile);
                    AddToAlreadyReadOutputModel output = outputBuilder();
                    log.info("Finished adding book to already read");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);

    }

    private AddToAlreadyReadOutputModel outputBuilder() {
        return AddToAlreadyReadOutputModel.builder()
                .message("Successfully added book to read")
                .build();
    }
}
