package com.alkemy.ong.service.impl;
import com.alkemy.ong.dto.request.TestimonialsCreationDto;
import com.alkemy.ong.dto.response.TestimonialsResponseDto;
import com.alkemy.ong.model.Testimonials;
import com.alkemy.ong.repository.TestimonialsRepository;
import com.alkemy.ong.service.Interface.IFileStore;
import com.alkemy.ong.service.Interface.ITestimonials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;

@Service
public class TestimonialsServiceImpl implements ITestimonials {

    @Autowired
    private final TestimonialsRepository testimonialsRepository;
    private final ProjectionFactory projectionFactory;
    private final IFileStore fileStore;
    private final MessageSource messageSource;

    @Autowired
    public TestimonialsServiceImpl(TestimonialsRepository testimonialsRepository, ProjectionFactory projectionFactory, IFileStore fileStore, MessageSource messageSource) {
        this.testimonialsRepository = testimonialsRepository;
        this.projectionFactory = projectionFactory;
        this.fileStore = fileStore;
        this.messageSource = messageSource;
    }



    @Override
    public Testimonials getTestimonialsById(Long id) {
        return testimonialsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        messageSource.getMessage("testimonials.error.not.found", null, Locale.getDefault())
                )
        );
    }


    @Override
    public String deleteById(Long id) {
        Testimonials testimonials = getTestimonialsById(id);
        fileStore.deleteFilesFromS3Bucket(testimonials);
        testimonialsRepository.delete(testimonials);
        return messageSource.getMessage("testimonials.delete.successful",null, Locale.getDefault());
    }



    @Override
    public TestimonialsResponseDto createTestimonials(TestimonialsCreationDto testimonialsCreationDto) {

        Testimonials testimonials = new Testimonials(
                testimonialsCreationDto.getName(),
                testimonialsCreationDto.getContent()
        );
        Testimonials testimonialsCreated = testimonialsRepository.save(testimonials);
        
        if(!testimonialsCreationDto.getImage().isEmpty())
        	testimonialsCreated.setImage(fileStore.save(testimonialsCreated, testimonialsCreationDto.getImage()));

        return projectionFactory.createProjection(TestimonialsResponseDto.class, testimonialsRepository.save(testimonialsCreated));
    }

    @Override
    public TestimonialsResponseDto updateTestimonials(Long id, TestimonialsCreationDto testimonialsCreationDto) {
        Testimonials testimonials = getTestimonialsById(id);
        testimonials.setContent(testimonialsCreationDto.getContent());
        testimonials.setName(testimonialsCreationDto.getName());
        
        if(!testimonialsCreationDto.getImage().isEmpty())
            testimonials.setImage(fileStore.save(testimonials, testimonialsCreationDto.getImage()));

        return projectionFactory.createProjection(TestimonialsResponseDto.class, testimonialsRepository.save(testimonials));
    }

    @Override
    public Page<Testimonials> showAllTestimonials(Pageable pageable) {
        return testimonialsRepository.findAll(pageable);
    }

}
