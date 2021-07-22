package com.alkemy.ong.service.Interface;


import com.alkemy.ong.dto.request.TestimonialsCreationDto;
import com.alkemy.ong.dto.response.TestimonialsResponseDto;
import com.alkemy.ong.model.Testimonials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITestimonials {

    Testimonials getTestimonialsById(Long id);

    String deleteById(Long id);

    TestimonialsResponseDto createTestimonials(TestimonialsCreationDto testimonialsCreationDto);

    TestimonialsResponseDto updateTestimonials(Long id, TestimonialsCreationDto testimonialsCreationDto);

    Page<Testimonials> showAllTestimonials(Pageable pageable);
}
