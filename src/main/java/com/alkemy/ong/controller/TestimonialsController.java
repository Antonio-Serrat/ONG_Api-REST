package com.alkemy.ong.controller;

import com.alkemy.ong.dto.request.TestimonialsCreationDto;
import com.alkemy.ong.service.Interface.ITestimonials;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@Api(value = "Testimonial controller")
@RequestMapping("/testimonials")
public class TestimonialsController {

    private final ITestimonials iTestimonials;
    private final MessageSource messageSource;

    @Autowired
    public TestimonialsController(ITestimonials iTestimonials, MessageSource messageSource) {
        this.iTestimonials = iTestimonials;
        this.messageSource = messageSource;
    }

    @PostMapping
    @ApiOperation("Crea un nuevo testimonio")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Operación exitosa"),
            @ApiResponse(code = 400, message = "Solicitud incorrecta")
    })
    public ResponseEntity<?> createTestimonials(@ApiParam(value = "JSON con Testimonial para crear", required = true) @ModelAttribute(name = "testimonialsCreationDto") @Valid TestimonialsCreationDto testimonialsCreationDto) {
        try {
             return ResponseEntity.status(HttpStatus.CREATED).body(iTestimonials.createTestimonials(testimonialsCreationDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping(path = "/{id}")
    @ApiOperation("Actualiza un testimonio")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Operación exitosa"),
            @ApiResponse(code = 404, message = "Testimonio no encontrado")
    })
    public ResponseEntity<?> Update(@ApiParam(value = "El id del testimonio", required = true, example = "1") @ModelAttribute(name = "testimonialsCreationDto") @Valid TestimonialsCreationDto testimonialsCreationDto, @PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iTestimonials.updateTestimonials(id, testimonialsCreationDto));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @DeleteMapping(path = "{id}")
    @ApiOperation("Elimina un testimonio")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Operación exitosa"),
            @ApiResponse(code = 404, message = "Testimonio no encontrado")
    })
    public ResponseEntity<String> deleteTestimonialById(@ApiParam(value = "El id del testimonio", required = true, example = "1") @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iTestimonials.deleteById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @ApiOperation("Buscar y paginar por el nombre  todos los testimonios")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Operación exitosa"),
            @ApiResponse(code = 400, message = "Solicitud incorrecta")
    })

    public  ResponseEntity<?> AllPagination(@PageableDefault(size = 10 , page = 0 ) Pageable pageable ,@RequestParam
            (value = "page" , defaultValue = "0") int page) {

        try {
            Page<?> result = iTestimonials.showAllTestimonials(pageable);
            if (page >= result.getTotalPages()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("pagination.error.notFound",
                        null, Locale.getDefault()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

}


