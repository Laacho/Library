package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.User;

import java.util.Optional;

public interface ReaderProfileRepository extends GenericRepository<ReaderProfile> {
    Optional<ReaderProfile> findByUser(User user);
    void update(ReaderProfile readerProfile);
}
