package com.ftn.upp.model;

import com.ftn.upp.dto.FormSubmissionDto;
import org.hibernate.annotations.CollectionId;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name = "WORK")
public class Work implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column
    private String title;

    @Column
    private String apstrakt;

    @Column
    private String keyWords;

    @Column
    private String pdf;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "work_user",
            joinColumns = @JoinColumn(name = "work_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;

    @ManyToOne
    private ScientificField scientificField;

    public Work(){}

    public Work(List<FormSubmissionDto> workData){

        List<ScientificField> fields = new ArrayList<>();
        String scientificField = "";
        for(FormSubmissionDto dto : workData) {

            if (dto.getFieldId().equals("naslov")) {
                this.title = dto.getFieldValue();
            } else if (dto.getFieldId().equals("apstrakt")) {
                this.apstrakt = dto.getFieldValue();
            } else if (dto.getFieldId().equals("kljucniPojmovi")) {
                this.keyWords = dto.getFieldValue();
            } else if (dto.getFieldId().equals("pdf")) {
                    this.pdf = dto.getFieldValue();

            }
        }

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

    public String getApstrakt() {
        return apstrakt;
    }

    public void setApstrakt(String apstrakt) {
        this.apstrakt = apstrakt;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public ScientificField getScientificField() {
        return scientificField;
    }

    public void setScientificField(ScientificField scientificField) {
        this.scientificField = scientificField;
    }

}
