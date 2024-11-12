package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.UserCredentials;

import java.util.Optional;

public interface UserCredentialsRepository extends GenericRepository<UserCredentials>{
    Optional<UserCredentials> findByUsername(String username);
    Optional<UserCredentials> findByEmail(String email);

}
