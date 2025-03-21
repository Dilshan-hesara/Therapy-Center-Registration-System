package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PatientController {

    @FXML
    private TableView<?> PatientTable;

    @FXML
    private DatePicker birthdatepicker;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<?, ?> colbirthday;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colmedical;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colregister;

    @FXML
    private Label lblbirthday;

    @FXML
    private Label lblid;

    @FXML
    private Label lblregister;

    @FXML
    private Label lblrole;

    @FXML
    private DatePicker registerdatepicker;

    @FXML
    private TextField txtcontactnumber;

    @FXML
    private TextField txtmedical;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtsearch;

    @FXML
    void AppointmentOnAction(ActionEvent event) {

    }

    @FXML
    void BirthDatePickerOnAction(ActionEvent event) {

    }

    @FXML
    void DeleteOnAction(ActionEvent event) {

    }

    @FXML
    void RegisterDatePickerOnAction(ActionEvent event) {

    }

    @FXML
    void ResetOnAction(ActionEvent event) {

    }

    @FXML
    void SaveOnAction(ActionEvent event) {

    }

    @FXML
    void SearchOnAction(ActionEvent event) {

    }

    @FXML
    void TableOnClicked(MouseEvent event) {

    }

    @FXML
    void UpdateOnAction(ActionEvent event) {

    }

}
