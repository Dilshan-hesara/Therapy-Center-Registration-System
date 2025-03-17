module lk.cw.ormcoursework {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires java.sql;
    requires net.sf.jasperreports.core;


    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires jbcrypt;

    opens lk.cw.entity to org.hibernate.orm.core;
//    opens lk.cw.config to jakarta.persistence;



    opens lk.cw to javafx.fxml;
    opens lk.cw.controller to javafx.fxml;
    exports lk.cw;
}