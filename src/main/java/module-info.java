module bg.tu_varna.sit.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.hibernate.orm.core;
    // requires java.persistence;
    requires static lombok;
    requires annotations;
    requires jakarta.validation;
    requires log4j;
    requires io.vavr;
    requires org.apache.commons.lang3;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    requires org.reflections;
    requires java.desktop;
    requires java.sql;
    requires java.naming;
    requires org.hibernate.validator;
    requires jakarta.persistence;


    opens bg.tu_varna.sit.library.models.add_book to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.add_genre to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.add_to_already_read to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.add_to_favorites to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.add_to_read to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.approve_profiles to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.borrow_books to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.change_password to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.change_username to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.check_if_book_exists_in_already_read to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.check_if_book_exists_in_to_read to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.check_if_codes_matches to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.check_if_reader_profile_exists to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.confirm_registration to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.create_reader_profile to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.delete_user to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.find_book_by_id to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.find_book_by_inventory_number to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.get_reader_profile to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.login to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.promote_user to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.register to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.remove_notifications to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.request_reader_profile to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.reset_password_for_user to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.return_books to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.save_to_archived to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.save_to_discard to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.search to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.search_for_user to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.search_user_by_username to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.send_email to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.send_email_with_code to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.send_verification_code_for_new_email to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.update_borrowed_books to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.update_notifications_for_admin to org.hibernate.validator;
    opens bg.tu_varna.sit.library.models.update_returned_books to org.hibernate.validator;

    exports bg.tu_varna.sit.library.core;
    opens bg.tu_varna.sit.library.core to javafx.fxml;

    exports bg.tu_varna.sit.library.application;
    opens bg.tu_varna.sit.library.application to javafx.fxml;

    exports bg.tu_varna.sit.library.presentation.controllers;
    opens bg.tu_varna.sit.library.presentation.controllers to javafx.fxml;

    opens bg.tu_varna.sit.library.data.access to org.hibernate.orm.core;
    exports bg.tu_varna.sit.library.data.access;


    opens bg.tu_varna.sit.library.data.entities
            to org.hibernate.validator,
            org.hibernate.orm.core;
    exports bg.tu_varna.sit.library.data.entities;


    exports bg.tu_varna.sit.library.presentation.controllers.base;
    opens bg.tu_varna.sit.library.presentation.controllers.base to javafx.fxml;
    exports bg.tu_varna.sit.library.presentation.controllers.admin;
    opens bg.tu_varna.sit.library.presentation.controllers.admin to javafx.fxml;
    exports bg.tu_varna.sit.library.presentation.controllers.user;
    opens bg.tu_varna.sit.library.presentation.controllers.user to javafx.fxml;
}