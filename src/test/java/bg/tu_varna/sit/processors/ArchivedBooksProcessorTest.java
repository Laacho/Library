package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.approve_profiles.ApproveProfilesProcessor;
import bg.tu_varna.sit.library.core.admin.archived_books.ArchivedBooksProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.ArchivedRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.ArchivedRepository;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOperationModel;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ArchivedBooksProcessorTest {

    @Mock
    private ArchivedRepository archivedRepository;
    private ArchivedBooksProcessor archivedBooksProcessor;
    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        archivedBooksProcessor=SingletonFactory.getSingletonInstance(ArchivedBooksProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ArchivedRepositoryImpl.class)).thenReturn(archivedRepository);
        }
    }
    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class,archivedBooksProcessor);
        assertInstanceOf(ArchivedBooksOperationModel.class,archivedBooksProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.archivedBooksProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }
}
