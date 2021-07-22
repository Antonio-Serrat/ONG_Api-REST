package com.alkemy.ong.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter @Setter
public class MemberCreationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{member.error.empty.name}")
    private String name;

    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private MultipartFile image;
    private String description;



}
