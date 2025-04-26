package lk.cw.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.TherapistBO;
import lk.cw.dao.custom.TherapyProgramDAO;
import lk.cw.dao.custom.impl.TherapyProgramDAOImpl;
import lk.cw.dto.TherapistDTO;
import lk.cw.entity.Therapist;
import lk.cw.tm.TherapistTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapistController implements Initializable {


    @FXML
    private TableView<TherapistTM> TherapistTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<TherapistTM,String> colavailable;

    @FXML
    private TableColumn<TherapistTM,String> colid;

    @FXML
    private TableColumn<TherapistTM,String> colname;

    @FXML
    private TableColumn<TherapistTM,String> colspecail;

    @FXML
    private Label lblid;

    @FXML
    private TextField txtavailable;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtspecail;

    @FXML
    private ComboBox<String> txtspec;


    TherapistBO therapistBO = (TherapistBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPIST);

    @FXML
    void speciaOnAction(ActionEvent event) {
        String selectedID = txtspec.getValue();

        System.out.println(selectedID);

    }

    @FXML
    private ComboBox<String> comSelectAv;

    TherapyProgramDAO therapyProgramDAO = new TherapyProgramDAOImpl();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colid.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colspecail.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colavailable.setCellValueFactory(new PropertyValueFactory<>("availability"));

        loadAvailabilityOptions();
        ArrayList<String> programList = therapyProgramDAO.getProgramList();
        System.out.println(programList);
        txtspec.getItems().addAll(programList);


        btndelete.setDisable(true);
        btnsave.setDisable(false);
        btnupdate.setDisable(true);


        try {
            LoadNextID();
            loadTableData();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void loadAvailabilityOptions() {
        comSelectAv.getItems().addAll("Available", "Busy");
    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        try {
            String therapistId = lblid.getText().trim();
            String therapistName = txtname.getText().trim();
            String specialization = txtspec.getValue() != null ? txtspec.getValue().toString().trim() : "";
            String availability = comSelectAv.getValue() != null ? comSelectAv.getValue().toString().trim() : "";

            if (therapistId.isEmpty()) {
                showAlert("Therapist ID is NOT GENARATED !", Alert.AlertType.ERROR);
                lblid.requestFocus();
                return;
            }
            if (!therapistId.matches("^[A-Za-z0-9-]+$")) {
                showAlert("Therapist ID can only contain letters, numbers and hyphens!", Alert.AlertType.ERROR);
                lblid.requestFocus();
                return;
            }

            if (therapistName.isEmpty()) {
                showAlert("Therapist name is required!", Alert.AlertType.ERROR);
                txtname.requestFocus();
                return;
            }
            if (!therapistName.matches("^[a-zA-Z\\s'-]+$")) {
                showAlert("Therapist name can only contain letters, spaces, apostrophes, and hyphens!", Alert.AlertType.ERROR);
                txtname.requestFocus();
                return;
            }
            if (therapistName.length() > 100) {
                showAlert("Therapist name cannot exceed 100 characters!", Alert.AlertType.ERROR);
                txtname.requestFocus();
                return;
            }


            if (specialization.isEmpty()) {
                showAlert("Specialization is required!", Alert.AlertType.ERROR);
                txtspec.requestFocus();
                return;
            }

            if (availability.isEmpty()) {
                showAlert("Availability is required!", Alert.AlertType.ERROR);
                comSelectAv.requestFocus();
                return;
            }

            TherapistDTO therapistDTO = new TherapistDTO(therapistId, therapistName, specialization, availability);
            boolean isRegistered = therapistBO.save(therapistDTO);

            if (isRegistered) {
                showAlert("Therapist Saved Successfully!", Alert.AlertType.INFORMATION);
                refreshPage();
                clearForm();
            } else {
                showAlert("Failed to save therapist. Please try again.", Alert.AlertType.ERROR);
            }

        } catch (IOException e) {
            showAlert("Duplicate ID detected. Please use a unique ID.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Database error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            showAlert("System configuration error.", Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("An unexpected error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void clearForm() {
        txtname.clear();
        txtspec.setValue(null);
        txtspec.getItems().clear();
        comSelectAv.setValue(null);
        comSelectAv.getItems().clear();


    }

//    @FXML
//    void SaveOnAction(ActionEvent event) {
//        String therapistId = lblid.getText();
//        String therapistName = txtname.getText();
//        String specialization = txtspec.getValue();
//        String availability = comSelectAv.getValue();
//
//        try {
//            boolean isRegistered = therapistBO.save(new TherapistDTO(therapistId,therapistName,specialization,availability));
//            if(isRegistered){
//                refreshPage();
//                new Alert(Alert.AlertType.INFORMATION,"User Saved SUCCESSFULLY 😎").show();
//
//            }
//            else {
//                new Alert(Alert.AlertType.ERROR,"PLEASE TRY AGAIN 😥").show();
//            }
//        } catch (IOException e) {
//            new Alert(Alert.AlertType.ERROR,"duplicate Id");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        refreshPage();
    }


    @FXML
    void TableOnClicked(MouseEvent event) {
        TherapistTM therapistTM = (TherapistTM) TherapistTable.getSelectionModel().getSelectedItem();
        if (therapistTM != null) {
            lblid.setText(therapistTM.getTherapistId());
            txtname.setText(therapistTM.getTherapistName());
//            txtspecail.setText(therapistTM.getSpecialization());
//            txtavailable.setText(therapistTM.getAvailability());

            btndelete.setDisable(false);
            btnsave.setDisable(true);
            btnupdate.setDisable(false);
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {
        String therapistId = lblid.getText();
        String therapistName = txtname.getText();
        String specialization = colspecail.getText();
        String availability = comSelectAv.getValue();

        try {
            boolean isRegistered = therapistBO.update(new TherapistDTO(therapistId,therapistName,specialization,availability));
            if(isRegistered){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION,"User Saved SUCCESSFULLY 😎").show();

            }
            else {
                new Alert(Alert.AlertType.ERROR,"PLEASE TRY AGAIN 😥").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"duplicate Id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();

        btndelete.setDisable(true);
        btnsave.setDisable(false);
        btnupdate.setDisable(true);

        txtname.setText("");

        txtspec.setValue(null);
        txtspec.getItems().clear();
        comSelectAv.setValue(null);
        comSelectAv.getItems().clear();

    }

    private void LoadNextID() throws SQLException, IOException {
        String nextID = therapistBO.getNextId();
        lblid.setText(nextID);
    }


    private void loadTableData() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<TherapistDTO> therapistDTOS = (ArrayList<TherapistDTO>) therapistBO.getAll();
        ObservableList<TherapistTM> therapistTMS = FXCollections.observableArrayList();

        for (TherapistDTO therapistDTO : therapistDTOS) {
            TherapistTM therapistTM = new TherapistTM(
                    therapistDTO.getTherapistId(),
                    therapistDTO.getTherapistName(),
                    therapistDTO.getSpecialization(),
                    therapistDTO.getAvailability()


            );
            therapistTMS.add(therapistTM);
        }
        TherapistTable.setItems(therapistTMS);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        String ID = lblid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDelete = therapistBO.delete(ID);
            if (isDelete) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Labor deleted...!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Labor...!").show();

            }
        }
    }



}
