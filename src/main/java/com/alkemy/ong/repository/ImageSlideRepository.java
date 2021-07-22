package com.alkemy.ong.repository;

import com.alkemy.ong.dto.response.ImageSlideResponseDto;
import com.alkemy.ong.model.ImageSlide;
import com.alkemy.ong.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


import java.util.Optional;

@Repository
public interface ImageSlideRepository extends JpaRepository<ImageSlide, Long> {


    List<ImageSlideResponseDto> findAllByOrganizationOrderByOrdered(Organization organization);

    Optional<ImageSlide> findById(Long id);

    List<ImageSlideResponseDto> findAllProjectedBy();


}
