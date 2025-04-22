package lk.cw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data


    public class PaymentDTO {
    private String paymentId;
    private String status;
    private double amount;
    private String paymentDate;
    private String patientId;


    public PaymentDTO(String payid, String amount, String payDate, String payPatient, String states) {
        this.paymentId = payid;
        this.amount = Double.parseDouble(amount);
        this.paymentDate = payDate;
        this.patientId = payPatient;
        this.status = states;

    }
}


