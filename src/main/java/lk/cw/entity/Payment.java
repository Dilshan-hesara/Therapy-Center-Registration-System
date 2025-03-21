package lk.cw.entity;


import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Payments")

public class Payment implements SuperEntity  {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "payment_id")
        private int paymentId;

        @ManyToOne
        @JoinColumn(name = "patient_id", nullable = false)
        private Patient patient;

        @ManyToOne
        @JoinColumn(name = "program_id", nullable = false)
        private TherapyProgram therapyProgram;

        @Column(name = "amount", nullable = false)
        private double amount;

        @Column(name = "payment_date", nullable = false)
        private Date paymentDate;

        @Column(name = "status", nullable = false)
        private String status;

    }
