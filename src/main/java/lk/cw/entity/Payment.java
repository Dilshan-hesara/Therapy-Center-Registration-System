package lk.cw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "Payment")
public class Payment implements SuperEntity{
    @Id
    private String paymentId;
    private double amount;
    private String paymentDate;
    private String Status;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;


    public Payment(String paymentId, Patient patient, double amount, String paymentDate, String status) {
        this.paymentId = paymentId;
        this.patient = patient;
        this.amount = amount;
        this.paymentDate = paymentDate;
        Status = status;

    }


    public Payment(String paymentId, String patientId, double amount, String paymentDate, String status) {
        this.paymentId = paymentId;
        this.patient = patient;
        this.amount = amount;
        this.paymentDate = paymentDate;
        Status = status;

    }

    public Payment(String paymentId, String status, double amount, Date paymentDate, Patient patient) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate.toString();
        this.patient = patient;
        this.Status = status;
    }

    public Payment(String paymentId, String status, double amount, String paymentDate, Patient patient) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.patient = patient;
        this.Status = status;

    }
}
