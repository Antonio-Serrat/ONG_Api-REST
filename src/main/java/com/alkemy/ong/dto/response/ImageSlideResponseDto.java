package com.alkemy.ong.dto.response;

import com.alkemy.ong.model.Organization;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface ImageSlideResponseDto {
    Long getId();
    String getImageUrl();
    String getText();
    Long getOrdered();
    Date getCreatedAt();
    boolean getDeleted();
    Organization getOrganization();

    interface Organization {
        Long getId();
        String getName();
        Integer getPhone();
        String getEmail();
        String getAddress();
    }
}
