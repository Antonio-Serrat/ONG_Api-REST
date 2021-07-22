package com.alkemy.ong.controller;

import com.alkemy.ong.dto.request.ImageSlideCreationDto;
import com.alkemy.ong.dto.response.ImageSlideResponseDto;

import com.alkemy.ong.service.Interface.IImgSlideService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import java.util.Locale;


@RestController
@RequestMapping("/slides")
public class ImageSliderController {

    private final IImgSlideService iImgSlideService;
    private final MessageSource messageSource;
    private final ProjectionFactory projectionFactory;

    @Autowired
    public ImageSliderController(IImgSlideService iImgSlideService, MessageSource messageSource, ProjectionFactory projectionFactory) {
        this.iImgSlideService = iImgSlideService;
        this.messageSource = messageSource;
        this.projectionFactory = projectionFactory;
    }

    @PostMapping
    public ResponseEntity<Object> createImageSlide(@Valid @ModelAttribute(name = "imageSlideCreationDto") ImageSlideCreationDto imageSlideCreationDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(iImgSlideService.createSlide(imageSlideCreationDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSlidesByOrganization(@RequestParam(value = "organization_id") Long organizationId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iImgSlideService.getAllSlidesByOrganization(organizationId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getImageSlideById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(projectionFactory.createProjection(ImageSlideResponseDto.class, iImgSlideService.getImageSlideById(id)), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(messageSource.getMessage("slide.error.do.not.exists", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSlide(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(iImgSlideService.deleteImage(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/all")
	public ResponseEntity<?> getSlides(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iImgSlideService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateImageSlide(@PathVariable Long id, @Valid @ModelAttribute(name = "imageSlideCreationDto") ImageSlideCreationDto imageSlideCreationDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iImgSlideService.updateImage(id, imageSlideCreationDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}





