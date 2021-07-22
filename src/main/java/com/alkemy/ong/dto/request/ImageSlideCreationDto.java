package com.alkemy.ong.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter @Setter
public class ImageSlideCreationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private MultipartFile image;
    private String text;
    private Long organizationId;
    private Long ordered;

}
