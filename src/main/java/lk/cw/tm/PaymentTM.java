package lk.cw.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentTM {
    private String paymentId;
    private String patientId;
    private double amount;
    private String paymentDate;
    private String Status;


    public PaymentTM(String paymentId, double amount, String paymentDate, String status, String patientId, Object o, int i) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.Status = status;
        this.patientId = patientId;

    }

    public PaymentTM(String paymentId, double amount, String paymentDate, String status, String patientId) {

        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.Status = status;
        this.patientId = patientId;

    }
}


