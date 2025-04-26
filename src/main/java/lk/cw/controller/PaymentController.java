package lk.cw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.PatientBO;
import lk.cw.bo.custom.PatientRegBO;
import lk.cw.bo.custom.PaymentBO;
import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.dao.custom.PaymentDAO;
import lk.cw.dao.custom.impl.PatientRegDAOImpl;
import lk.cw.dao.custom.impl.PaymentDAOImpl;
import lk.cw.dto.PaymentDTO;
import lk.cw.entity.Payment;
import lk.cw.tm.PaymentTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

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
    private TextField txtsearch;

    @FXML
    private Button btnsearch;


    @FXML
    void PaymentSearchOnAction(ActionEvent event) {
        String name = txtsearch.getText();

        try{
            List<Payment> paymentList = paymentBO.searchPayment(name);
            ObservableList<PaymentDTO> observableList = FXCollections.observableArrayList();

            for (Payment payment : paymentList) {
                observableList.add(new PaymentDTO(
                        payment.getPaymentId(),
                        payment.getAmount(),
                        payment.getPaymentDate(),
                        payment.getStatus(),
                        payment.getPatient()
                ));
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PaymentSearch.fxml"));
            Parent load = loader.load();

            PaymentSearchController paymentSearchController = loader.getController();
            paymentSearchController.setSessionList(observableList);
            System.out.println(observableList);
            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Search");

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnsearch.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSessionList(ObservableList<PaymentDTO> observableList) {


    }

    PatientRegDAO patientRegBO = new PatientRegDAOImpl();
    @FXML
    private Button invoisbtn;
    String av ;
    @FXML
    void InvoiceOnAction(ActionEvent event) throws IOException {


     //   String av = "300";

      ///  PaymentTM paymentTM = new PaymentTM();



        System.out.println(av+"dfffffffffffff");
//        double balance = patientRegBO.getBalanceByPatientId(patientId);
//         String av = (String.format("%.2f", balance));

        System.out.println(av);

   //    paymentTM = PaymentTable.getSelectionModel().getSelectedItem();

//        if(paymentTM == null){
//            return;
//        }
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/PaymentInvoice.jrxml"
                            ));

            Session session = FactoryConfiguration.getInstance().getSession();
            session.beginTransaction();

            Connection connection = session.doReturningWork(conn -> conn);

            Map<String, Object> params = new HashMap<>();
            params.put("P_paymentId", "P005");
            params.put("P_AvBlanec", av);


            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    params,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
            session.getTransaction().commit();
            session.close();
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
//           e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


String payIDRe ;
    @FXML
    void TableOnClicked(MouseEvent event) throws IOException {
        PaymentTM paymentTM = (PaymentTM) PaymentTable.getSelectionModel().getSelectedItem();
        if (paymentTM != null) {
            payIDRe=(paymentTM.getPaymentId());
           String paid = (paymentTM.getPatientId());


                    double balance = patientRegBO.getBalanceByPatientId(paid);
          av = (String.format("%.2f", balance));
//            txtamount.setText(String.valueOf(paymentTM.getAmount()));
//            lbldate.setText(String.valueOf(paymentTM.getPaymentDate()));


//
//            btndelete.setDisable(false);
//            btnsave.setDisable(true);
//            btnupdate.setDisable(false);
        }
    }

    @FXML
    private Button btnAddpay;

    @FXML
    void addPayOnAction(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddPaymentFrom.fxml"));
        Parent load = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.setTitle("Payment From");

        stage.initModality(Modality.APPLICATION_MODAL);

        Window underWindow = btnAddpay.getScene().getWindow();
        stage.initOwner(underWindow);

        stage.showAndWait();

    }


}
