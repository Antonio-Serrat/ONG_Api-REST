package com.alkemy.ong.repository;

import com.alkemy.ong.model.Testimonials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestimonialsRepository extends JpaRepository<Testimonials, Long> {

    Optional<Testimonials> findById(Long id);

}
