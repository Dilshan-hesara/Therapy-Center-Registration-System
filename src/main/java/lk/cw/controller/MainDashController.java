package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.PatientBO;
import lk.cw.bo.custom.TherapistBO;
import lk.cw.bo.custom.TherapyProgramBO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainDashController implements Initializable {



    @FXML
    private Label lbltotalpatient;

    @FXML
    private Label lblTherpist;


    @FXML
    private Label lblpro;

    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYOROGRAM);
    TherapistBO therapistBO = (TherapistBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPIST);





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadPatientCount();
        loadTherapistCount();
        loadProgramCount();
    }


    private void loadPatientCount() {
        try {

            int totalLabors = patientBO.getTotalPatients();
            lbltotalpatient.setText(String.valueOf(totalLabors));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadTherapistCount() {
        try {

            int totalTherapists = therapistBO.getTotalTherapists();
            lblTherpist.setText(String.valueOf(totalTherapists));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProgramCount() {
        try {

            int totalPrograms = therapyProgramBO.getTotalPrograms();
            lblpro.setText(String.valueOf(totalPrograms));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
