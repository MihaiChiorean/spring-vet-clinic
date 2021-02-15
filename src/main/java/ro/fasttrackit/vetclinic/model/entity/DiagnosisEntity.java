package ro.fasttrackit.vetclinic.model.entity;

import javax.persistence.*;

@Entity(name = "diagnosis")
public class DiagnosisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String recommendations;

}
