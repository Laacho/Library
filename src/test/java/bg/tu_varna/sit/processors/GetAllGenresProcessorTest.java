package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.search.GetAllAuthorsProcessor;
import bg.tu_varna.sit.library.core.user.search.GetAllGenresProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.GenreRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenreRepository;
import bg.tu_varna.sit.library.models.get_all_authors.GetAllAuthorsOperationModel;
import bg.tu_varna.sit.library.models.get_all_genres.GetAllGenresOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class GetAllGenresProcessorTest {
    @Mock
    private GenreRepository genreRepository;

    private GetAllGenresProcessor getAllGenresProcessor;

    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        getAllGenresProcessor = SingletonFactory.getSingletonInstance(GetAllGenresProcessor.class);
    }

    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class)).thenReturn(genreRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, getAllGenresProcessor);
        assertInstanceOf(GetAllGenresOperationModel.class, getAllGenresProcessor);
    }

    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.getAllGenresProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
