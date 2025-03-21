package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TherapyProgrameController {

    @FXML
    private TableView<?> TherapistTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> coldur;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblregister;

    @FXML
    private Label lblstatus;

    @FXML
    private Label lbltherapistid;

    @FXML
    private Label lbltherapistname;

    @FXML
    private Label lprogramid;

    @FXML
    private TextField txtdure;

    @FXML
    private TextField txtfee;

    @FXML
    private TextField txtname;

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
