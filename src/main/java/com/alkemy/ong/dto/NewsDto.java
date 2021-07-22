package com.alkemy.ong.dto;

import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Detalles sobre novedad")
public class NewsDto  implements Serializable {

    private static final long serialVersionUID = 6522896498689132123L;

    @ApiModelProperty(notes = "Identificación única de la entidad")
    private Long id;
    @ApiModelProperty(notes = "Nombre de la novedad")
    private String name;
    @ApiModelProperty(notes = "Contenido o descripción de la novedad")
    private String content;
    @ApiModelProperty(notes = "Imagen de la novedad")
    private String image;
    @ApiModelProperty(notes = "Categoria de la novedad")
    private Category category;
    @ApiModelProperty(notes = "Lista de comentarios de la novedad")
    private List<Comment> comments = new ArrayList<>();
}
