package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.update_status.FindBookByInventoryNumberProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.models.find_book_by_inventory_number.FindBookByInventoryNumberOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class FindBookByInventoryNumberProcessorTest {

    @Mock
    private BookRepository bookRepository;
    private FindBookByInventoryNumberProcessor findBookByInventoryNumberProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        findBookByInventoryNumberProcessor =SingletonFactory.getSingletonInstance(FindBookByInventoryNumberProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, findBookByInventoryNumberProcessor);
        assertInstanceOf(FindBookByInventoryNumberOperationModel.class, findBookByInventoryNumberProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.findBookByInventoryNumberProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
