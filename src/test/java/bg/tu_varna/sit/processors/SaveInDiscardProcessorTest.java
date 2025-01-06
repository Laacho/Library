package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.update_status.SaveInDiscardProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.DiscardedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.DiscardedBooksRepository;
import bg.tu_varna.sit.library.models.save_to_discard.SaveToDiscardOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class SaveInDiscardProcessorTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private DiscardedBooksRepository discardedBooksRepository;
    private SaveInDiscardProcessor saveInArchivedProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        saveInArchivedProcessor =SingletonFactory.getSingletonInstance(SaveInDiscardProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(DiscardedBooksRepositoryImpl.class)).thenReturn(discardedBooksRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, saveInArchivedProcessor);
        assertInstanceOf(SaveToDiscardOperationModel.class, saveInArchivedProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.saveInArchivedProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
