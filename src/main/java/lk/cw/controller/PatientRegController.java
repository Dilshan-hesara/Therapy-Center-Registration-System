package lk.cw.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.PatientBO;
import lk.cw.bo.custom.PatientRegBO;
import lk.cw.bo.custom.TherapyProgramBO;
import lk.cw.dao.custom.PatientDAO;
import lk.cw.dao.custom.TherapyProgramDAO;
import lk.cw.dao.custom.impl.PatientDAOImpl;
import lk.cw.dao.custom.impl.TherapyProgramDAOImpl;
import lk.cw.dto.PatientDTO;
import lk.cw.dto.PatientRegistrationDTO;
import lk.cw.dto.TherapyProgramDTO;
import lk.cw.entity.Patient;
import lk.cw.tm.PatientRegistrationTM;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PatientRegController implements Initializable {

    @FXML
    private TableView<PatientRegistrationTM> RegistrationTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<?, ?> colcount;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> colpatid;

    @FXML
    private TableColumn<?, ?> colproid;

    @FXML
    private TableColumn<?, ?> colregid;

    @FXML
    private ComboBox<?> combopatientid;

    @FXML
    private ComboBox<String> comboprogramId;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label lblcount;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblid;

    @FXML
    private Label lblpatientid;

    @FXML
    private Label lblprogramid;

    @FXML
    private Label lbltherapistname;

    PatientRegBO patientRegistrationBO = (PatientRegBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REG);
    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYOROGRAM);


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colregid.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colpatid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colproid.setCellValueFactory(new PropertyValueFactory<>("programId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        colcount.setCellValueFactory(new PropertyValueFactory<>("sessionCount"));
        loadProgramIDs();

        loadPatientIDs();
        LoadNextID();
    }


    @FXML
    void DatePickerOnAction(ActionEvent event) {

    }

    @FXML
    void DeleteOnAction(ActionEvent event) {

    }

    @FXML
    void ResetOnAction(ActionEvent event) {

    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        String registrationId = lblid.getText();
        String patientId = String.valueOf(combopatientid.getValue());
        String programId = String.valueOf(comboprogramId.getValue());
        String registrationDate = lbldate.getText();

        try{
            PatientRegistrationDTO patientRegistrationDTO = new PatientRegistrationDTO(
                    registrationId,patientId,programId,registrationDate
            );
            boolean isRegistered = patientRegistrationBO.save(patientRegistrationDTO);

            if (isRegistered) {
           //     refreshPage();  // UI එක refresh කරන්න
                new Alert(Alert.AlertType.INFORMATION, "PatientRegistration Saved SUCCESSFULLY 😎").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    TherapyProgramDAO therapyProgramDAO = new TherapyProgramDAOImpl();

//    private void loadProgramIDs() throws SQLException, ClassNotFoundException, IOException {
//
//        ArrayList<String>programIds = therapyProgramBO.getAllProgramIDs();
//        System.out.println(programIds);
//      //  comboprogramId.getItems().setAll(programIds); // Changed to setAll()
//        for (String id : programIds) {
//            comboprogramId.getItems().add(id);
//        }
//    }
private void LoadNextID() throws SQLException, IOException {
    String nextID = patientRegistrationBO.getNextId();
    lblid.setText(nextID);
}
@FXML
private ComboBox<String> cmbpat;

    private void loadProgramIDs() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> programIds = therapyProgramBO.getAllProgramIDs();
        comboprogramId.getItems().addAll(programIds);
    }
    PatientDAO patientDAO = new PatientDAOImpl();

    private void loadPatientIDs() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<String> patientIds = patientBO.getAllPatientIds();
        cmbpat.getItems().addAll(patientIds);
    }





    @FXML
    void TableOnClicked(MouseEvent event) {

    }

    @FXML
    void UpdateOnAction(ActionEvent event) {

    }


    @FXML
    void ComboPatientIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedID = (String) cmbpat.getValue();
        PatientDTO patientDTO = patientBO.findById(selectedID);

        if (patientDTO != null) {
            lblpatientid.setText(patientDTO.getName());
        }
    }

    @FXML
    void ComboProgramIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedID = (String) comboprogramId.getValue();
        TherapyProgramDTO therapyProgramDTO = therapyProgramBO.findById(selectedID);

        if (therapyProgramDTO != null) {
            lblprogramid.setText(therapyProgramDTO.getProgramName());
        }
    }

