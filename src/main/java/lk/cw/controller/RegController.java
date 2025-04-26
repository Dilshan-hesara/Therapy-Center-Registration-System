package lk.cw.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.UserBO;
import lk.cw.dto.UserDTO;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegController implements Initializable {
    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadcmb();
        LoadNextID();
    }

    @FXML
    private ComboBox<String> cmbRole;


    @FXML
    private AnchorPane ancer1;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtRePassword;

    @FXML
    private TextField txtUsername;


    UserBO userBO =  (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @FXML
    void BackOnAction(ActionEvent event) throws IOException {

        login();
    }

    String ID;
//    @FXML
//    void regisOnAction(ActionEvent event) throws IOException {
//        String txtid = ID;
//        String Id = txtid;
//        String UserName = txtUsername.getText();
//        String Password = txtPassword.getText();
//        String pasworRePassword = txtRePassword.getText();
//
//        String Role = cmbRole.getValue();
//        if(Role == "Admin"){
//            new Alert(Alert.AlertType.ERROR,"CANT REG ADMIN ONLY REG USER ACC").show();
//        }else {
//
//            try {
//                boolean isRegistered = userBO.save(new UserDTO(Id,UserName,Password,Role));
//                if(isRegistered){
//                    new Alert(Alert.AlertType.INFORMATION,"REGISTERED SUCCESSFULLY").show();
//                    clearFeilds();
//                    login();
//                }
//                else {
//                    new Alert(Alert.AlertType.ERROR,"PLEASE TRY AGAIN").show();
//                }
//            } catch (IOException e) {
//                new Alert(Alert.AlertType.ERROR,"duplicate Id");
//            }
//
//
//        }
//
//
//
//    }
@FXML
void regisOnAction(ActionEvent event) throws IOException {
    String txtid = ID;
    String Id = txtid;
    String UserName = txtUsername.getText();
    String Password = txtPassword.getText();
    String pasworRePassword = txtRePassword.getText();
    String Role = cmbRole.getValue();

    // Validation start

    if (UserName == null || UserName.trim().isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "Username is required!").show();
        return;
    }

    if (Password == null || Password.trim().isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "Password is required!").show();
        return;
    }

    if (pasworRePassword == null || pasworRePassword.trim().isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "Re-enter Password is required!").show();
        return;
    }

    if (!Password.equals(pasworRePassword)) {
        new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
        return;
    }

    if (Role == null || Role.trim().isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "Role must be selected!").show();
        return;
    }

    if (Role.equals("Admin")) {
        new Alert(Alert.AlertType.ERROR, "Cannot register an Admin. Only User accounts allowed!").show();
        return;
    }

    // Validation End ✅

    try {
        boolean isRegistered = userBO.save(new UserDTO(Id, UserName, Password, Role));
        if (isRegistered) {
            new Alert(Alert.AlertType.INFORMATION, "REGISTERED SUCCESSFULLY").show();
            clearFeilds();
            login();
        } else {
            new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN").show();
        }
    } catch (IOException e) {
        new Alert(Alert.AlertType.ERROR, "Duplicate ID detected!").show();
    }
}

    @FXML
    private Label UserIDlbl;

    private void LoadNextID() throws SQLException, IOException {

        String uis = userBO.getNextId();

        ID = uis;
        UserIDlbl.setText(uis);
    }


    @FXML
    void roleComb(ActionEvent event) {
    }

    private void loadcmb() {

        cmbRole.setItems(FXCollections.observableArrayList("Admin", "Receptionist").sorted());

    }


    private  void login() throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        ancer1.getChildren().clear();
        ancer1.getChildren().add(load);
    }

    public void clearFeilds(){
        txtPassword.setText("");
        txtUsername.setText("");
        txtRePassword.setText("");
    }

}
