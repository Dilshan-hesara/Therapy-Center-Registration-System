package lk.cw.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "TherapyPrograms")
public class TherapyProgram {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "program_id")
        private int programId;

        private String name;

        private String duration;

        private double cost;

        private String description;

        @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL)
        private Set<TherapySession> therapySessions;
}
