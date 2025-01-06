package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.update_status.SaveInArchivedProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.ArchivedRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.ArchivedRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.models.find_book_by_inventory_number.FindBookByInventoryNumberOperationModel;
import bg.tu_varna.sit.library.models.save_to_archived.SaveInArchivedOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class SaveInArchivedProcessorTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private ArchivedRepository archivedRepository;
    private SaveInArchivedProcessor saveInArchivedProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        saveInArchivedProcessor =SingletonFactory.getSingletonInstance(SaveInArchivedProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ArchivedRepositoryImpl.class)).thenReturn(archivedRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, saveInArchivedProcessor);
        assertInstanceOf(SaveInArchivedOperationModel.class, saveInArchivedProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.saveInArchivedProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
