package lk.cw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class PaymentController {

    @FXML
    private TableView<?> PaymentTable;

    @FXML
    private TableColumn<?, ?> colamount;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> colpatid;

    @FXML
    private TableColumn<?, ?> colpaymentid;

    @FXML
    private TableColumn<?, ?> colstatus;

    @FXML
    private Label lblPatientid;

    @FXML
    private Label lbldate;

    @FXML
    void InvoiceOnAction(ActionEvent event) {

    }

    @FXML
    void TableOnClicked(MouseEvent event) {

    }

}
