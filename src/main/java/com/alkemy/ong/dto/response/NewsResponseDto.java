package com.alkemy.ong.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface NewsResponseDto {
    Long getId();
    String getName();
    String getContent();
    String getImage();
    boolean getDeleted();
    Date getCreated();
    Date getEdited();
    Categories getCategory();
    List<Comment> getComments();

    interface Categories {
        Long getId();
        String getName();
        String getImage();
    }

    interface Comment {
        Long getId();
        String getBody();
        User getUser();

        interface User {
            Long getId();
            String getFirstName();
            String getLastName();
            String getEmail();
            String getPhoto();
        }
    }

}
