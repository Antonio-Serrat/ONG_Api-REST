package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.request.ImageSlideCreationDto;
import com.alkemy.ong.dto.response.ImageSlideResponseDto;
import com.alkemy.ong.model.ImageSlide;

import java.util.List;

public interface IImgSlideService {

    ImageSlideResponseDto createSlide(ImageSlideCreationDto imageSlideCreationDto);
    List<ImageSlideResponseDto> getAll();

    ImageSlideResponseDto updateImage(Long id, ImageSlideCreationDto image);
    String deleteImage(Long id);
    List<ImageSlideResponseDto> getAllSlidesByOrganization(Long organizationId);

    ImageSlide getImageSlideById(Long id);
}
