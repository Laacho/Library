package bg.tu_varna.sit.library.core.admin.home_view;

import bg.tu_varna.sit.library.core.BaseProcessor;
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
public class AdminHomeViewProcessor extends BaseProcessor implements AdminHomeViewOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private final DiscardedBooksRepository discardedBooksRepository;
    private final ArchivedRepository archivedRepository;
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final BookRepository bookRepository;
    private final NotificationRepository notificationRepository;
    private static final Logger log = Logger.getLogger(AdminHomeViewProcessor.class);


    private AdminHomeViewProcessor() {
        super();
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
        borrowedBooksRepository = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
        archivedRepository = SingletonFactory.getSingletonInstance(ArchivedRepositoryImpl.class);
        discardedBooksRepository = SingletonFactory.getSingletonInstance(DiscardedBooksRepositoryImpl.class);
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
    }

    @Override
    public Either<Exception, AdminHomeViewOutputModel> process(AdminHomeViewInputModel input) {
        return Try.of(() -> {
                    log.info("Started loading admin home view");
                    int countDiscardedBooks = discardedBooksRepository.findAll().size();
                    int countAllBooks = bookRepository.findAll().size();
                    int countArchivedBooks = archivedRepository.findAll().size();
                    int countReaderProfiles = borrowedBooksRepository.findAll().size();
                    int countUsers = userCredentialsRepository.findAllUsers().size();
                    int countNotification = notificationRepository.findAllAdminNotification().size();
                    AdminHomeViewOutputModel outputModel = AdminHomeViewOutputModel.builder()
                            .countUsers(countUsers)
                            .countAllBooks(countAllBooks)
                            .countArchivedBooks(countArchivedBooks)
                            .countDiscardedBooks(countDiscardedBooks)
                            .countReadersProfiles(countReaderProfiles)
                            .countNotifications(countNotification)
                            .build();
                    log.info("Finished loading admin home view");
                    return outputModel;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
