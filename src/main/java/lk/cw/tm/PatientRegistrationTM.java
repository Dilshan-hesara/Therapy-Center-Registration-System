package lk.cw.tm;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class PatientRegistrationTM {
    private String registrationId;
    private String patientId;
    private String programId;
    private Date registrationDate;
    private int sessionCount;
    private double registerFee;
    private double balance;

}
