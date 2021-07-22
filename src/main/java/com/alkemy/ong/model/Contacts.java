package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "contacts")
@SQLDelete(sql = "UPDATE contacts SET deleted=true WHERE id = ?")
@Where(clause = "deleted_at = false")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El campo Name no puede estar vacío")
    @Column(name = "name", length = 80, nullable = false)
    private String name;

    @NotBlank(message = "El campo Phone no puede estar vacío")
    @Column(name = "phone", length = 50, nullable = false)
    private String phone;

    @Email
    @NotBlank(message = "El campo Email no puede estar vacío")
    @Column(name = "email", length = 80)
    private String email;

    @Column(name = "message")
    private String message;

    @Column(name = "deletedAt")
    private Boolean deleted = false;
}
