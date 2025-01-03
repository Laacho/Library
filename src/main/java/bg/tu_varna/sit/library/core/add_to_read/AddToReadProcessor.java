package bg.tu_varna.sit.library.core.add_to_read;

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
import bg.tu_varna.sit.library.models.add_to_favorites.AddToFavoriteOutputModel;
import bg.tu_varna.sit.library.models.add_to_read.AddToReadInputModel;
import bg.tu_varna.sit.library.models.add_to_read.AddToReadOperationModel;
import bg.tu_varna.sit.library.models.add_to_read.AddToReadOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.Set;

@Processor
public class AddToReadProcessor extends BaseProcessor implements AddToReadOperationModel {
    private static final Logger log = Logger.getLogger(AddToReadProcessor.class);
    private final ReaderProfileRepository readerProfileRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final BookRepository bookRepository;

    public AddToReadProcessor() {
        this.readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }
    @Override
    public Either<Exception, AddToReadOutputModel> process(AddToReadInputModel input) {
        return Try.of(()->{
                log.info("Started adding book to read");
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    UserCredentials loggedUser = userCredentialsRepository.findByUsername(userSession.getUsername()).get();
                    ReaderProfile readerProfile = readerProfileRepository.
                            findByUser(loggedUser.getUser())
                            .orElseThrow(()->new RuntimeException("No reader profile found!"));
                    Set<Book> wantToRead = readerProfile.getWantToRead();
                    Book book = bookRepository.findByInventoryNumber(input.getCommonBooksProperties().getInventoryNumber())
                            .orElseThrow(() -> new RuntimeException("No book found!"));
                                //todo
                    if(input.getWantsToDelete()){
                        wantToRead.removeIf(book1 -> book1.getInventoryNumber()
                                                            .equals(book.getInventoryNumber()));
                    }
                    else {
                        wantToRead.add(book);
                    }
                    readerProfile.setWantToRead(wantToRead);
                    readerProfileRepository.update(readerProfile);
                    AddToReadOutputModel output = outputBuilder();
                    log.info("Finished adding/removing book from to read");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private AddToReadOutputModel outputBuilder() {
        return AddToReadOutputModel.builder()
                .message("Successfully added to want to read")
                .build();
    }
}
