package com.alkemy.ong.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class CategoryCreationDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "{categories.error.empty.name}")
	private String name;

	private String description;

	private MultipartFile image;

}
