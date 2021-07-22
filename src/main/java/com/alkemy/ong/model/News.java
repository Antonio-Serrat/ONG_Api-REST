package com.alkemy.ong.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "news")
@SQLDelete(sql = "UPDATE news SET deleted = true WHERE id=?")
@FilterDef(name = "deletedNewsFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
@ApiModel(description = "Detalles sobre novedad")
@Filter(name = "deletedNewsFilter", condition = "deleted = :isDeleted")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificación única de la entidad")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Completar el campo nombre")
    @Size(min = 2, max = 20, message = "El nombre debe contener entre 2 y 20 carácteres")
    @ApiModelProperty(notes = "Nombre de la novedad")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Completar el campo contenido")
    @ApiModelProperty(notes = "Contenido o descripción de la novedad")
    private String content;

    @Column(nullable = false)
    @NotBlank(message = "Completar el campo imagen")
    @ApiModelProperty(notes = "Imagen de la novedad")
    private String image;

    @OneToOne
    @JoinColumn(name="category")
    @ApiModelProperty(notes = "Categoria de la novedad")
    private Category category;

    private boolean deleted = Boolean.FALSE;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date_time", nullable = false, updatable = false)
    private Date created = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "news")
    @ApiModelProperty(notes = "Lista de comentarios de la novedad")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public News(String name, String content, Category category) {
        super();
        this.name = name;
        this.content = content;
        this.category = category;
        this.created = new Date();
    }
}
