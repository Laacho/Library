package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.settings.ResetPasswordForUserProcessor;
import bg.tu_varna.sit.library.core.admin.settings.SearchUserByUsernameProcess;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.reset_password_for_user.ResetPasswordForUserOperationModel;
import bg.tu_varna.sit.library.models.search_user_by_username.SearchUserByUsernameOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class SearchUserByUsernameProcessorTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;
    private SearchUserByUsernameProcess searchUserByUsernameProcess;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        searchUserByUsernameProcess =SingletonFactory.getSingletonInstance(SearchUserByUsernameProcess.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, searchUserByUsernameProcess);
        assertInstanceOf(SearchUserByUsernameOperationModel.class, searchUserByUsernameProcess);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.searchUserByUsernameProcess.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
