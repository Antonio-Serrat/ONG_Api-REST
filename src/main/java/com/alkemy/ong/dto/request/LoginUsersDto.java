package com.alkemy.ong.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter @Setter
public class LoginUsersDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "{user.error.blank.email }")
	private String email;

	@NotBlank(message = "{user.error.blank.password}")
	private String password;
}
