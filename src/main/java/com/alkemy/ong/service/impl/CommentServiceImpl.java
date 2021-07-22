package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.response.CommentResponseDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.Interface.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import com.alkemy.ong.service.Interface.INewsService;
import org.springframework.data.projection.ProjectionFactory;

import com.alkemy.ong.dto.request.CommentCreationDto;
import com.alkemy.ong.model.News;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UsersRepository;

@Service
public class CommentServiceImpl implements ICommentService{

	private final UsersRepository repoUser;
	private final INewsService newsService;
	private final ProjectionFactory projectionFactory;
	private final CommentRepository repoComment;

	@Autowired
	public CommentServiceImpl(UsersRepository repoUser, INewsService newsService, ProjectionFactory projectionFactory, CommentRepository repoComment) {
		this.repoUser = repoUser;
		this.newsService = newsService;
		this.projectionFactory = projectionFactory;
		this.repoComment = repoComment;
	}

	public List<CommentResponseDto> commentsOrderedByDate() {return (List<CommentResponseDto>) repoComment.findAllByOrderCreatedDesc();}

	@Override
	public CommentResponseDto createComment(String email, CommentCreationDto dto) {
		
		User user = repoUser.findByEmail(email).get();
		News post = newsService.getNewById(dto.getNews());
		
		Comment comment = new Comment(
				user,
				dto.getBody(),
				post
		);

		return projectionFactory.createProjection(CommentResponseDto.class, repoComment.save(comment));
	}

	
}
