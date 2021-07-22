package com.alkemy.ong.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import javax.persistence.*;
import javax.validation.constraints.Email;
import org.hibernate.annotations.Where;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE Organizations SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor
@Setter @Getter
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,length = 80)
    @NotNull(message = "El campo name no puede estar vacío")
    private String name;


    private String image;

    private String address;

    private Integer phone;

    @Column(nullable = false ,length = 80)
    @NotNull(message = "El campo email no puede estar vacío")
    @Email(message = "El formato no es válido")
    private String email;


    @NotNull(message = "El campo welcomeText no puede estar vacío")
    @Column(columnDefinition = "TEXT",nullable = false)
    private String welcomeText;

    @Column(columnDefinition = "TEXT")
    private String aboutUsText;

    private boolean deleted = Boolean.FALSE;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;
    

    @OneToMany(cascade = CascadeType.ALL)
    private List<SocialNetwork> contact;

    @Builder
    public Organization(String name, String address, Integer phone, String email, String welcomeText, String aboutUsText) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.welcomeText = welcomeText;
        this.aboutUsText = aboutUsText;
        this.created = new Date();
    }
}
