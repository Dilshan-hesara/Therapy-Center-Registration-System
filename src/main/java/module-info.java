module lk.cw.ormcoursework {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//    requires lombok;
//    requires java.sql;
//    requires net.sf.jasperreports.core;
//
//
//    requires org.hibernate.orm.core;
//    requires jakarta.persistence;
//    requires java.naming;
//    requires jbcrypt;
//    requires java.desktop;
//    // requires jbcrypt;
//
//
//
//    opens lk.cw.entity to org.hibernate.orm.core;
//    opens lk.cw.config to jakarta.persistence;
//
//
//    opens lk.cw.controller to javafx.fxml;
//    exports lk.cw;
//    opens lk.cw to javafx.fxml;
//    opens lk.cw.tm to javafx.base;
//

    // JavaFX dependencies
    requires javafx.controls;
    requires javafx.fxml;

    // Database and ORM dependencies
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    // Utility dependencies
    requires lombok;
    requires net.sf.jasperreports.core;
    requires jbcrypt;
    requires java.desktop;

    // Open packages for reflection
    opens lk.cw.entity to org.hibernate.orm.core;
    opens lk.cw.config to jakarta.persistence, org.hibernate.orm.core;
    opens lk.cw.controller to javafx.fxml;
    opens lk.cw.tm to javafx.base;
    // Exports
    exports lk.cw;
    exports lk.cw.controller;
    exports lk.cw.entity;
    exports lk.cw.tm;




//
//
//    opens lk.ijse.gdse.ormcw.controller to javafx.fxml;
//    exports lk.ijse.gdse.ormcw;
//    opens lk.ijse.gdse.ormcw.tm to javafx.base;
}