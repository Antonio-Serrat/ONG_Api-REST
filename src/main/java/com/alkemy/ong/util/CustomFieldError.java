package com.alkemy.ong.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



//Clase custom para mostrar los errores de los modelos que arroja el javax validation
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomFieldError {

    private String field;

    private String message;

}