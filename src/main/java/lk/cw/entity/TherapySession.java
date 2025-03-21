package lk.cw.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "TherapySessions")
public class TherapySession {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "session_id")
        private int sessionId;

        @ManyToOne
        @JoinColumn(name = "patient_id", nullable = false)
        private Patient patient;

        @ManyToOne
        @JoinColumn(name = "therapist_id", nullable = false)
        private Therapist therapist;

        @ManyToOne
        @JoinColumn(name = "program_id", nullable = false)
        private TherapyProgram therapyProgram;



        @Column(name = "session_date", nullable = false)
        private Date sessionDate;

        @Column(name = "status", nullable = false)
        private String status;

    }
