package com.alkemy.ong.repository;

import com.alkemy.ong.dto.response.CategoryResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id);

    List<CategoryResponseDto> findAllProjectedBy();

    @Query(value = "SELECT name FROM categories", nativeQuery = true, countQuery = "SELECT COUNT(*) FROM categories")
    Page<CategoryResponseDto> fetchName(Pageable pagebale);

}
