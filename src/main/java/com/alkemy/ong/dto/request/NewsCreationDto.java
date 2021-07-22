package com.alkemy.ong.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter
public class NewsCreationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{news.error.blank.name}")
    private String name;

    @NotBlank(message = "{news.error.blank.content}")
    private String content;

    @NotNull(message = "{news.error.null.image}")
    private MultipartFile image;

    @NotNull(message = "{news.error.null.category}")
    private Long category;

}
