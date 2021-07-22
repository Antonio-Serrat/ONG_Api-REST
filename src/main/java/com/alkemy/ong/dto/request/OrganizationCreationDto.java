package com.alkemy.ong.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter
public class OrganizationCreationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{organization.error.empty.name}")
    private String name;

    @NotNull(message = "{organization.error.null.image}")
    private MultipartFile image;

    private Integer phone;
    private String address;

    @NotBlank(message = "{organization.error.empty.email}")
    @Email(message = "{organization.error.invalid.email}")
    private String email;

    @NotBlank(message = "{organization.error.empty.welcomeText}")
    private String welcomeText;

    private String aboutUsText;
    

}
