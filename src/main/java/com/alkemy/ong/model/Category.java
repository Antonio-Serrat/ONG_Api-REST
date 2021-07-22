package com.alkemy.ong.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deleted=true WHERE id =?")
@Where(clause = "deleted = false")
@ApiModel(description = "Modelo de categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identificación única de categoria")
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "El nombre no puede estar vacío")
	@ApiModelProperty(notes ="Nombre de categoria")
	private String name;

	@ApiModelProperty(notes ="Descripción de categoria")
	private String description;

	@ApiModelProperty(notes ="Imagen de categoria")
	private String image;

	@Column(name = "created_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(notes ="Fecha de creación de categoria")
	private Date created;

	@Column(name = "edited_date")
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(notes ="Fecha de actualización de categoria")
	private Date edited;

	@ApiModelProperty(notes ="Baja de categoria")
	private Boolean deleted = Boolean.FALSE;

	public Category(String name, String description) {
		this.name = name;
		this.description = description;
		this.created = new Date();
	}
}
