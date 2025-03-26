package lk.cw.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class TherapistTM {
    private String therapistId;
    private String therapistName;
    private String specialization;
    private String availability;
}
