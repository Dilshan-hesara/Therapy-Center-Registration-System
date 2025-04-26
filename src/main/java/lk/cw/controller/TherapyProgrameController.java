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
import lk.cw.bo.custom.TherapyProgramBO;
import lk.cw.dto.TherapyProgramDTO;
import lk.cw.tm.TherapyProgramTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapyProgrameController implements Initializable {
    @FXML
    private TableView<TherapyProgramTM> ProgramTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<TherapyProgramTM,String> colduration;

    @FXML
    private TableColumn<TherapyProgramTM,Double> colfee;

    @FXML
    private TableColumn<TherapyProgramTM,String> colid;

    @FXML
    private TableColumn<TherapyProgramTM,String> colname;

    @FXML
    private TableColumn<TherapyProgramTM,String> colfee1;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblregister;

    @FXML
    private Label lblstatus;

    @FXML
    private Label lbltherapistid;

    @FXML
    private Label lbltherapistname;

    @FXML
    private Label lprogramid;

    @FXML
    private TextField txtdes;

    @FXML
    private TextField txtdure;

    @FXML
    private TextField txtfee;

    @FXML
    private TextField txtname;

    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYOROGRAM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colduration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colfee.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colfee1.setCellValueFactory(new PropertyValueFactory<>("Description"));


        try {
            LoadNextID();
            loadTableData();
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        String ID = lprogramid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDelete = therapyProgramBO.delete(ID);
            if (isDelete) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "TherapyProgram deleted...!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete TherapyProgram...!").show();

            }
        }
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        refreshPage();
    }

