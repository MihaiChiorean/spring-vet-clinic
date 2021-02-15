package ro.fasttrackit.vetclinic.model.entity;

import javax.persistence.*;

@Entity(name = "consultations")
public class ConsultationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
