package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.return_books.UpdateReturnedBooksProcessor;
import bg.tu_varna.sit.library.core.admin.search.SearchProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.*;
import bg.tu_varna.sit.library.data.repositories.interfaces.*;
import bg.tu_varna.sit.library.models.search.SearchOperationModel;
import bg.tu_varna.sit.library.models.update_returned_books.UpdateReturnedBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class SearchProcessorTest {

    @Mock
    private BookRepository bookRepository;


    private SearchProcessor searchProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        searchProcessor =SingletonFactory.getSingletonInstance(SearchProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, searchProcessor);
        assertInstanceOf(SearchOperationModel.class, searchProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.searchProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
