package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.request.NewsCreationDto;
import com.alkemy.ong.dto.response.CommentResponseDto;
import com.alkemy.ong.dto.response.NewsResponseDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.Interface.ICategoriesService;
import com.alkemy.ong.service.Interface.IFileStore;
import com.alkemy.ong.service.Interface.INewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;
import java.util.Date;


import org.springframework.context.MessageSource;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Locale;


@Service
public class NewsServiceImpl implements INewsService {

    private final NewsRepository newsRepository;
    private final ProjectionFactory projectionFactory;
    private final MessageSource messageSource;
    private final IFileStore fileStore;
    private final ICategoriesService categoriesService;
    private final CommentRepository commentRepository;

    private static final String ASC = "asc";

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, ProjectionFactory projectionFactory, MessageSource messageSource, IFileStore fileStore, ICategoriesService categoriesService, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.projectionFactory = projectionFactory;
        this.messageSource = messageSource;
        this.fileStore = fileStore;
        this.categoriesService = categoriesService;
        this.commentRepository = commentRepository;
    }


    public News getNewById(Long id) {
        return newsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        messageSource.getMessage("news.error.not.found", null, Locale.getDefault())
                )
        );
    }

    @Override
    public List<News> findAll() {
        return null;
    }

    @Override
    public NewsResponseDto save(NewsCreationDto newsCreationDto) {

        Category categoryEntity = categoriesService.findCategoriesById(newsCreationDto.getCategory());

        News newsEntity = News.builder()
                .name(newsCreationDto.getName())
                .content(newsCreationDto.getContent())
                .category(categoryEntity)
                .build();
        News newsCreated = newsRepository.save(newsEntity);
        
        if(!newsCreationDto.getImage().isEmpty())
        	newsCreated.setImage(fileStore.save(newsEntity, newsCreationDto.getImage()));
        
        return projectionFactory.createProjection(NewsResponseDto.class, newsRepository.save(newsCreated));
    }

    @Override
    public void deleteNews(Long id) {
        News newsEntity = getNewById(id);
        newsRepository.delete(newsEntity);
        fileStore.deleteFilesFromS3Bucket(newsEntity);
    }

    @Override
    public NewsResponseDto updateNews(Long id, NewsCreationDto newsCreationDto) {

        News newsUpdated = getNewById(id);
        Category categoryEntity = categoriesService.findCategoriesById(newsCreationDto.getCategory());

        newsUpdated.setCategory(categoryEntity);
        newsUpdated.setContent(newsCreationDto.getContent());
        newsUpdated.setName(newsCreationDto.getName());
        if(!newsCreationDto.getImage().isEmpty()) {
            newsUpdated.setImage(fileStore.save(newsUpdated, newsCreationDto.getImage()));
        }
        newsUpdated.setEdited(new Date());
        return projectionFactory.createProjection(NewsResponseDto.class, newsRepository.save(newsUpdated));
    }

    @Override
    public Page<NewsResponseDto> getAllNewsPaginated(int page, int limit, String sortBy, String sortDir) {
        if (page > 0) {
            page = page - 1;
        }

        Pageable pageable = PageRequest.of(
                page, limit,
                sortDir.equalsIgnoreCase(ASC) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        return newsRepository.findAllProjectedBy(pageable);
    }

    @Override
    public List<CommentResponseDto> getAllCommentsByPost(Long id) {
        News news = getNewById(id);
        if(id == null) throw new IllegalStateException(
                messageSource.getMessage("news.error.object.notFound", null, Locale.getDefault())
        );
        return commentRepository.findByNewsOrderByCreatedDesc(news);
    }


}
