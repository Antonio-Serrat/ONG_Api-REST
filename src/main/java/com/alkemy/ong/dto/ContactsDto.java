package com.alkemy.ong.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ContactsDto {

    @NotBlank(message = "El campo Name no puede estar vacío")
    private String name;

    private String phone;

    @NotBlank(message = "El campo Email no puede estar vacío")
    private String email;

    private String message;
}