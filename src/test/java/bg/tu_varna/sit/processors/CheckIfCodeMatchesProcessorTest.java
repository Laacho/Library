package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.my_profile.CheckIfCodesMatchesProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.change_username.ChangeUsernameOperationModel;
import bg.tu_varna.sit.library.models.check_if_codes_matches.CheckIfCodesMatchesOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class CheckIfCodeMatchesProcessorTest {
    @Mock
    private UserCredentialsRepository userCredentialsRepository;
    private CheckIfCodesMatchesProcessor checkIfCodesMatchesProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        checkIfCodesMatchesProcessor =SingletonFactory.getSingletonInstance(CheckIfCodesMatchesProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, checkIfCodesMatchesProcessor);
        assertInstanceOf(CheckIfCodesMatchesOperationModel.class, checkIfCodesMatchesProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.checkIfCodesMatchesProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
