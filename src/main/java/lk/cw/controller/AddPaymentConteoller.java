package lk.cw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.AddPayBO;
import lk.cw.bo.custom.PatientBO;
import lk.cw.dto.PatientDTO;
import lk.cw.dto.PaymentDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddPaymentConteoller implements Initializable {

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private ComboBox<String> comboStatus;
    private final String[] Status = {"Payment Completed","Incomplete"};


    @FXML
    private ComboBox<?> combopatientid;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label lblPatientid;

    @FXML
    private Label lblPatientid1;

    @FXML
    private Label lblbalance;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblid;

    @FXML
    private Label lblstatus;

    @FXML
    private TextField txtamount;


    AddPayBO paymentBO = (AddPayBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);
 //   PatientRegistrationBO patientRegistrationBO = (PatientRegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REGISTRATION);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboStatus.getItems().addAll(Status);


        try {
            LoadNextID();

            loadPatientIDs();
     //       loadTableData();
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load PaymentId").show();
        }
    }


    @FXML
    void StatusComboOnAction(ActionEvent event) {
        String SelectedValue = comboStatus.getValue();
        lblstatus.setText(SelectedValue);
    }
    @FXML
    void ComboPatientIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String selectedID = (String) combopatientid.getValue();
        PatientDTO patientDTO = patientBO.findById(selectedID);
        if (patientDTO != null) {
            lblPatientid.setText(patientDTO.getName());
        }
//        if (selectedID != null) {
//            double balance = patientRegistrationBO.getBalanceByPatientId(selectedID);
//            lblbalance.setText(String.format("%.2f", balance));
//        }
    }

    @FXML
    void DatePickerOnAction(ActionEvent event) {
        LocalDate localDate = datepicker.getValue();
        String pattern = "yyyy-MM-dd";
        String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
        lbldate.setText(datePattern);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
//        String ID = lblid.getText();
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
//        Optional<ButtonType> optionalButtonType = alert.showAndWait();
//
//        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
//
//            boolean isDelete = paymentBO.delete(ID);
//            if (isDelete) {
//                refreshPage();
//                new Alert(Alert.AlertType.INFORMATION, "Payment deleted...!").show();
//
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Fail to delete Payment...!").show();
//
//            }
//        }
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        String PaymentId = lblid.getText();
        String patientId = (String) combopatientid.getValue();
        double amount = Double.parseDouble(txtamount.getText());
        String paymentDate = lbldate.getText();
        String Status = lblstatus.getText();

        try{
            PaymentDTO paymentDTO = new PaymentDTO(
                    PaymentId,patientId,amount,paymentDate,Status
            );
            boolean isRegistered = paymentBO.save(paymentDTO);

            if (isRegistered) {
                refreshPage();  // UI එක refresh කරන්න
                new Alert(Alert.AlertType.INFORMATION, "Payment Saved SUCCESSFULLY 😎").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    private void loadPatientIDs() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<String> patientIds = patientBO.getAllPatientIds();
      //  combopatientid.getItems().addAll(patientIds);
    }
    private void LoadNextID() throws SQLException, IOException {
        String nextID = paymentBO.getNextId();
        lblid.setText(nextID);
    }

    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        //loadTableData();

        btndelete.setDisable(true);
        btnsave.setDisable(false);
     //   btnupdate.setDisable(true);

        lblPatientid.setText("");
        txtamount.setText("");
        lbldate.setText("");


    }

//    @FXML
//    void ComboPatientIdOnAction(ActionEvent event) {
//
//    }
//
//    @FXML
//    void DatePickerOnAction(ActionEvent event) {
//
//    }
//
//    @FXML
//    void DeleteOnAction(ActionEvent event) {
//
//    }
//
//    @FXML
//    void ResetOnAction(ActionEvent event) {
//
//    }
//
//    @FXML
//    void SaveOnAction(ActionEvent event) {
//
//    }
//
//    @FXML
//    void StatusComboOnAction(ActionEvent event) {
//
//    }

}
