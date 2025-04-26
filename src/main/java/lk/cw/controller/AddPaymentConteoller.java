package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.AddPayBO;
import lk.cw.bo.custom.PatientBO;
import lk.cw.bo.custom.PatientRegBO;
import lk.cw.bo.custom.impl.AddPayBOImpl;
import lk.cw.dao.custom.AddPayDAO;
import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.dao.custom.impl.AddPayDAOImpl;
import lk.cw.dao.custom.impl.PatientRegDAOImpl;
import lk.cw.dto.PatientDTO;
import lk.cw.dto.PaymentDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddPaymentConteoller implements Initializable {


    @FXML
    private Button btnsave;

    @FXML
    private ComboBox<String> comboStatus;


    @FXML
    private ComboBox<String> combopatientid;

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

    private final String[] Status = {"PAY-Completed","PAY-Incomplete"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboStatus.getItems().addAll(Status);

        try {
            loadPatientIDs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            LoadNextID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    PatientRegBO patientRegistrationBO = (PatientRegBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REG);

    PatientRegDAO patientRegBO = new PatientRegDAOImpl();


    @FXML
    void ComboPatientIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String selectedID = (String) combopatientid.getValue();
        PatientDTO patientDTO = patientBO.findById(selectedID);
        if (patientDTO != null) {
            lblPatientid.setText(patientDTO.getName());
        }
        if (selectedID != null) {
            double balance = patientRegBO.getBalanceByPatientId(selectedID);
          System.out.println(balance);
           lblbalance.setText(String.format("%.2f", balance));
        }
    }

    @FXML
    void DatePickerOnAction(ActionEvent event) {
        LocalDate localDate = datepicker.getValue();
        String pattern = "yyyy-MM-dd";
        String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
        lbldate.setText(datePattern);
    }

   // AddPayBO paymentBO = (AddPayBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ADDPAYMENT);

    AddPayBO addPayBO = new AddPayBOImpl();
   // AddPayDAO addPayDAO = new AddPayDAOImpl();
//
//    @FXML
//    void SaveOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
//        String paymentId = lblid.getText();
//        String patientId = lblstatus.getText();// Ensure this returns a valid PatientID
//        String paymentDate = lbldate.getText();
//        String status = combopatientid.getValue();
//        double amount = Double.parseDouble(txtamount.getText());
//
//        System.out.println("Payment ID: " + paymentId);
//        System.out.println("Patient ID: " + patientId);
//        System.out.println("Payment Date: " + paymentDate);
//        System.out.println("Status: " + status);
//        System.out.println("Amount: " + amount);
//
//        if (patientId == null || patientId.isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Please select a valid patient.").show();
//            return;
//        }
//
//        PaymentDTO paymentDTO = new PaymentDTO(paymentId, patientId, amount, paymentDate, status);
//        System.out.println("PaymentDTO Created:");
//        System.out.println("Payment ID: " + paymentDTO.getPaymentId());
//        System.out.println("Patient ID: " + paymentDTO.getPatientId());
//        System.out.println("Amount: " + paymentDTO.getAmount());
//        System.out.println("Payment Date: " + paymentDTO.getPaymentDate());
//        System.out.println("Status: " + paymentDTO.getStatus());
//
//        boolean isSaved = addPayBO.save(paymentDTO);
//        if (isSaved) {
//            new Alert(Alert.AlertType.INFORMATION, "Payment Saved SUCCESSFULLY 😎").show();
//        } else {
//            new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
//        }
//    }


    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        String paymentId = lblid.getText();
        String patientId = lblstatus.getText(); 
        String paymentDate = lbldate.getText();
        String status = combopatientid.getValue();
        String amountText = txtamount.getText();


        if (paymentId == null || paymentId.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Payment ID is required!").show();
            return;
        }

        if (patientId == null || patientId.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a valid patient!").show();
            return;
        }

        if (paymentDate == null || paymentDate.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Payment Date is required!").show();
            return;
        }

        if (status == null || status.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Payment Status is required!").show();
            return;
        }

        if (amountText == null || amountText.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Amount is required!").show();
            return;
        }

        if (!amountText.matches("\\d+(\\.\\d{1,2})?")) {
            new Alert(Alert.AlertType.ERROR, "Amount must be a valid number (example: 100 or 100.50)!").show();
            return;
        }

        double amount = Double.parseDouble(amountText);


        PaymentDTO paymentDTO = new PaymentDTO(paymentId, patientId, amount, paymentDate, status);


        boolean isSaved = addPayBO.save(paymentDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Payment Saved SUCCESSFULLY 😎").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
        }
    }


    @FXML
    void StatusComboOnAction(ActionEvent event) {
        String SelectedValue = comboStatus.getValue();
        lblstatus.setText(SelectedValue);
    }

    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);

    AddPayDAO payDAO = new AddPayDAOImpl();

//    private void loadPatientIDs() throws SQLException, IOException, ClassNotFoundException {
//        ArrayList<String> patientIds = patientBO.getAllPatientIds();
//       // combopatientid.getItems().addAll(patientIds);
//    }
    private void loadPatientIDs() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<String> patientIds = patientBO.getAllPatientIds();
        combopatientid.getItems().addAll(patientIds);
    }
    private void LoadNextID() throws SQLException, IOException {
        String nextID = payDAO.getNextId();
        lblid.setText(nextID);
    }

}
