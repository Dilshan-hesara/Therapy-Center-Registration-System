package lk.cw.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Patients")
public class Patient {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "patient_id")
        private int patientId;

        private String name;

        private String email;

        private String phone;

        private String address;

        private String medicalHistory;

        @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
        private Set<TherapySession> therapySessions;

        @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
        private Set<Payment> payments;
}
