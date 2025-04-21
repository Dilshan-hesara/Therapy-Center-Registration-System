package lk.cw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.PatientBO;
import lk.cw.bo.custom.PatientRegBO;
import lk.cw.bo.custom.PaymentBO;
import lk.cw.dao.custom.PaymentDAO;
import lk.cw.dao.custom.impl.PaymentDAOImpl;
import lk.cw.dto.PaymentDTO;
import lk.cw.tm.PaymentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private TableView<PaymentTM> PaymentTable;


    @FXML
    private TableColumn<PaymentTM,Double> colamount;

    @FXML
    private TableColumn<PaymentTM, Date> coldate;

    @FXML
    private TableColumn<PaymentTM,String> colpatid;

    @FXML
    private TableColumn<PaymentTM,String> colpaymentid;

    @FXML
    private TableColumn<PaymentTM,String> colstatus;



    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
//    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);
//    PatientRegBO patientRegistrationBO = (PatientRegBO ) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REG);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//       // comboStatus.getItems().addAll(Status);
        colpaymentid.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colpatid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        System.out.println("eyyhtjy");
        try {
          //  LoadNextID();
//
        //    loadPatientIDs();

       //     loadTableData();

              loadTableData();
          //  refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load PaymentId").show();
        }
    }

    private void loadTableData() throws SQLException, IOException {
        ArrayList<PaymentDTO> paymentDTOS = (ArrayList<PaymentDTO>) paymentBO.getAll();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getPaymentId(),
                    paymentDTO.getPatientId(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getStatus()
            );
            paymentTMS.add(paymentTM);
        }
       PaymentTable.setItems(paymentTMS);
    }

    @FXML
    void InvoiceOnAction(ActionEvent event) throws SQLException, IOException {

        loadTableData();
    }

    @FXML
    void TableOnClicked(MouseEvent event) {
      //  PaymentTM paymentTM = (PaymentTM) PaymentTable.getSelectionModel().getSelectedItem();
    //    if (paymentTM != null) {
//            lblid.setText(paymentTM.getPaymentId());
//            combopatientid.setValue(paymentTM.getPatientId());
//            txtamount.setText(String.valueOf(paymentTM.getAmount()));
    //        lbldate.setText(String.valueOf(paymentTM.getPaymentDate()));


//
//            btndelete.setDisable(false);
//            btnsave.setDisable(true);
//            btnupdate.setDisable(false);
        //}
    }

    @FXML
    void addPayOnAction(ActionEvent event) {

    }


}
