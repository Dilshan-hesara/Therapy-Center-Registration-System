package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
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
    void PatientManageOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/PatientManage.fxml"));
        ancer.getChildren().clear();
        ancer.getChildren().add(load);
    }

    @FXML
    void PayementOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/PaymentManage.fxml"));
        ancer.getChildren().clear();
        ancer.getChildren().add(load);
    }

    @FXML
    void TherapistManageOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/TherapistManage.fxml"));
        ancer.getChildren().clear();
        ancer.getChildren().add(load);
    }

    @FXML
    void TherapytManageOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/TherapyProgrameManage.fxml"));
        ancer.getChildren().clear();
        ancer.getChildren().add(load);
    }

    @FXML
    void TherapySessionOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/TherapeySessionManage.fxml"));
        ancer.getChildren().clear();
        ancer.getChildren().add(load);
    }




    @FXML
    void UserOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/UserManage.fxml"));
       ancer.getChildren().clear();
        ancer.getChildren().add(load);
    }


    @FXML
    private AnchorPane mainAnc;

    @FXML
    void LogOutOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        mainAnc.getChildren().clear();
        mainAnc.getChildren().add(load);
    }

    @FXML
    private Button userbtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loggedInUser != null) {
            if ("Receptionist".equals(loggedInUser)) {

                TherapistBtn.setDisable(true);
                TherapyBtn.setDisable(true);
                userbtn.setDisable(true);
                System.out.println("User");
            } else if ("Admin".equals(loggedInUser)) {
                userbtn.setDisable(false);

                TherapistBtn.setDisable(false);
                TherapyBtn.setDisable(false);
                System.out.println("Admin.");
            } else {

                System.out.println("uno" + loggedInUser);
            }
        }
    }
}
