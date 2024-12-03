package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.Genre;

import java.util.Optional;

public interface GenreRepository extends GenericRepository<Genre> {
    Optional<Genre> findByName(String name);
}
