package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

import static lk.cw.controller.LoginController.loggedInUser;

public class DashboardController implements Initializable {

    @FXML
    private Button TherapistBtn;

    @FXML
    private Button TherapyBtn;

    @FXML
    private AnchorPane ancer;

    @FXML
    void DashboardOnAction(ActionEvent event) {

    }

    @FXML
    void PatientManageOnAction(ActionEvent event) {

    }

    @FXML
    void PayementOnAction(ActionEvent event) {

    }

    @FXML
    void TherapistManageOnAction(ActionEvent event) {

    }

    @FXML
    void TherapySessionOnAction(ActionEvent event) {

    }

    @FXML
    void TherapytManageOnAction(ActionEvent event) {

    }

    @FXML
    void UserOnAction(ActionEvent event) {

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if ("User".equals(loggedInUser)) {
            TherapistBtn.setDisable(true);
            TherapyBtn.setDisable(true);
            System.out.println("usrr");
        }
        else {
            TherapistBtn.setDisable(false);
            TherapyBtn.setDisable(false);
            System.out.println("admin");
        }
    }
}
