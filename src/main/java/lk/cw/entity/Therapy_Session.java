package lk.cw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Therapy_Session")
public class Therapy_Session implements SuperEntity{
    @Id
    private String sessionId;

    @ManyToOne

    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne

    @JoinColumn(name = "therapistId")
    private Therapist therapist;

    private Date sessionDate;
    private String sessionTime;
    private String status;


}
