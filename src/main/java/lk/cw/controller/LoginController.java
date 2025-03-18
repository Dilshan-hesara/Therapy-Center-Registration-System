package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.UserBO;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginController {

    @FXML
    private AnchorPane ancer;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @FXML
    void LogingOnAction(ActionEvent event) throws Exception {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();



        String dbPassword = userDAO.getPasswordByUserName(userName);

        System.out.println(dbPassword);
        try {
            // Fetch the password for the given username

            // Validate the password
            if (dbPassword != null && BCrypt.checkpw(password, dbPassword)) {
                System.out.println("Login successful for user: " + userName);
                dashBoad();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Password. Try Again").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error during login: " + e.getMessage()).show();
        }
    }






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