//    @FXML
//    void SaveOnAction(ActionEvent event) {
//        String programId = lprogramid.getText();
//        String programName = txtname.getText();
//        String duration = txtdure.getText();
//        double cost = Double.parseDouble(txtfee.getText());
//        String Description = txtdes.getText();
//
//        if (programName.isEmpty()) {
//            showAlert("Program name is required!");
//            txtname.requestFocus();
//            return;
//        }
//
//// Check for letters only (including spaces, apostrophes, and hyphens)
//        if (!programName.matches("^[a-zA-Z\\s'-]+$")) {
//            showAlert("Program name can only contain letters, spaces, apostrophes, and hyphens!");
//            txtname.requestFocus();
//            return;
//        }
//
//        // Validate Program ID
//        if (programId.isEmpty()) {
//            showAlert("Program ID is required!");
//            lprogramid.requestFocus();
//            return;
//        }
//
//        // Validate Program Name
//        if (programName.isEmpty()) {
//            showAlert("Program name is required!");
//            txtname.requestFocus();
//            return;
//        }
//        if (programName.length() > 100) {
//            showAlert("Program name cannot exceed 100 characters!");
//            txtname.requestFocus();
//            return;
//        }
//
//        // Validate Duration
//        if (duration.isEmpty()) {
//            showAlert("Duration is required!");
//            txtdure.requestFocus();
//            return;
//        }
//        if (!duration.matches("^\\d+\\s*(weeks?|months?|years?)$")) {
//            showAlert("Duration format invalid (e.g., '4 weeks', '6 months')");
//            txtdure.requestFocus();
//            return;
//        }
//
//        // Validate Fee
//        if (feeText.isEmpty()) {
//            showAlert("Fee is required!");
//            txtfee.requestFocus();
//            return;
//        }
//        double cost;
//        try {
//            cost = Double.parseDouble(feeText);
//            if (cost <= 0) {
//                showAlert("Fee must be greater than 0!");
//                txtfee.requestFocus();
//                return;
//            }
//            if (cost > 999999.99) {
//                showAlert("Fee cannot exceed 999,999.99!");
//                txtfee.requestFocus();
//                return;
//            }
//        } catch (NumberFormatException e) {
//            showAlert("Fee must be a valid number!");
//            txtfee.requestFocus();
//            return;
//        }
//
//        // Validate Description
//        if (description.isEmpty()) {
//            showAlert("Description is required!");
//            txtdes.requestFocus();
//            return;
//        }
//        if (description.length() > 500) {
//            showAlert("Description cannot exceed 500 characters!");
//            txtdes.requestFocus();
//            return;
//        }
//
//
//        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(programId, programName, duration, cost, Description);
//        try {
//            boolean isSaved = therapyProgramBO.save( therapyProgramDTO);
//            if(isSaved){
//                new Alert(Alert.AlertType.INFORMATION,"Therapy Program Saved SUCCESSFULLY 😎").show();
//                refreshPage();
//                clearForm();
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
//
//    private void showAlert(String message, Alert.AlertType alertType) {
//        Platform.runLater(() -> {
//            Alert alert = new Alert(alertType);
//            alert.setContentText(message);
//            alert.showAndWait();
//        });
//    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        try {
            String programId = lprogramid.getText().trim();
            String programName = txtname.getText().trim();
            String duration = txtdure.getText().trim();
            String feeText = txtfee.getText().trim();
            String description = txtdes.getText().trim();

            if (programId.isEmpty()) {
                showAlert("Program ID is required!", Alert.AlertType.ERROR);
                lprogramid.requestFocus();
                return;
            }

            if (programName.isEmpty()) {
                showAlert("Program name is required!", Alert.AlertType.ERROR);
                txtname.requestFocus();
                return;
            }

            if (!programName.matches("^[a-zA-Z\\s'-]+$")) {
                showAlert("Program name can only contain letters, spaces, apostrophes, and hyphens!", Alert.AlertType.ERROR);
                txtname.requestFocus();
                return;
            }

            if (programName.length() > 100) {
                showAlert("Program name cannot exceed 100 characters!", Alert.AlertType.ERROR);
                txtname.requestFocus();
                return;
            }

            if (duration.isEmpty()) {
                showAlert("Duration is required!", Alert.AlertType.ERROR);
                txtdure.requestFocus();
                return;
            }

            if (!duration.matches("^\\d+\\s*(weeks?|months?|years?)$")) {
                showAlert("Duration format invalid (e.g., '4 weeks', '6 months')", Alert.AlertType.ERROR);
                txtdure.requestFocus();
                return;
            }

            if (feeText.isEmpty()) {
                showAlert("Fee is required!", Alert.AlertType.ERROR);
                txtfee.requestFocus();
                return;
            }

            double cost;
            try {
                cost = Double.parseDouble(feeText);
                if (cost <= 0) {
                    showAlert("Fee must be greater than 0!", Alert.AlertType.ERROR);
                    txtfee.requestFocus();
                    return;
                }
                if (cost > 999999.99) {
                    showAlert("Fee cannot exceed 999,999.99!", Alert.AlertType.ERROR);
                    txtfee.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Fee must be a valid number!", Alert.AlertType.ERROR);
                txtfee.requestFocus();
                return;
            }

            if (description.isEmpty()) {
                showAlert("Description is required!", Alert.AlertType.ERROR);
                txtdes.requestFocus();
                return;
            }

            if (description.length() > 500) {
                showAlert("Description cannot exceed 500 characters!", Alert.AlertType.ERROR);
                txtdes.requestFocus();
                return;
            }

            TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(
                    programId, programName, duration, cost, description
            );

            boolean isSaved = therapyProgramBO.save(therapyProgramDTO);

            if (isSaved) {
                showAlert("Therapy Program Saved SUCCESSFULLY 😎", Alert.AlertType.INFORMATION);
                refreshPage();
                clearForm();
            } else {
                showAlert("PLEASE TRY AGAIN 😥", Alert.AlertType.ERROR);
            }

        } catch (IOException e) {
            showAlert("Duplicate ID detected", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Database error: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            showAlert("Configuration error: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("An unexpected error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void clearForm() {
        txtname.clear();
        txtdure.clear();
        txtfee.clear();
        txtdes.clear();
    }

    @FXML
    void TableOnClicked(MouseEvent event) {
        TherapyProgramTM therapyProgramTM = (TherapyProgramTM) ProgramTable.getSelectionModel().getSelectedItem();
        if (therapyProgramTM != null) {
            lprogramid.setText(therapyProgramTM.getProgramId());
            txtname.setText(therapyProgramTM.getProgramName());
            txtdure.setText(therapyProgramTM.getDuration());
            txtfee.setText(String.valueOf(therapyProgramTM.getCost()));
            txtdes.setText(therapyProgramTM.getDescription());



            btndelete.setDisable(false);
            btnsave.setDisable(true);
            btnupdate.setDisable(false);
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {
        String programId = lprogramid.getText();
        String programName = txtname.getText();
        String duration = txtdure.getText();
        double cost = Double.parseDouble(txtfee.getText());
        String Description = txtdes.getText();

        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(programId, programName, duration, cost, Description);
        try {
            boolean isSaved = therapyProgramBO.update( therapyProgramDTO);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Therapy Program Saved SUCCESSFULLY 😎").show();
                refreshPage();
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
    private void LoadNextID() throws SQLException, IOException {
        String nextID = therapyProgramBO.getNextId();
        lprogramid.setText(nextID);
    }
    private void loadTableData() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<TherapyProgramDTO> therapyProgramDTOS = (ArrayList<TherapyProgramDTO>) therapyProgramBO.getAll();
        ObservableList<TherapyProgramTM> therapyProgramTMS = FXCollections.observableArrayList();

        for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {
            TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                    therapyProgramDTO.getProgramId(),
                    therapyProgramDTO.getProgramName(),
                    therapyProgramDTO.getDuration(),
                    therapyProgramDTO.getCost(),
                    therapyProgramDTO.getDescription()


            );
            therapyProgramTMS.add(therapyProgramTM);
        }
        ProgramTable.setItems(therapyProgramTMS);
    }
    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();

        btndelete.setDisable(true);
        btnsave.setDisable(false);
        btnupdate.setDisable(true);

        txtname.setText("");
        txtname.setText("");
        txtdure.setText("");
        txtfee.setText("");
        txtdes.setText("");

    }

}
