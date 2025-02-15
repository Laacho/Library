package bg.tu_varna.sit.library.data.repositories.interfaces;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    Long save(T entity);
    void saveAll(List<T> entities);

    Optional<T> findById(Long id);
    List<T> findAll();

    Optional<T> deleteById(Long id);

}
