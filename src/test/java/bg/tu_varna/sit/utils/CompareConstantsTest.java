package bg.tu_varna.sit.utils;

import org.junit.jupiter.api.Test;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompareConstantsTest {

    @Test
    void compare(){
        assertEquals(ADMIN_ALL_BOOKS_VIEW,"/bg/tu_varna/sit/library/presentation.views/admin/all_books_view/pages/all-books-view.fxml");
        assertEquals(ADMIN_DISCARDED_BOOKS,"/bg/tu_varna/sit/library/presentation.views/admin/discarded_books_view/pages/discarded-books-view.fxml");
        assertEquals(ADMIN_ARCHIVED_BOOKS,"/bg/tu_varna/sit/library/presentation.views/admin/archived_books_view/pages/archived-books-view.fxml");
        assertEquals(ADMIN_USERS_TABLE_VIEW,"/bg/tu_varna/sit/library/presentation.views/admin/users_table_view/pages/users-table-view.fxml");
        assertEquals(ADMIN_NOTIFICATIONS,"/bg/tu_varna/sit/library/presentation.views/admin/notification_view/pages/notification-view.fxml");
        assertEquals(ADMIN_READERS_TABLE_VIEW,"/bg/tu_varna/sit/library/presentation.views/admin/reader_profile_view/pages/reader-profile-view.fxml");
        assertEquals(ADMIN_PATH_TO_SAVE_IMAGES,"src/main/resources/bg/tu_varna/sit/library/presentation.views/admin/add_book/images/book_images");
        assertEquals(ADMIN_RETURN_TO_HOME_VIEW_AFTER_APPROVING_BOOKS,"/bg/tu_varna/sit/library/presentation.views/admin/home_view/pages/admin-home-view.fxml");

        assertEquals(ADMIN_HOME_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/home_view/pages/admin-home-view.fxml");
        assertEquals(ADMIN_ADD_BOOK_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/add_book/pages/add-book-view.fxml");
        assertEquals(ADMIN_UPDATE_STATUS_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/update_status/pages/update-status-view.fxml");
        assertEquals(ADMIN_APPROVE_BOOKS_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/approve_books/pages/approve-books-view.fxml");
        assertEquals(ADMIN_APPROVE_PROFILES_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/approve_profiles_view/pages/approve-profiles.fxml");
        assertEquals(ADMIN_OVERDUE_BOOKS_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/overdue_books_view/pages/overdue-books.fxml");
        assertEquals(ADMIN_RETURN_BOOKS_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/return_book_view/pages/return-books.fxml");
        assertEquals(ADMIN_SEND_EMAIL_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/send_email_view/pages/send-email-view.fxml");
        assertEquals(ADMIN_SETTINGS_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/settings/pages/settings-view.fxml");
        assertEquals(ADMIN_SEARCH_VIEW, "/bg/tu_varna/sit/library/presentation.views/admin/search/pages/search-view.fxml");
        assertEquals(LIBRARY_HOME_VIEW, "/bg/tu_varna/sit/library/presentation.views/library_home_view/pages/library-home-view.fxml");

        assertEquals(USER_HOME_VIEW, "/bg/tu_varna/sit/library/presentation.views/user/user_home_view/pages/user-home-view.fxml");
        assertEquals(USER_SEARCH_FOR_USER_VIEW, "/bg/tu_varna/sit/library/presentation.views/user/search_for_user/pages/search-for-user-view.fxml");
        assertEquals(USER_READER_PROFILE_VIEW, "/bg/tu_varna/sit/library/presentation.views/user/reader_profile/pages/reader-profile-view.fxml");
        assertEquals(USER_ALL_BOOKS_VIEW, "/bg/tu_varna/sit/library/presentation.views/user/user_all_books_view/pages/user-all-books-view.fxml");
        assertEquals(USER_ABOUT_US_VIEW, "/bg/tu_varna/sit/library/presentation.views/user/about_us/pages/about-us-view.fxml");
        assertEquals(USER_MY_PROFILE_VIEW, "/bg/tu_varna/sit/library/presentation.views/user/my_profile/pages/my-profile-view.fxml");
        assertEquals(USER_NOTIFICATIONS_VIEW, "/bg/tu_varna/sit/library/presentation.views/user/notifications/pages/notifications-view.fxml");
        assertEquals(USER_BORROW_CART_VIEW, "/bg/tu_varna/sit/library/presentation.views/user/borrow_cart/pages/borrow-cart-view.fxml");
        assertEquals(USER_BOOK_DATA_FOR_USER, "/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/pages/book-data-for-user-view.fxml");

        assertEquals(LOGGING_CHANGE_TO_LOGIN_VIEW, "/bg/tu_varna/sit/library/presentation.views/logging/login/pages/login-view.fxml");
        assertEquals(LOGGING_CHANGE_TO_REGISTER_VIEW, "/bg/tu_varna/sit/library/presentation.views/logging/register/pages/register-view.fxml");
        assertEquals(LOGGING_CHANGE_TO_CONFIRM_REGISTRATION_VIEW, "/bg/tu_varna/sit/library/presentation.views/logging/confirm_registration/pages/confirm-registration.fxml");

    }



}