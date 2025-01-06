package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.search.GetAllPublishersProcessor;
import bg.tu_varna.sit.library.core.user.search.SearchForUserProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.PublisherRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.models.search_for_user.SearchForUserOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class SearchForUserProcessorTest {
    @Mock
    private BookRepository bookRepository;

    private SearchForUserProcessor searchForUserProcessor;

    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        searchForUserProcessor = SingletonFactory.getSingletonInstance(SearchForUserProcessor.class);
    }

    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, searchForUserProcessor);
        assertInstanceOf(SearchForUserOperationModel.class, searchForUserProcessor);
    }

    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.searchForUserProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
