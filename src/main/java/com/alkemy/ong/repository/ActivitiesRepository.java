package com.alkemy.ong.repository;

import com.alkemy.ong.dto.response.ActivityResponseDto;
import com.alkemy.ong.model.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivitiesRepository extends CrudRepository<Activity, Long> {

    Optional<Activity> findById(Long id);

    List<ActivityResponseDto> findAllProjectedBy();

}
