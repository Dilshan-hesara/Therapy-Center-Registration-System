package lk.cw.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentTM {
    private String paymentId;
    private String patientId;
    private double amount;
    private String paymentDate;
    private String Status;


}


