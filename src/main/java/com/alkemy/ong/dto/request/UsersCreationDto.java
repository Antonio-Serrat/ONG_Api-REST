package com.alkemy.ong.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.alkemy.ong.model.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter

public class UsersCreationDto implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "{user.error.blank.firstName}")
	private String firstName;


	@NotBlank(message = "{user.error.blank.lastName}")
	private String lastName;

	@NotBlank(message = "{user.error.blank.email}")
	@Email(message = "{user.error.invalid.email}")
	private String email;

	@NotBlank(message = "{user.error.blank.password}")
	@Size(min = 8 , message = "{user.error.min.size.password}")
	private String password;

	private MultipartFile photo;

}
