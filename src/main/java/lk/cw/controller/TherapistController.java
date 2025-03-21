package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TherapistController {

    @FXML
    private TableView<?> TherapistTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<?, ?> colavailable;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colspecail;

    @FXML
    private Label lblid;

    @FXML
    private TextField txtavailable;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtspecail;

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
