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
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.PatientBO;
import lk.cw.bo.custom.PatientRegBO;
import lk.cw.bo.custom.TherapistBO;
import lk.cw.bo.custom.TherapySessionBO;
import lk.cw.bo.custom.impl.PatientRegBOImpl;
import lk.cw.bo.custom.impl.TherapySessionBOImpl;
import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.AddPayDAO;
import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.dao.custom.impl.AddPayDAOImpl;
import lk.cw.dao.custom.impl.PatientRegDAOImpl;
import lk.cw.dto.PatientDTO;
import lk.cw.dto.PaymentDTO;
import lk.cw.dto.TherapistDTO;
import lk.cw.dto.TherapySessionDTO;
import lk.cw.entity.Payment;
import lk.cw.entity.Therapy_Session;
import lk.cw.tm.TherapySessionTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TherapeySessionController implements Initializable {

    @FXML
    private TableView<TherapySessionTM> SessionTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colpatientid;

    @FXML
    private TableColumn<?, ?> colstatus;

    @FXML
    private TableColumn<?, ?> coltherapist;

    @FXML
    private TableColumn<?, ?> coltime;

    @FXML
    private ComboBox<String> combopatientid;

    @FXML
    private ComboBox<String> combostatus;

    @FXML
    private ComboBox<String> combotherapistId;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblid;

    @FXML
    private Label lblpatientname;

    @FXML
    private Label lblstatus;

    @FXML
    private Label lbltherapistname;

    @FXML
    private TextField txttime;

    @FXML
    private Label txtAvSessions;


    @FXML
    private Button btnInvoice;

    @FXML
    private Label txtAvBlance;


    @FXML
    private TextField txtPay;

    @FXML
    private Label txtSessionCount;


    TherapistBO therapistBO = (TherapistBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPIST);
 TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYSESSION);
    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);

    @FXML
    void InvoiecOnAction(ActionEvent event) {
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
            params.put("P_AvBlanec", "229");


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
    @FXML
    private Button btnsearch;

    @FXML
    private TextField txtsearch;

    public void SearchOnAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String input = txtsearch.getText().trim();

        try {
            List<Therapy_Session> sessionList = null;


            if (input.matches("T\\d+")) {
                sessionList = therapySessionBO.searchTherapistTherapySession(input);
            }


            if (sessionList == null || sessionList.isEmpty()) {
                sessionList = therapySessionBO.searchTherapySession(input);
            }


            if (sessionList == null || sessionList.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "No sessions found for: " + input).show();
                return;
            }

            ObservableList<TherapySessionDTO> observableList = FXCollections.observableArrayList();

            for (Therapy_Session session : sessionList) {
                observableList.add(new TherapySessionDTO(
                        session.getSessionId(),
                        String.valueOf(session.getSessionDate()),
                        session.getSessionTime(),
                        session.getStatus(),
                        session.getTherapist().getTherapistId(),
                        session.getPatient().getPatientId()


                ));
            }


            String fxmlPath = input.matches("T\\d+")
                    ? "/view/TherapistSearch.fxml"
                    : "/view/Search.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent load = loader.load();

            if (fxmlPath.contains("TherapistSearch")) {
                TherapistSearchController controller = loader.getController();
                controller.setSessionList(observableList);
            } else {
                SearchController controller = loader.getController();
                controller.setSessionList(observableList);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Therapy Session Search");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnsearch.getScene().getWindow());
            stage.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Search failed due to a database error.").show();
        }
    }

    @FXML
    private ComboBox<String> selectTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combostatus.getItems().addAll(Status);
        colid.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        coltherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colpatientid.setCellValueFactory(new PropertyValueFactory<>("patientId"));

        selectTime.getItems().addAll("08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
                "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM");


        txtAvBlance.setText(String.valueOf(0));

        avm =0;
        btnInvoice.setDisable(true);
        try {
            LoadNextID();
            loadTherapistIDs();
            loadPatientIDs();
            loadTableData();
            refreshPage();
            LoadPayNextID();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load LeaveID").show();
        }

    }
    private final String[] Status = {"Scheduled", "Completed", "Cancelled"};

    PatientRegDAO patientRegBO = new PatientRegDAOImpl();
    @FXML
    void ComboPatientIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String selectedID = combopatientid.getValue();
        PatientDTO patientDTO = patientBO.findById(selectedID);

        getSessionCount(selectedID);
        if (patientDTO != null) {
            lblpatientname.setText(patientDTO.getName());
        }

        if (selectedID != null) {
            double balance = patientRegBO.getBalanceByPatientId(selectedID);
            txtAvBlance.setText(String.format("%.2f", balance));

            //checkStates();
        }

    }
    AddPayDAO payDAO = new AddPayDAOImpl();

    String PAYID;
    private void LoadPayNextID() throws SQLException, IOException {
        String nextID = payDAO.getNextId();
        PAYID = nextID;
    }

    @FXML
    private Label lblsesCount;

    PatientRegDAO patientRegDAO = new PatientRegDAOImpl();
    private void getSessionCount(String selectedID) throws IOException {
        String patienid = selectedID;
       String sc = String.valueOf(patientRegDAO.getSessionCount(patienid));
        System.out.println(sc);
        lblsesCount.setText(sc);

    }



    @FXML
    void ComboStatusOnAction(ActionEvent event) {
        String SelectedValue = combostatus.getValue();
        lblstatus.setText(SelectedValue);

    }


    @FXML
    void ComboTherapistIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedID = combotherapistId.getValue();
        TherapistDTO therapistDTO = therapistBO.findById(selectedID);

        if (therapistDTO != null) {
            lbltherapistname.setText(therapistDTO.getTherapistName());
        }

    }

    @FXML
    void DatePickerOnAction(ActionEvent event) {
        LocalDate localDate = datepicker.getValue();
        String pattern = "yyyy-MM-dd";
        String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
        lbldate.setText(datePattern);

    }

    @FXML
    void DeleteOnAction(ActionEvent event) {

        checkStates();
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        refreshPage();
    }

    String states;

