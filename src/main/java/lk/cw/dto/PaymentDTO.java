package lk.cw.dto;

import lk.cw.entity.Patient;
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

    public PaymentDTO(String paymentId, double amount, String paymentDate, String status, Patient patient) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.patientId = patient.getPatientId();
        this.status = status;
    }
//    public PaymentDTO(String payid, String amount, String payDate, String payPatient, String states) {
//        this.paymentId = payid;
//        this.amount = Double.parseDouble(payPatient);
//        this.paymentDate = payDate;
//        this.patientId = amount;
//        this.status = states;
//
//    }


//    public PaymentDTO(String payid, String amount, String payDate, String payPatient, String states) {
//        this.paymentId = payid;
//        this.amount = Double.parseDouble(amount);
//        this.paymentDate = payDate;
//        this.patientId = payPatient;
//        this.status = states;
//
//    }


}


