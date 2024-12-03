package bg.tu_varna.sit.library.core.admin_hello_view;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.login.LoginProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.*;
import bg.tu_varna.sit.library.data.repositories.interfaces.*;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewInputModel;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewOperationModel;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

@Processor
public class AdminHelloViewProcessor extends BaseProcessor implements AdminHomeViewOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private final DiscardedBooksRepository discardedBooksRepository;
    private final ArchivedRepository archivedRepository;
    private final ReaderProfileRepository readerProfileRepository;
    private final BookRepository bookRepository;
    private static final Logger log = Logger.getLogger(AdminHelloViewProcessor.class);


    private AdminHelloViewProcessor() {
        super();
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
        readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImpl.class);
        archivedRepository = SingletonFactory.getSingletonInstance(ArchivedRepositoryImpl.class);
        discardedBooksRepository = SingletonFactory.getSingletonInstance(DiscardedBooksRepositoryImpl.class);
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, AdminHomeViewOutputModel> process(AdminHomeViewInputModel input) {
        return Try.of(() -> {
                    log.info("start admin home view");
                    int countDiscardedBooks = discardedBooksRepository.findAll().orElseThrow(() -> new RuntimeException()).size();
                    int countAllBooks = bookRepository.findAll().orElseThrow(() -> new RuntimeException()).size();
                    int countArchivedBooks = archivedRepository.findAll().orElseThrow(() -> new RuntimeException()).size();
                    int countReaderProfiles = readerProfileRepository.findAll().orElseThrow(() -> new RuntimeException()).size();
                    int countUsers = userCredentialsRepository.findAllUsers().orElseThrow(() -> new RuntimeException()).size();
                    AdminHomeViewOutputModel outputModel = AdminHomeViewOutputModel.builder()
                            .countUsers(countUsers)
                            .countAllBooks(countAllBooks)
                            .countArchivedBooks(countArchivedBooks)
                            .countDiscardedBooks(countDiscardedBooks)
                            .countReadersProfiles(countReaderProfiles)
                            .build();
                    log.info("end "+outputModel);
                    return outputModel;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
