package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.request.OrganizationCreationDto;
import com.alkemy.ong.dto.SocialNetworkDto;
import com.alkemy.ong.dto.response.OrganizationResponseDto;
import com.alkemy.ong.model.Organization;

import java.util.List;

public interface IOrganization {

    List<OrganizationResponseDto> getAll();
    
    Organization getById(Long id);

    String deleteOrganization(Long id);

    OrganizationResponseDto updateOrg(Long id, OrganizationCreationDto org);

	SocialNetworkDto newContact(Long id, SocialNetworkDto contact);

    OrganizationResponseDto newOrg(OrganizationCreationDto org);

}
