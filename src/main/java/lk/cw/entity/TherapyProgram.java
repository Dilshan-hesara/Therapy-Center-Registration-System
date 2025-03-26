package lk.cw.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Therapy_Program")
public class TherapyProgram implements SuperEntity{
    @Id
    private String programId;
    private String programName;
    private String duration;
    private double cost;
    private String Description;


    public TherapyProgram(String programId, String programName, String duration, double cost, String description) {
        this.programId = programId;
        this.programName = programName;
        this.duration = duration;
        this.cost = cost;
        Description = description;
    }
}