//    private void checkStates() {
//        String avPy = txtAvBlance.getText();
//        if (avPy == "0") {
//            states = "PAY COMPLETED";
//        }else{
//            states = "PAY PANDING";
//        }
//    }

    String AVBL;
    private void checkStates() {
        String avPyStr = txtAvBlance.getText().trim();
        String amountStr = txtPay.getText().trim();

        double avPy = Double.parseDouble(avPyStr);
        double amount = Double.parseDouble(amountStr);


        AVBL =avPyStr;


        double redu = avPy - amount;

        if (redu == 0 ) {
            states = "PAY-for Session[Completed]";
            System.out.println("C");
        } else {
            states = "PAY-for Session";
            System.out.println("B");
        }
    }

    int avm;
    private void avblance(){

        String avPyStr = txtAvBlance.getText().trim();

         avm = Integer.parseInt(avPyStr);
        System.out.println(avm);
    }



    @FXML
    void SaveOnAction(ActionEvent event) {
        //checkStates();



        // TAERAPEY Sesion table
        String sessionId = lblid.getText();
        String sessionDate = lbldate.getText();
        String sessionTime = selectTime.getValue();
        String status = combostatus.getValue();
        String therapistId = combotherapistId.getValue();
        String patientId = combopatientid.getValue();


        //  payment table payment
        String payid  = PAYID;
        String amount = txtPay.getText();
        String payDate = lbldate.getText();
//        String payDate = "22";
//        String payPatient = combopatientid.getValue();
        String payPatient = combopatientid.getValue();
        String States = "PAY-for Session";

        if (patientId == null || patientId.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Patient ID is required!").show();
            return;
        }
        AVBL();

        int av = avm;
        if (av == 0) {
            new Alert(Alert.AlertType.ERROR, "First, Register a Patient!").show();
            return;
        }
        if ( amount.isEmpty()) {
            System.out.println("Validation failed: Amount is empty or null");
            new Alert(Alert.AlertType.ERROR, "Payment amount is required!").showAndWait();
            return;
        }
        checkStates();
        System.out.println("Amount: '" + amount + "'");

        if (amount == null || amount.trim().isEmpty()) {
            System.out.println("Validation failed: Amount is empty or null");
            new Alert(Alert.AlertType.ERROR, "Payment amount is required!").showAndWait();
            return;
        }





        String Avalablablance = AVBL;


//        checkStates();

        double availableBalance = Double.parseDouble(Avalablablance);
        double enteredAmount = Double.parseDouble(amount);




        if (enteredAmount > availableBalance) {
            new Alert(Alert.AlertType.ERROR, "Entered amount cannot be greater than available balance!").show();
            return;
        }


        try {
            double amountValue = Double.parseDouble(amount.trim());
            if (amountValue <= 0) {
                new Alert(Alert.AlertType.ERROR, "Amount must be greater than 0!").showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Amount must be a valid number (e.g., 100 or 100.50)!").showAndWait();
            return;
        }

        if (sessionId == null || sessionId.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Session ID is required!").show();
            return;
        }
        if (sessionDate == null || sessionDate.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Session date is required!").show();
            return;
        }
        if (sessionTime == null || sessionTime.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Session time is required!").show();
            return;
        }
        if (status == null || status.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Status is required!").show();
            return;
        }
        if (combotherapistId.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "No therapists available. Please add a therapist first!").show();
            return;
        }
        if (combopatientid.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "No patients available. Please add a patient first!").show();
            return;
        }

        //String therapistId = combotherapistId.getValue();
 //       String patientId = combopatientid.getValue();



        if (therapistId == null || therapistId.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Therapist ID is required!").show();
            return;
        }
        if (patientId == null || patientId.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Patient ID is required!").show();
            return;
        }



        // Validate amount format
        if (!amount.matches("^\\d+(\\.\\d{1,2})?$")) {
            new Alert(Alert.AlertType.ERROR, "Amount must be a valid number (Example: 100 or 100.50)!").show();
            return;
        }

        try {
            double amountValue = Double.parseDouble(amount);
            if (amountValue <= 0) {
                new Alert(Alert.AlertType.ERROR, "Amount must be greater than 0!").show();
                return;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount format!").show();
            return;
        }




        ArrayList<PaymentDTO> paymentDTOS =new ArrayList<>();

        PaymentDTO paymentDTO = new PaymentDTO(
                payid,
                 amount,

                payDate,
                   payPatient ,
                  States


        );
        paymentDTOS.add(paymentDTO);

        System.out.println(payid);
        System.out.println(payDate);
        System.out.println(payPatient);
        System.out.println(amount);
        System.out.println(States);


        try {
            if (sessionId == null || sessionId.trim().isEmpty() ||
                    sessionDate == null || sessionDate.trim().isEmpty() ||
                    sessionTime == null || sessionTime.trim().isEmpty() ||
                    status == null || status.trim().isEmpty() ||
                    therapistId == null || therapistId.trim().isEmpty() ||
                    patientId == null || patientId.trim().isEmpty()) {

                new Alert(Alert.AlertType.ERROR, "All session fields must be selected and filled!").show();
                return;
            }

            if (payid == null || payid.trim().isEmpty() ||
                    amount == null || amount.trim().isEmpty() ||
                    payDate == null || payDate.trim().isEmpty() ||
                    payPatient == null || payPatient.trim().isEmpty()) {

                new Alert(Alert.AlertType.ERROR, "All payment fields must be selected and filled!").show();
                return;
            }

            if (!amount.matches("\\d+(\\.\\d{1,2})?")) {
                new Alert(Alert.AlertType.ERROR, "Amount must be a valid number (Example: 100 or 100.50)!").show();
                return;
            }

            if (Double.parseDouble(amount) <= 0) {
                new Alert(Alert.AlertType.ERROR, "Amount must be greater than 0!").show();
                return;
            }

            TherapySessionDTO therapySessionDTO = new TherapySessionDTO(
                    sessionId, sessionDate, sessionTime, status, therapistId, patientId,paymentDTOS
            );

            boolean isRegistered = therapySessionBO.save(therapySessionDTO);

            if (isRegistered) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "User Saved SUCCESSFULLY 😎").show();
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

    private void AVBL() {
        avm = (int) Double.parseDouble(txtAvBlance.getText());
        System.out.println(avm);
    }


    //
//    @FXML
//    void SaveOnAction(ActionEvent event) {
//        try {
//
//
//            // Get all values first
//            String sessionId = lblid.getText();
//            String sessionDate = lbldate.getText();
//            String sessionTime = selectTime.getValue();
//            String status = combostatus.getValue();
//            String therapistId =combotherapistId.getValue();
//            String patientId = combopatientid.getValue();
//            String amount = txtPay.getText();
//            String payPatient = combopatientid.getValue();
//            String States = states;
//
//            // Validate session fields
//
//            // Validate payment fields
//            // Get amount from TextField
////            String amount = txtPay.getText();
//
//            if (patientId == null || patientId.trim().isEmpty()) {
//                new Alert(Alert.AlertType.ERROR, "Patient ID is required!").show();
//                return;
//            }
//
//
//
//
//// Debug output
//            System.out.println("Amount: '" + amount + "'");
//
//// Check for null, empty, or whitespace-only
//            if (amount == null || amount.trim().isEmpty()) {
//                System.out.println("Validation failed: Amount is empty or null");
//                new Alert(Alert.AlertType.ERROR, "Payment amount is required!").showAndWait(); // Use showAndWait()
//                return;
//            }
//
//            checkStates();
//
//
//// Check if amount is a valid number
//            try {
//                double amountValue = Double.parseDouble(amount.trim());
//                if (amountValue <= 0) {
//                    new Alert(Alert.AlertType.ERROR, "Amount must be greater than 0!").showAndWait();
//                    return;
//                }
//            } catch (NumberFormatException e) {
//                new Alert(Alert.AlertType.ERROR, "Amount must be a valid number (e.g., 100 or 100.50)!").showAndWait();
//                return;
//            }
//
//            if (sessionId == null || sessionId.trim().isEmpty()) {
//                new Alert(Alert.AlertType.ERROR, "Session ID is required!").show();
//                return;
//            }
//            if (sessionDate == null || sessionDate.trim().isEmpty()) {
//                new Alert(Alert.AlertType.ERROR, "Session date is required!").show();
//                return;
//            }
//            if (sessionTime == null || sessionTime.trim().isEmpty()) {
//                new Alert(Alert.AlertType.ERROR, "Session time is required!").show();
//                return;
//            }
//            if (status == null || status.trim().isEmpty()) {
//                new Alert(Alert.AlertType.ERROR, "Status is required!").show();
//                return;
//            }
//            if (therapistId == null || therapistId.trim().isEmpty()) {
//                new Alert(Alert.AlertType.ERROR, "Therapist ID is required!").show();
//                return;
//            }
//            if (patientId == null || patientId.trim().isEmpty()) {
//                new Alert(Alert.AlertType.ERROR, "Patient ID is required!").show();
//                return;
//            }
//
//
//
//            // Validate amount format
//            if (!amount.matches("^\\d+(\\.\\d{1,2})?$")) {
//                new Alert(Alert.AlertType.ERROR, "Amount must be a valid number (Example: 100 or 100.50)!").show();
//                return;
//            }
//
//            // Validate amount value
//            try {
//                double amountValue = Double.parseDouble(amount);
//                if (amountValue <= 0) {
//                    new Alert(Alert.AlertType.ERROR, "Amount must be greater than 0!").show();
//                    return;
//                }
//            } catch (NumberFormatException e) {
//                new Alert(Alert.AlertType.ERROR, "Invalid amount format!").show();
//                return;
//            }
//
//            // Create DTOs
//            PaymentDTO paymentDTO = new PaymentDTO(
//                    PAYID,
//                    amount,
//                    sessionDate, // Using same date as session
//                    payPatient,
//                    States
//            );
//
//            ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
//            paymentDTOS.add(paymentDTO);
//
//            TherapySessionDTO therapySessionDTO = new TherapySessionDTO(
//                    sessionId, sessionDate, sessionTime, status, therapistId, patientId, paymentDTOS
//            );
//
//            // Save to database
//            boolean isRegistered = therapySessionBO.save(therapySessionDTO);
//
//            if (isRegistered) {
//                refreshPage();
//                new Alert(Alert.AlertType.INFORMATION, "Session Saved Successfully!").show();
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Failed to save session. Please try again.").show();
//            }
//        } catch (IOException e) {
//            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
//            e.printStackTrace();
//        } catch (SQLException | ClassNotFoundException e) {
//            new Alert(Alert.AlertType.ERROR, "Database error occurred: " + e.getMessage()).show();
//            e.printStackTrace();
//        } catch (Exception e) {
//            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
//            e.printStackTrace();
//        }
//    }
    @FXML
    void TableOnClicked(MouseEvent event) {
        TherapySessionTM therapySessionTM = SessionTable.getSelectionModel().getSelectedItem();
        if (therapySessionTM != null) {


            btnInvoice.setDisable(false);


            btnsave.setDisable(true);
        }

    }



    @FXML
    void UpdateOnAction(ActionEvent event) {
//        String sessionId = lblid.getText();
//        String sessionDate = lbldate.getText();
//        String sessionTime = txttime.getText();
//        String status = combostatus.getValue();
//        String therapistId = combotherapistId.getValue();
//        String patientId = combopatientid.getValue();
//
//        try {
//            TherapySessionDTO therapySessionDTO = new TherapySessionDTO(
//                    sessionId, sessionDate, sessionTime, status, therapistId, patientId
//            );
//
//            boolean isRegistered = therapySessionBO.update(therapySessionDTO);
//
//            if (isRegistered) {
//                refreshPage();
//                new Alert(Alert.AlertType.INFORMATION, "User Saved SUCCESSFULLY 😎").show();
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

        checkStates();
    }
    private void loadTherapistIDs() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> therapistIds = therapistBO.getAllTherapistIDs();
        combotherapistId.getItems().addAll(therapistIds);
    }
    private void loadPatientIDs() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<String> patientIds = patientBO.getAllPatientIds();
        combopatientid.getItems().addAll(patientIds);
    }
    private void LoadNextID() throws SQLException, IOException {
        String nextID = therapySessionBO.getNextId();
        lblid.setText(nextID);
    }
    private void loadTableData() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<TherapySessionDTO> therapySessionDTOS = (ArrayList<TherapySessionDTO>) therapySessionBO.getAll();
        ObservableList<TherapySessionTM> therapySessionTMS = FXCollections.observableArrayList();

        for (TherapySessionDTO therapySessionDTO : therapySessionDTOS) {
            TherapySessionTM therapySessionTM = new TherapySessionTM(
                    therapySessionDTO.getSessionId(),
                    therapySessionDTO.getSessionDate(),
                    therapySessionDTO.getSessionTime(),
                    therapySessionDTO.getStatus(),
                    therapySessionDTO.getTherapistId(),
                    therapySessionDTO.getPatientId()


            );
            therapySessionTMS.add(therapySessionTM);
        }
        SessionTable.setItems(therapySessionTMS);
    }
    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();

        btnsave.setDisable(false);

        lbldate.setText("");
        lblstatus.setText("");
        lbltherapistname.setText("");
        lblpatientname.setText("");
    }


}
