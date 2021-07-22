package com.alkemy.ong.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface MemberResponseDto {

    Long getId();
    String getName();
    String getDescription();
    String getFacebookUrl();
    String getInstagramUrl();
    String getLinkedinUrl();
    String getImage();
    Date getCreated();
    Date getEdited();
    Boolean getDeleted();

}
