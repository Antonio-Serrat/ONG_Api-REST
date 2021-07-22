package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.request.ImageSlideCreationDto;
import com.alkemy.ong.dto.response.ImageSlideResponseDto;
import com.alkemy.ong.model.ImageSlide;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.ImageSlideRepository;
import com.alkemy.ong.service.Interface.IFileStore;
import com.alkemy.ong.service.Interface.IImgSlideService;
import com.alkemy.ong.service.Interface.IOrganization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;

@Service
public class ImgSlideServiceImpl implements IImgSlideService {

    private final ImageSlideRepository imageRepo;
	private final MessageSource messageSource;
	private final ProjectionFactory projectionFactory;
	private final IOrganization organizationService;
	private final IFileStore fileStore;


	@Autowired
    public ImgSlideServiceImpl(ImageSlideRepository imageRepo, MessageSource messageSource, ProjectionFactory projectionFactory, IOrganization organizationService, IFileStore fileStore) {
        this.imageRepo = imageRepo;
        this.messageSource = messageSource;
        this.projectionFactory = projectionFactory;
        this.organizationService = organizationService;
        this.fileStore = fileStore;
    }


    @Override
    public ImageSlideResponseDto createSlide(ImageSlideCreationDto imageSlideCreationDto) {

        Organization organization = organizationService.getById(imageSlideCreationDto.getOrganizationId());

        ImageSlide imageSlideEntity = new ImageSlide(
                imageSlideCreationDto.getText(),
                imageSlideCreationDto.getOrdered(),
                organization
        );

        ImageSlide imageSlideCreated = imageRepo.save(imageSlideEntity);
        imageSlideCreated.setImageUrl(fileStore.save(imageSlideCreated, imageSlideCreationDto.getImage()));
        return projectionFactory.createProjection(ImageSlideResponseDto.class, imageRepo.save(imageSlideCreated));
	}


    @Override
    public List<ImageSlideResponseDto> getAll() {
        return imageRepo.findAllProjectedBy();
    }

    @Override
    public ImageSlideResponseDto updateImage(Long id, ImageSlideCreationDto image) {
        ImageSlide imageSlide = getImageSlideById(id);
        if(image.getText()!=null)
            imageSlide.setText(image.getText());
        if(image.getOrdered()!=null)
            imageSlide.setOrdered(image.getOrdered());
        if(image.getOrganizationId() != null) {
            Organization organization = organizationService.getById(image.getOrganizationId());
            imageSlide.setOrganization(organization);
        }
        if(image.getImage() != null)
            imageSlide.setImageUrl(fileStore.save(imageSlide, image.getImage()));

        return projectionFactory.createProjection(ImageSlideResponseDto.class, imageRepo.save(imageSlide));
    }

    @Override
    public String deleteImage(Long id){
        ImageSlide imageSlide = getImageSlideById(id);
        imageRepo.delete(imageSlide);
        fileStore.deleteFilesFromS3Bucket(imageSlide);
        return messageSource.getMessage("slide.delete.successful", null, Locale.getDefault());
    }

    @Override
    public List<ImageSlideResponseDto> getAllSlidesByOrganization(Long organizationId) {
        Organization organization = organizationService.getById(organizationId);
        return imageRepo.findAllByOrganizationOrderByOrdered(organization);
    }

    @Override
    public ImageSlide getImageSlideById(Long id) {
        return imageRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                messageSource.getMessage("slide.error.not.found",null, Locale.getDefault())
        ));
    }

}
