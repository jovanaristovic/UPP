package com.ftn.upp.model;

import com.ftn.upp.dto.FormSubmissionDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name = "JOURNAL")
public class Journal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column
    private String title;

    @Column
    private String ISSN;

    @Column
    private String payment;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "journal_scientific_field",
            joinColumns = @JoinColumn(name = "journal_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "scientificField_id", referencedColumnName = "id"))
    private List<ScientificField> scientificFields;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    @Column
    private boolean isActive;


    public Journal( ){}

    public Journal(List<FormSubmissionDto> journalData){

        List<ScientificField> fields = new ArrayList<>();
        for(FormSubmissionDto dto : journalData) {

            if (dto.getFieldId().equals("naslov")) {
                this.title = dto.getFieldValue();
            } else if (dto.getFieldId().equals("nacinPlacanja")) {
                this.payment = dto.getFieldValue();
            } else if (dto.getFieldId().equals("ISSN")) {
                this.ISSN = (dto.getFieldValue());
            }
        }
        this.scientificFields = new ArrayList<>();
        this.users = new ArrayList<>();
        this.isActive = false;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<ScientificField> getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(List<ScientificField> scientificFields) {
        this.scientificFields = scientificFields;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
