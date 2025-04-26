package lk.cw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapySessionDTO {
    private String sessionId;
    private Date sessionDate;
    private String sessionTime;
    private String status;
    private String therapistId;
    private String patientId;

    private ArrayList<PaymentDTO>paymentDTOS;

    public TherapySessionDTO(String sessionId, String sessionDate, String sessionTime, String status, String therapistId, String patientId, ArrayList<PaymentDTO> paymentDTOS) {

                this.sessionId = sessionId;
        this.sessionDate = Date.valueOf(sessionDate);
        this.sessionTime = sessionTime;
        this.status = status;
        this.therapistId = therapistId;
        this.patientId = patientId;
        this.paymentDTOS = paymentDTOS;

    }

    public TherapySessionDTO(String sessionId, String s, String sessionTime, String status, String therapistId, String patientId) {
        this.sessionId = sessionId;
        this.sessionDate = Date.valueOf(s);
        this.sessionTime = sessionTime;
        this.status = status;
        this.therapistId = therapistId;
        this.patientId = patientId;
    }

//    public TherapySessionDTO(String sessionId, String sessionDate, String sessionTime, String status, String therapistId, String patientId) {
//        this.sessionId = sessionId;
//        this.sessionDate = Date.valueOf(sessionDate);
//        this.sessionTime = sessionTime;
//        this.status = status;
//        this.therapistId = therapistId;
//        this.patientId = patientId;
//
//    }
}
