package com.alkemy.ong.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface TestimonialsResponseDto {

    Long getId();
    String getName();
    String getImage();
    String getContent();
    Date getCreated();
    Date getEdited();
    Boolean getDeleted();

}
