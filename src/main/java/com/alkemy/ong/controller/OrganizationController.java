package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SocialNetworkDto;
import com.alkemy.ong.dto.request.OrganizationCreationDto;
import com.alkemy.ong.dto.response.OrganizationResponseDto;
import com.alkemy.ong.service.Interface.IOrganization;
import com.amazonaws.services.workmail.model.OrganizationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final IOrganization organizationService;
    private final ProjectionFactory projectionFactory;

    @Autowired
    public OrganizationController(IOrganization organizationService, ProjectionFactory projectionFactory) {
        this.organizationService = organizationService;
        this.projectionFactory = projectionFactory;
    }


    @GetMapping(path = "/public")
    public ResponseEntity<List<OrganizationResponseDto>> getOrganizationData(){
        return ResponseEntity.status(HttpStatus.OK).body(organizationService.getAll());
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getOrganizationById(@PathVariable Long id) {
    	try {
    		return ResponseEntity.status(HttpStatus.OK)
                    .body(projectionFactory.createProjection(OrganizationResponseDto.class, organizationService.getById(id)));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    	}
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> updateOrganization(@Valid @ModelAttribute(name = "organizationCreationDto") OrganizationCreationDto organizationCreationDto, @PathVariable Long id) {
        try {
        	return ResponseEntity.status(HttpStatus.OK).body(organizationService.updateOrg(id, organizationCreationDto));
        } catch (Exception e){
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PostMapping("/newContact/{id}")
    public ResponseEntity<Object> newContactOrganization(@RequestBody SocialNetworkDto contact, @PathVariable Long id){
    	try {
    		return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.newContact(id, contact));
    	}catch(OrganizationNotFoundException e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    	}
    }
    
    @PostMapping
    public ResponseEntity<Object> newOrganization(@Valid @ModelAttribute(name = "organizationCreationDto") OrganizationCreationDto organizationCreationDto){
    	try {
    	    return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.newOrg(organizationCreationDto));
        } catch (Exception e) {
    	    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(organizationService.deleteOrganization(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    

}

