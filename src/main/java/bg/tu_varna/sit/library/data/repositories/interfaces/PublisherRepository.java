package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.Publisher;

import java.util.Optional;

public interface PublisherRepository extends GenericRepository<Publisher> {
    Optional<Publisher> findByName(String name);
}
