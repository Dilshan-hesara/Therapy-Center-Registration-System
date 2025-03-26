package lk.cw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientRegistrationDTO {
    private String registrationId;
    private String patientId;
    private String programId;
    private Date registrationDate;
    private int sessionCount;


    public PatientRegistrationDTO(String registrationId, String patientId, String programId, String registrationDate) {
        this.registrationId = registrationId;
        this.patientId = patientId;
        this.programId = programId;
        this.registrationDate = Date.valueOf(registrationDate);

    }
}
