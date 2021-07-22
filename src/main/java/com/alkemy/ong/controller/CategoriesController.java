package com.alkemy.ong.controller;


import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.alkemy.ong.dto.response.CategoryResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.alkemy.ong.dto.request.CategoryCreationDto;
import com.alkemy.ong.service.Interface.ICategoriesService;
import com.amazonaws.services.alexaforbusiness.model.NotFoundException;



import org.springframework.web.bind.annotation.DeleteMapping;
@Api(value = "Categorias controller")
@RestController
@RequestMapping("/categories")
public class CategoriesController {

	private final ICategoriesService iCategory;
	private final MessageSource message;
	@Autowired
	public CategoriesController(ICategoriesService iCategory, MessageSource message) {
		this.iCategory = iCategory;
		this.message = message;
	}

	@ApiOperation("Crear categoria")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@PostMapping
	public ResponseEntity<?> post(@Valid @ModelAttribute(name = "categoryCreationDto") CategoryCreationDto categoryCreationDto) throws EntityNotFoundException{
		try{
			return new ResponseEntity<>(iCategory.createCategory(categoryCreationDto) ,HttpStatus.CREATED);
		}catch (EntityNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation("Actualizar categoria")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @ModelAttribute(name = "categoryCreationDto") CategoryCreationDto categoryCreationDto) {
		try {
			return new ResponseEntity<>(iCategory.updateCategoryById(id, categoryCreationDto), HttpStatus.OK);
		}catch(EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation("Buscar por id a categoria")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@GetMapping(path="/{id}")
	public ResponseEntity<?> shearch(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(iCategory.findById(id), HttpStatus.OK);
		} catch(EntityNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}


	@ApiOperation("Buscar y paginar por el nombre  todas las categorias")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@GetMapping
	public ResponseEntity<?> getAllPageable(@PageableDefault (size = 10, page = 0) Pageable pagebale, 
			@RequestParam(value = "page", defaultValue = "0") int page){
	try {
		Page<CategoryResponseDto> result = iCategory.findAllWithNameInPage(pagebale);	 		
		if(page >= result.getTotalPages() | page < 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(message.getMessage("pagination.error.notFound", null, Locale.getDefault()));

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}catch(NotFoundException e) {
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}

	@ApiOperation("Eliminar por id a categoria")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@DeleteMapping(path = "/{id}")
        public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iCategory.deleteById(id));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
