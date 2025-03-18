package lk.cw.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.UserBO;
import lk.cw.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadcmb();
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

    @FXML
    void regisOnAction(ActionEvent event) throws IOException {
        String txtid = "04";
        String Id = txtid;
        String UserName = txtUsername.getText();
        String Password = txtPassword.getText();
        String Role = cmbRole.getValue();

        try {
            boolean isRegistered = userBO.save(new UserDTO(Id,UserName,Password,Role));
            if(isRegistered){
                new Alert(Alert.AlertType.INFORMATION,"REGISTERED SUCCESSFULLY").show();
                clearFeilds();
                login();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"PLEASE TRY AGAIN").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"duplicate Id");
        }

    }



    @FXML
    void roleComb(ActionEvent event) {
    }

    private void loadcmb() {

        cmbRole.setItems(FXCollections.observableArrayList("Admin", "User").sorted());

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
