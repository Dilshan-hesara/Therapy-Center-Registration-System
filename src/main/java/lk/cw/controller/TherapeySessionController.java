package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TherapeySessionController {

    @FXML
    private TableView<?> SessionTable;

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
    private ComboBox<?> combopatientid;

    @FXML
    private ComboBox<?> combostatus;

    @FXML
    private ComboBox<?> combotherapistId;

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
    void ComboPatientIdOnAction(ActionEvent event) {

    }

    @FXML
    void ComboStatusOnAction(ActionEvent event) {

    }

    @FXML
    void ComboTherapistIdOnAction(ActionEvent event) {

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

    }

    @FXML
    void TableOnClicked(MouseEvent event) {

    }

    @FXML
    void UpdateOnAction(ActionEvent event) {

    }

}
