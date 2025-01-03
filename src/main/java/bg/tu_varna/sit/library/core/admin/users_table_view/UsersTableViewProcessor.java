package bg.tu_varna.sit.library.core.admin.users_table_view;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.users_table_view.UsersData;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewInputModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewOperationModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Processor
public class UsersTableViewProcessor extends BaseProcessor implements UsersTableViewOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log= Logger.getLogger(UsersTableViewProcessor.class);
    private UsersTableViewProcessor() {
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, UsersTableViewOutputModel> process(UsersTableViewInputModel input) {
        return Try.of(() -> {
                    log.info("Started processing users table view");
                    List<UserCredentials> userCredentials = userCredentialsRepository.findAllUsers();
                    List<UsersData> usersData = getUsersData(userCredentials);
                    UsersTableViewOutputModel output = UsersTableViewOutputModel.builder().usersData(usersData).build();
                    log.info("Finished processing users table view");
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
