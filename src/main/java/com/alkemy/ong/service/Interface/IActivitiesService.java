package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.request.ActivityCreationDto;
import com.alkemy.ong.dto.response.ActivityResponseDto;
import com.alkemy.ong.model.Activity;

import java.util.List;

public interface IActivitiesService {

    ActivityResponseDto createActivity(ActivityCreationDto activitiesDto);

    ActivityResponseDto updateActivity(Long id, ActivityCreationDto activitiesDto);

    List<ActivityResponseDto> getAllActivities();

    void deleteActivity(Long id);

    Activity getActivityById(Long id);

}
