package lk.cw.entity;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Therapists")
public class Therapist  implements SuperEntity {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "therapist_id")
        private int therapistId;

        private String name;

        private String email;


        private String phone;


        private String specialization;


        private String availability;


    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL)
    private Set<TherapySession> therapySessions;

    }
