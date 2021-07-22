package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.request.NewsCreationDto;
import com.alkemy.ong.dto.response.CommentResponseDto;
import com.alkemy.ong.dto.response.NewsResponseDto;
import com.alkemy.ong.model.News;
import org.springframework.data.domain.Page;

import java.util.List;

public interface INewsService {


    News getNewById(Long id);

    List<News> findAll();

    NewsResponseDto save(NewsCreationDto newsCreationDto);

    void deleteNews(Long id);

    NewsResponseDto updateNews(Long id, NewsCreationDto newsCreationDto);

    Page<NewsResponseDto> getAllNewsPaginated(int page, int limit, String sortBy, String sortDir);

    List<CommentResponseDto> getAllCommentsByPost(Long id);
}
