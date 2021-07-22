package com.alkemy.ong.model;


import com.alkemy.ong.Enum.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "roles")
@SQLDelete(sql = "UPDATE Roles SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 80)
    @NotEmpty(message = "El campo name no puede estar vacío")
    private String name;

    private String description;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;

    private boolean deleted = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole roleName;


}
