package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class PatientRegController {

    @FXML
    private TableView<?> RegistrationTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<?, ?> colcount;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> colpatid;

    @FXML
    private TableColumn<?, ?> colproid;

    @FXML
    private TableColumn<?, ?> colregid;

    @FXML
    private ComboBox<?> combopatientid;

    @FXML
    private ComboBox<?> comboprogramId;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label lblcount;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblid;

    @FXML
    private Label lblpatientid;

    @FXML
    private Label lblprogramid;

    @FXML
    private Label lbltherapistname;

    @FXML
    void ComboPatientIdOnAction(ActionEvent event) {

    }

    @FXML
    void ComboProgramIdOnAction(ActionEvent event) {

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
