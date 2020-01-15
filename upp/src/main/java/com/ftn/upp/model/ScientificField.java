package com.ftn.upp.model;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity(name = "SCIENTIFICFIELDS")
public class ScientificField implements Serializable {
    private  static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column
    private String name;

}
