package com.alkemy.ong.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@Entity
@Table(name ="members")
@DynamicUpdate
@SQLDelete(sql = "UPDATE Member SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@Getter @Setter
@NoArgsConstructor
@ApiModel(description = "Detalles sobre Miembros")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificación única de la entidad")
    private Long id;

    @NotBlank(message = "{member.error.empty.name}")
    @ApiModelProperty(notes = "Nombre del Miembro")
    private String name;

    @ApiModelProperty(notes = "Link de Facebook")
    private String facebookUrl;
    @ApiModelProperty(notes = "Link de Instagram")
    private String instagramUrl;
    @ApiModelProperty(notes = "Link de Linkedin")
    private String linkedinUrl;
    
    @ApiModelProperty(notes = "Link de la foto del Miembro")
    private String image;
    
    @ApiModelProperty(notes = "Contenido o Descripción del Miembro")
    private String description;

    @Column(name = "create_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "edit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Member(String name, String facebookUrl, String instagramUrl, String linkedinUrl, String description) {
        this.name = name;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.linkedinUrl = linkedinUrl;
        this.description = description;
        this.created = new Date();
    }
}
