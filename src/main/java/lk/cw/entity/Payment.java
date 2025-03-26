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
    private Date paymentDate;
    private String Status;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;




}
