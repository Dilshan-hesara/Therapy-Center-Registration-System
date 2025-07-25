package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.UserBO;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.UserDAO;
import lk.cw.dao.custom.impl.UserDAOImpl;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //btndesable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private AnchorPane ancer;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    public static String loggedInUser;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    //UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);


    String uN;


//    @FXML
//    void LogingOnAction(ActionEvent event) throws Exception {
//        String password = txtPassword.getText();
//        String userName = txtUsername.getText();
//
//        if (userName == null || userName.trim().isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Username is required!").show();
//            return;
//        }
//
//        if (password == null || password.trim().isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Password is required!").show();
//            return;
//        }
//
//
//        uN =userName;
//
//        String dbPassword = userBO.getPasswordByUserName(userName);
//
//        System.out.println(dbPassword);
//        try {
//
//            if (dbPassword != null && BCrypt.checkpw(password, dbPassword)) {
//                System.out.println("Login successful for user: " + userName);
//
//                String role = userBO.getRoleByUserName(uN);
//
//                loggedInUser = role;
//                dashBoad();
//
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Invalid Password & User . Try Again").show();
//            }
//        } catch (Exception e) {
//            new Alert(Alert.AlertType.ERROR, "Error during login: " + e.getMessage()).show();
//        }
//    }

//
//    @FXML
//    void LogingOnAction(ActionEvent event) throws Exception {
//        String password = txtPassword.getText();
//        String userName = txtUsername.getText();
//
//        if (userName == null | userName.trim().isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Username is required!").show();
//            return;
//        }
//
//        if (password == null || password.trim().isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Password is required!").show();
//            return;
//        }
//
//        uN = userName;
//
//        String dbPassword = userBO.getPasswordByUserName(userName);
//
//        System.out.println("Password from DB: " + dbPassword);
//
//        if (dbPassword == null) {
//            new Alert(Alert.AlertType.ERROR, "Invalid Username!").show();
//            return;
//        }
//
//        if (!BCrypt.checkpw(password, dbPassword)) {
//            new Alert(Alert.AlertType.ERROR, "Invalid Password!").show();
//            return;
//        }
//
//        // If username and password correct
//        System.out.println("Login successful for user: " + userName);
//        String role = userBO.getRoleByUserName(uN);
//        loggedInUser = role;
//        dashBoad();
//    }


    @FXML
    void LogingOnAction(ActionEvent event) throws Exception {
        String password = txtPassword.getText();
        String userName = txtUsername.getText();

        if (userName == null || userName.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Username is required!").show();
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password is required!").show();
            return;
        }

        uN = userName;

        boolean isExist = userDAO.existsByUsername(userName);

        if (!isExist) {
            new Alert(Alert.AlertType.ERROR, "Invalid Username!").show();
            return;
        }

        String dbPassword = userBO.getPasswordByUserName(userName);
        System.out.println("Password from DB: " + dbPassword);

        if (!BCrypt.checkpw(password, dbPassword)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Password!").show();
            return;
        }

        System.out.println("Login successful for user: " + userName);
        String role = userBO.getRoleByUserName(uN);
        loggedInUser = role;
        dashBoad();
    }


    UserDAO userDAO = new UserDAOImpl();

    @FXML
    void regisOnAction(ActionEvent event) throws IOException {

        newReg();
    }


    private  void dashBoad() throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DashBoad.fxml"));
        ancer.getChildren().clear();
        ancer.getChildren().add(load);
    }

    private  void newReg() throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Regest.fxml"));
        ancer.getChildren().clear();
        ancer.getChildren().add(load);
    }

}
