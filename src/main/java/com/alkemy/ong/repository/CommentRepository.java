package com.alkemy.ong.repository;

import com.alkemy.ong.dto.response.CommentResponseDto;
import com.alkemy.ong.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);

    List<CommentResponseDto> findByNewsOrderByCreatedDesc(News news);

    @Query(value = "SELECT body FROM comments ORDER BY created_date DESC", countQuery = "SELECT COUNT(*) FROM comments", nativeQuery = true)
    List<CommentResponseDto>  findAllByOrderCreatedDesc();

}


