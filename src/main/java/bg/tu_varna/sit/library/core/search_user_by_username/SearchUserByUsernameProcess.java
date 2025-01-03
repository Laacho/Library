package bg.tu_varna.sit.library.core.search_user_by_username;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.search_user_by_username.SearchUserByUsernameInputModel;
import bg.tu_varna.sit.library.models.search_user_by_username.SearchUserByUsernameOperationModel;
import bg.tu_varna.sit.library.models.search_user_by_username.SearchUserByUsernameOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

@Processor
public class SearchUserByUsernameProcess extends BaseProcessor implements SearchUserByUsernameOperationModel {
    private static final Logger log = Logger.getLogger(SearchUserByUsernameProcess.class);
    private final UserCredentialsRepository userCredentialsRepository;

    private SearchUserByUsernameProcess() {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class) ;
    }

    @Override
    public Either<Exception, SearchUserByUsernameOutputModel> process(SearchUserByUsernameInputModel input) {
        return Try.of(()->{
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(input.getUsername())
                            .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found","User with username: " +input.getUsername()+" has not been found"));
                    SearchUserByUsernameOutputModel build = SearchUserByUsernameOutputModel.builder()
                            .userCredentials(userCredentials)
                            .build();
                    log.info("Successfully found user by username!");
                    return build;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
