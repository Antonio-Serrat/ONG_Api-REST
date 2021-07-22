package com.alkemy.ong.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter @Setter
public class TestimonialsCreationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{testimonials.error.empty.name}")
    private String name;

    private MultipartFile image;

    private String content;

}
