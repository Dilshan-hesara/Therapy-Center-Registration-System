package lk.cw.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.cw.dto.TherapySessionDTO;
import lk.cw.tm.TherapySessionTM;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TherapistSearchController implements Initializable {

    @FXML
    private TableView<TherapySessionTM> ThearapistSearchTable;

    @FXML
    private TableColumn<TherapySessionTM, Date> coldate;

    @FXML
    private TableColumn<TherapySessionTM,String> colpatient;

    @FXML
    private TableColumn<TherapySessionTM,String> colstatus;

    @FXML
    private TableColumn<TherapySessionTM,String> coltherapist;

    @FXML
    private TableColumn<TherapySessionTM,String> coltime;


    @FXML
    private ComboBox<String> comSelectAv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns(); // Table setup වෙන function එකකට තියාගමු

        loadAvailabilityOptions(); // ComboBox එක load වෙන function එකකට
    }

    private void setupTableColumns() {
        coltherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colpatient.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadAvailabilityOptions() {
        comSelectAv.getItems().addAll("Available ✅", "Busy ❌");
    }




    public void setSessionList(List<TherapySessionDTO> dtoList) throws SQLException, ClassNotFoundException, IOException {
        ObservableList<TherapySessionTM> sessionList = FXCollections.observableArrayList();

        for(TherapySessionDTO dto : dtoList){
            sessionList.add(new TherapySessionTM(
                    "",//dto.getSessionId()
                    dto.getSessionDate(),
                    dto.getSessionTime(),
                    dto.getStatus(),
                    dto.getTherapistId(),
                    dto.getPatientId()

            ));
        }
        ThearapistSearchTable.setItems(sessionList);
    }
}
