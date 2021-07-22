package com.alkemy.ong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Table(name ="comments")
@Getter @Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"comments", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @NotBlank(message = "El campo body no puede estar vacío.")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"comments", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", nullable = false, updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="edited_date")
    private Date edited;

    public Comment(User user, String body, News news) {
        this.user = user;
        this.body = body;
        this.news = news;
        this.created = new Date();
    }
}

