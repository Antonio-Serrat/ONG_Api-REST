package com.alkemy.ong.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@SQLDelete(sql = " UPDATE image_slide SET deleted = TRUE WHERE id = ?")
@Where(clause = "deleted = false")
@Getter @Setter
@NoArgsConstructor
public class ImageSlide implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    private String text;

    @NotNull(message = "Es necesario un valor entero para ordenar las imágenes")
    private Long ordered;


    private Date createdAt;
    private boolean deleted = false;


    @NotNull(message = "Es necesaria una Id de organización")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public ImageSlide(String text, Long ordered, Organization organization) {
        this.text = text;
        this.ordered = ordered;
        this.organization = organization;
        this.createdAt = new Date();
    }

}
