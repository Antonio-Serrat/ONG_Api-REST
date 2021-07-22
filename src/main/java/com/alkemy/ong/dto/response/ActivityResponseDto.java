package com.alkemy.ong.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface ActivityResponseDto {

    Long getId();
    String getName();
    String getContent();
    String getImage();
    Date getCreated();
    Date getEdited();
    Date getDeletedAt();

}
