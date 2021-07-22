package com.alkemy.ong.dto.response;

import com.alkemy.ong.model.SocialNetwork;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface OrganizationResponseDto {

    Long getId();
    String getName();
    String getImage();
    String getAddress();
    Integer getPhone();
    String getEmail();
    String getWelcomeText();
    String getAboutUsText();
    Date getCreated();
    Date getEdited();
    Boolean getDeleted();
    List<SocialNetwork> getContact();

    interface SocialNetwork {
        Long getId();
        String getName();
        String getLink();
    }

}
