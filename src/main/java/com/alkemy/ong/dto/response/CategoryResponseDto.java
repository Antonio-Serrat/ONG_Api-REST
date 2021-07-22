package com.alkemy.ong.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface CategoryResponseDto {

    Long getId();
    String getName();
    String getDescription();
    String getImage();
    Date getCreated();
    Date getEdited();
    Boolean getDeleted();

}
