package com.alkemy.ong.controller;

import com.alkemy.ong.dto.request.ActivityCreationDto;
import com.alkemy.ong.dto.response.ActivityResponseDto;
import com.alkemy.ong.service.Interface.IActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/activities")
public class ActivitiesController {

    private final IActivitiesService activitiesService;
    private final ProjectionFactory projectionFactory;
    private final MessageSource messageSource;

    @Autowired
    public ActivitiesController(IActivitiesService activitiesService, ProjectionFactory projectionFactory, MessageSource messageSource) {
        this.activitiesService = activitiesService;
        this.projectionFactory = projectionFactory;
        this.messageSource = messageSource;
    }


    @GetMapping
    public ResponseEntity<List<ActivityResponseDto>> getAllActivities() {
        return ResponseEntity.status(HttpStatus.OK).body(activitiesService.getAllActivities());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getActivityById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(projectionFactory.createProjection(ActivityResponseDto.class, activitiesService.getActivityById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createActivity(@Valid @ModelAttribute(name = "activityCreationDto") ActivityCreationDto activityCreationDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(activitiesService.createActivity(activityCreationDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateActivity(@PathVariable Long id, @Valid @ModelAttribute(name = "activityCreationDto") ActivityCreationDto activityCreationDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(activitiesService.updateActivity(id, activityCreationDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteActivityById(@PathVariable Long id) {
        try {
            activitiesService.deleteActivity(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(messageSource.getMessage("activity.delete.successful", null, Locale.getDefault()));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
