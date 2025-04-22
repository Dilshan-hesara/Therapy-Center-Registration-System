package lk.cw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Patient_Registration")
public class Patient_Registration implements SuperEntity{
    @Id
    private String registrationId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "programId")
    private TherapyProgram therapyProgram;

    private Date registrationDate;
    private int sessionCount;
    private double registerFee;
    private double balance;


    public Patient_Registration(String registrationId, Patient patient, TherapyProgram therapyProgram, Date registrationDate, double registerFee, double balance) {

        this.registrationId = registrationId;
        this.patient = patient;
        this.therapyProgram = therapyProgram;
        this.registrationDate = registrationDate;
        this.registerFee = registerFee;
        this.balance = balance;

    }
}
