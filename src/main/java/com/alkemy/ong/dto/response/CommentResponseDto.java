package com.alkemy.ong.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface CommentResponseDto {

    Long getId();
    String getBody();
    Date getCreated();
    User getUser();

    interface User {
        Long getId();
        String getFirstName();
        String getLastName();
        String getEmail();
        String getPhoto();
    }

}
