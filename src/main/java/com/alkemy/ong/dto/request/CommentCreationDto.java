package com.alkemy.ong.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class CommentCreationDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String body;

	private Long news;
}