//    @FXML
//    void ComboPatientIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
//        String selectedID = String.valueOf(combopatientid.getValue());
//        PatientDTO patientDTO = patientBO.findById(selectedID);
//
//        if (patientDTO != null) {
//            lblpatientid.setText(patientDTO.getName());
//        }
//    }
//
//    @FXML
//    void ComboProgramIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
//        String selectedID = String.valueOf(comboprogramId.getValue());
//        TherapyProgramDTO therapyProgramDTO = therapyProgramBO.findById(selectedID);
//
//        if (therapyProgramDTO != null) {
//            lblprogramid.setText(therapyProgramDTO.getProgramName());
//        }
//    }
//
//    @FXML
//    void DatePickerOnAction(ActionEvent event) {
//        LocalDate localDate = datepicker.getValue();
//        String pattern = "yyyy-MM-dd";
//        String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
//        lbldate.setText(datePattern);
//    }
//
//    @FXML
//    void DeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
//        String ID = lblid.getText();
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
//        Optional<ButtonType> optionalButtonType = alert.showAndWait();
//
//        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
//
//            boolean isDelete = patientRegistrationBO.delete(ID);
//            if (isDelete) {
//                ///refreshPage();
//                new Alert(Alert.AlertType.INFORMATION, "PatientRegistration deleted...!").show();
//
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Fail to delete PatientRegistration...!").show();
//
//            }
//        }
//    }
//
//    @FXML
//    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
//        //refreshPage();
//    }
//
//    @FXML
//    void SaveOnAction(ActionEvent event) {
//        String registrationId = lblid.getText();
//        String patientId = String.valueOf(combopatientid.getValue());
//        String programId = String.valueOf(comboprogramId.getValue());
//        String registrationDate = lbldate.getText();

//        try{
//            PatientRegistrationDTO patientRegistrationDTO = new PatientRegistrationDTO(
//                    registrationId,patientId,programId,registrationDate
//            );
//            boolean isRegistered = patientRegistrationBO.save(patientRegistrationDTO);
//
//            if (isRegistered) {
//                //refreshPage();  // UI එක refresh කරන්න
//                new Alert(Alert.AlertType.INFORMATION, "PatientRegistration Saved SUCCESSFULLY 😎").show();
//            } else {
//                new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
//            }
//        } catch (IOException e) {
//            new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }



//    @FXML
//    void TableOnClicked(MouseEvent event) {
//        PatientRegistrationTM patientRegistrationTM = (PatientRegistrationTM) RegistrationTable.getSelectionModel().getSelectedItem();
//        if (patientRegistrationTM != null) {
//            lblid.setText(patientRegistrationTM.getRegistrationId());
//            combopatientid.setValue(patientRegistrationTM.getPatientId());
//            comboprogramId.setValue(patientRegistrationTM.getProgramId());
//            lbldate.setText(String.valueOf(patientRegistrationTM.getRegistrationDate()));
//            lblcount.setText(String.valueOf(patientRegistrationTM.getSessionCount()));
//
//
//
//            btndelete.setDisable(false);
//            btnsave.setDisable(true);
//            btnupdate.setDisable(false);
//        }
//    }
//
//    @FXML
//    void UpdateOnAction(ActionEvent event) {
//        String registrationId = lblid.getText();
//        String patientId = String.valueOf(combopatientid.getValue());
//        String programId = String.valueOf(comboprogramId.getValue());
//        String registrationDate = lbldate.getText();
//
//
//        try{
//            PatientRegistrationDTO patientRegistrationDTO = new PatientRegistrationDTO(
//                    registrationId,patientId,programId,registrationDate
//            );
//            boolean isRegistered = patientRegistrationBO.update(patientRegistrationDTO);
//
//            if (isRegistered) {
//                refreshPage();  // UI එක refresh කරන්න
//                new Alert(Alert.AlertType.INFORMATION, "PatientRegistration Updated SUCCESSFULLY 😎").show();
//            } else {
//                new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
//            }
//        } catch (IOException e) {
//            new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private void loadProgramIDs() throws SQLException, ClassNotFoundException, IOException {
//        ArrayList<String> programIds = therapyProgramBO.getAllProgramIDs();
//        comboprogramId.getItems().addAll(programIds);
//    }
//    private void loadPatientIDs() throws SQLException, IOException, ClassNotFoundException {
//        ArrayList<String> patientIds = patientBO.getAllPatientIds();
//        combopatientid.getItems().addAll(patientIds);
//    }
//    private void LoadNextID() throws SQLException, IOException {
//        String nextID = patientRegistrationBO.getNextId();
//        lblid.setText(nextID);
//    }
//    private void loadTableData() {
//        try {
//            // Fetch all Patient Registration records from database
//            List<PatientRegistrationDTO> patientRegistrations = patientRegistrationBO.getAll();
//            ObservableList<PatientRegistrationTM> registrationList = FXCollections.observableArrayList();
//
//            for (PatientRegistrationDTO registrationDTO : patientRegistrations) {
//                // Patient Registration data added to the ObservableList
//                registrationList.add(new PatientRegistrationTM(
//                        registrationDTO.getRegistrationId(),
//                        registrationDTO.getPatientId(),
//                        registrationDTO.getProgramId(),
//                        registrationDTO.getRegistrationDate(),  // Make sure the session count is being updated in the DTO
//                        registrationDTO.getSessionCount()
//                ));
//            }
//
//            // Set the data to the TableView
//            Platform.runLater(() -> {
//                RegistrationTable.setItems(registrationList);  // Refresh TableView data
//            });
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
//        LoadNextID();
//        loadTableData();
//
//        btndelete.setDisable(true);
//        btnsave.setDisable(false);
//        btnupdate.setDisable(true);
//
//        lblpatientid.setText("");
//        lblprogramid.setText("");
//        lbldate.setText("");
//        lblcount.setText("");
//
//
//    }


}
