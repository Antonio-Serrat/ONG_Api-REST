package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.request.ActivityCreationDto;
import com.alkemy.ong.dto.response.ActivityResponseDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivitiesRepository;
import com.alkemy.ong.service.Interface.IActivitiesService;
import com.alkemy.ong.service.Interface.IFileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class ActivitiesServiceImpl implements IActivitiesService {

    private final ActivitiesRepository activitiesRepository;
    private final IFileStore fileStore;
    private final ProjectionFactory projectionFactory;
    private final MessageSource messageSource;

    @Autowired
    public ActivitiesServiceImpl(ActivitiesRepository activitiesRepository, IFileStore fileStore, ProjectionFactory projectionFactory, MessageSource messageSource) {
        this.activitiesRepository = activitiesRepository;
        this.fileStore = fileStore;
        this.projectionFactory = projectionFactory;
        this.messageSource = messageSource;
    }


    @Override
    public ActivityResponseDto createActivity(ActivityCreationDto activityCreationDto) {

        Activity activity = Activity.builder()
                .name(activityCreationDto.getName())
                .content(activityCreationDto.getContent())
                .created(new Date())
                .build();
        Activity activityCreated = activitiesRepository.save(activity);
        
        if(!activityCreationDto.getImage().isEmpty())
        	activityCreated.setImage(fileStore.save(activityCreated, activityCreationDto.getImage()));
        
        return projectionFactory.createProjection(ActivityResponseDto.class, activitiesRepository.save(activityCreated));
    }

    @Override
    public ActivityResponseDto updateActivity(Long id, ActivityCreationDto dto) {
        Activity activity = getActivityById(id);

        if(!dto.getName().isBlank())
        	activity.setName(dto.getName());
        if(!dto.getContent().isBlank())
        	activity.setContent(dto.getContent());
        
        if(!dto.getImage().isEmpty())
        	activity.setImage(fileStore.save(activity, dto.getImage()));
        
        activity.setEdited(new Date());
        return projectionFactory.createProjection(ActivityResponseDto.class, activitiesRepository.save(activity));
    }

    @Override
    public List<ActivityResponseDto> getAllActivities() {
        return activitiesRepository.findAllProjectedBy();
    }

    @Override
    public void deleteActivity(Long id) {
        Activity activity = getActivityById(id);
        activity.setDeletedAt(new Date());
        fileStore.deleteFilesFromS3Bucket(activity);
        activitiesRepository.save(activity);
        activitiesRepository.delete(activity);
    }

    @Override
    public Activity getActivityById(Long id) {
        return activitiesRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        messageSource.getMessage("activity.error.not.found", null, Locale.getDefault())
                )
        );
    }


}
