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
import lk.cw.bo.custom.PatientBO;
import lk.cw.bo.custom.PatientRegBO;
import lk.cw.bo.custom.TherapistBO;
import lk.cw.bo.custom.TherapySessionBO;
import lk.cw.bo.custom.impl.PatientRegBOImpl;
import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.dao.custom.impl.PatientRegDAOImpl;
import lk.cw.dto.PatientDTO;
import lk.cw.dto.PaymentDTO;
import lk.cw.dto.TherapistDTO;
import lk.cw.dto.TherapySessionDTO;
import lk.cw.entity.Payment;
import lk.cw.tm.TherapySessionTM;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combostatus.getItems().addAll(Status);
        colid.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        coltherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colpatientid.setCellValueFactory(new PropertyValueFactory<>("patientId"));


        try {
            LoadNextID();
            loadTherapistIDs();
            loadPatientIDs();
            loadTableData();
            refreshPage();
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

        if (patientDTO != null) {
            lblpatientname.setText(patientDTO.getName());
        }

        if (selectedID != null) {
            double balance = patientRegBO.getBalanceByPatientId(selectedID);
            txtAvBlance.setText(String.format("%.2f", balance));

            checkStates();
        }
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

    private void checkStates() {
        String avPyStr = txtAvBlance.getText().trim();
        String amountStr = txtPay.getText().trim();

        double avPy = Double.parseDouble(avPyStr);
        double amount = Double.parseDouble(amountStr);

        double redu = avPy - amount; // Now this is numeric subtraction

        if (redu == 0 ) {
            states = "PAY COMPLETED";
            System.out.println("C");
        } else {
            states = "PAY PENDING";
            System.out.println("B");
        }
    }


    @FXML
    void SaveOnAction(ActionEvent event) {
        checkStates();



        //this is TAERAPEY Sesion table
        String sessionId = lblid.getText();
        String sessionDate = lbldate.getText();
        String sessionTime = txttime.getText();
        String status = combostatus.getValue();
        String therapistId = combotherapistId.getValue();
        String patientId = combopatientid.getValue();


        // i want save  payment table payment
        String payid  = "P001";
        String amount = txtPay.getText();
        String payDate = lbldate.getText();
        String payPatient = combopatientid.getValue();
        String States = states;

        ArrayList<PaymentDTO> paymentDTOS =new ArrayList<>();

        PaymentDTO paymentDTO = new PaymentDTO(
                payid,
                 amount,
                Double.parseDouble(payDate),
                   payPatient ,
                  States


        );
        paymentDTOS.add(paymentDTO);

//        System.out.println(payid);
//        System.out.println(payDate);
//        System.out.println(payPatient);
//        System.out.println(amount);
//        System.out.println(States);

        try {
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


    @FXML
    void TableOnClicked(MouseEvent event) {
        TherapySessionTM therapySessionTM = SessionTable.getSelectionModel().getSelectedItem();
        if (therapySessionTM != null) {
            lblid.setText(therapySessionTM.getSessionId());
            lbldate.setText(String.valueOf(therapySessionTM.getSessionDate()));
            txttime.setText(therapySessionTM.getSessionTime());
            lblstatus.setText(therapySessionTM.getStatus());
            combopatientid.setValue(therapySessionTM.getPatientId());
            combotherapistId.setValue(therapySessionTM.getTherapistId());



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

        txttime.setText("");
        lbldate.setText("");
        lblstatus.setText("");
        lbltherapistname.setText("");
        lblpatientname.setText("");
    }


}
