package bg.tu_varna.sit.library.core.users_table_view;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Archived;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksOutputModel;
import bg.tu_varna.sit.library.models.archived_books.BooksData;
import bg.tu_varna.sit.library.models.users_table_view.UsersData;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewInputModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewOperationModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Processor
public class UsersTableViewProcessor extends BaseProcessor implements UsersTableViewOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;

    private UsersTableViewProcessor() {
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, UsersTableViewOutputModel> process(UsersTableViewInputModel input) {
        return Try.of(() -> {
                    List<UserCredentials> userCredentials = userCredentialsRepository.findAllUsers();
                    List<UsersData> usersData = getUsersData(userCredentials);
                    UsersTableViewOutputModel output = UsersTableViewOutputModel.builder().usersData(usersData).build();
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
    @NotNull
    private List<UsersData> getUsersData(List<UserCredentials> users) {
        List<UsersData> usersData = new ArrayList<>();
        for (UserCredentials user : users) {
            usersData.add(conversionService.convert(user, UsersData.class));
        }
        return usersData;
    }
}
