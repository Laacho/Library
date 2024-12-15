package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;

import java.util.List;
import java.util.Optional;

public interface UserCredentialsRepository extends GenericRepository<UserCredentials>{
    Optional<UserCredentials> findByUsername(String username);
    Optional<UserCredentials> findByEmail(String email);
    void update(UserCredentials userCredentials);
    List<UserCredentials> findAllUsers();
    Optional<UserCredentials> findByUser(User user);

}
