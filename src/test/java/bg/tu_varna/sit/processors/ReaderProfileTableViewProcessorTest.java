package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.overdue_books.OverdueBooksProcessor;
import bg.tu_varna.sit.library.core.admin.reader_profile.ReaderProfileTableViewProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileTableViewOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ReaderProfileTableViewProcessorTest {

    @Mock
    private ReaderProfileRepository readerProfileRepository;

    private ReaderProfileTableViewProcessor readerProfileTableViewProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        readerProfileTableViewProcessor =SingletonFactory.getSingletonInstance(ReaderProfileTableViewProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class)).thenReturn(readerProfileRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, readerProfileTableViewProcessor);
        assertInstanceOf(ReaderProfileTableViewOperationModel.class, readerProfileTableViewProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.readerProfileTableViewProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
