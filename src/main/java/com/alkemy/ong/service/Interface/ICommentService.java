package com.alkemy.ong.service.Interface;


import java.util.List;


import com.alkemy.ong.dto.request.CommentCreationDto;
import com.alkemy.ong.dto.response.CommentResponseDto;

public interface ICommentService {
    List<CommentResponseDto> commentsOrderedByDate();

    CommentResponseDto createComment(String email, CommentCreationDto dto);
}

