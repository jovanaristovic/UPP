package com.ftn.upp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.upp.dto.FormSubmissionDto;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Table
@Entity(name = "USERS")
public class User implements Serializable, UserDetails {

    private  static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private String city;

    @Column
    private  String country;

    @Column
    private String title;

    @Column
    private String email;

    @Column
    private String password;


    @Column
    private String username;

    @Column
    private boolean enabled;

    @Column
    private boolean reviewer;


    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScientificField> scientificFields;

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
    public User(){}

    public User(List<FormSubmissionDto> registrationData){

        List<ScientificField> fields = new ArrayList<>();
        for(FormSubmissionDto dto : registrationData) {

            if (dto.getFieldId().equals("ime")) {
                this.name = dto.getFieldValue();
            } else if (dto.getFieldId().equals("prezime")) {
                this.lastName = dto.getFieldValue();
            } else if (dto.getFieldId().equals("grad")) {
                this.city = dto.getFieldValue();
            } else if (dto.getFieldId().equals("drzava")) {
                this.country = dto.getFieldValue();
            } else if (dto.getFieldId().equals("titula")) {
                if(!dto.getFieldValue().isEmpty()) {
                    this.title = dto.getFieldValue();
                }
            } else if (dto.getFieldId().equals("email")) {
                this.email = dto.getFieldValue();
            } else if (dto.getFieldId().equals("korisnickoIme")) {
                this.username = dto.getFieldValue();
            } else if (dto.getFieldId().equals("jesteRecenzent")) {

                if (dto.getFieldValue() != null) {
                    if (dto.getFieldValue().equals("true")) {
                        this.reviewer = true;
                    } else {
                        this.reviewer = false;
                    }
                } else {
                    this.reviewer = false;
                }
            }
        }

        this.enabled = false;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        Timestamp now = new Timestamp(DateTime.now().getMillis());
        this.setLastPasswordResetDate( now );
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isReviewer() {
        return reviewer;
    }

    public void setReviewer(boolean reviewer) {
        this.reviewer = reviewer;
    }

    public List<ScientificField> getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(List<ScientificField> scientificFields) {
        this.scientificFields = scientificFields;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    @Override
    public boolean isEnabled(){
        return enabled;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }






}
