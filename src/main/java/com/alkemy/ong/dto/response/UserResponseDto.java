package com.alkemy.ong.dto.response;

import com.alkemy.ong.Enum.ERole;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface UserResponseDto {

    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhoto();
    Date getCreated();
    Date getEdited();
    Boolean getDeleted();
    Set<Role> getRoles();

    interface Role {
        Long getId();
        String getName();
        ERole getRoleName();
    }
}
