package com.alkemy.ong.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter @Setter
public class ActivityCreationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{activity.error.blank.name}")
    private String name;

    @NotBlank(message = "{activity.error.blank.content}")
    private String content;

    @NotNull(message = "{activity.error.null.image}")
    private MultipartFile image;

}
