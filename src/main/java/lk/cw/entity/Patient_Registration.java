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


}
