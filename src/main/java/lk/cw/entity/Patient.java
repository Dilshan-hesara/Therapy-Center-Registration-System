package lk.cw.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "Patient")
public class Patient implements SuperEntity{
    @Id
    @Column(name = "PatientID")
   private String patientId;
    private String name;
    private Date birthday;
    private int contactNumber;
    private String medicalHistory;




}
