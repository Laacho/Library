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
    requires java.persistence;
    requires static lombok;
    requires annotations;
    requires java.validation;
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


    opens bg.tu_varna.sit.library.models.login to org.hibernate.validator;
    exports bg.tu_varna.sit.library.application;
    opens bg.tu_varna.sit.library.application to javafx.fxml;

    exports bg.tu_varna.sit.library.presentation.controllers;
    opens bg.tu_varna.sit.library.presentation.controllers to javafx.fxml;

    opens bg.tu_varna.sit.library.data.access to org.hibernate.orm.core;
    exports bg.tu_varna.sit.library.data.access;

    opens bg.tu_varna.sit.library.data.entities to org.hibernate.orm.core;
    exports bg.tu_varna.sit.library.data.entities;
    exports bg.tu_varna.sit.library.presentation.controllers.base;
    opens bg.tu_varna.sit.library.presentation.controllers.base to javafx.fxml;
    exports bg.tu_varna.sit.library.presentation.controllers.admin;
    opens bg.tu_varna.sit.library.presentation.controllers.admin to javafx.fxml;
    exports bg.tu_varna.sit.library.presentation.controllers.user;
    opens bg.tu_varna.sit.library.presentation.controllers.user to javafx.fxml;
}