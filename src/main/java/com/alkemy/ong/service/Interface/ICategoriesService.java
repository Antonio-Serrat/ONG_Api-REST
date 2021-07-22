package com.alkemy.ong.service.Interface;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.alkemy.ong.dto.request.CategoryCreationDto;
import com.alkemy.ong.dto.response.CategoryResponseDto;
import com.alkemy.ong.model.Category;


public interface ICategoriesService {

	CategoryResponseDto findById(Long id);

	List<CategoryResponseDto> findAll();

	CategoryResponseDto createCategory(CategoryCreationDto category);

	String deleteById(Long id);

	Category findCategoriesById(Long id);
	
	Page<CategoryResponseDto> findAllWithNameInPage(Pageable pageable);

	CategoryResponseDto updateCategoryById(Long id, CategoryCreationDto dto);

}
