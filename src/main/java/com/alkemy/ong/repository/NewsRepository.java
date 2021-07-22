package com.alkemy.ong.repository;

import com.alkemy.ong.dto.response.NewsResponseDto;
import com.alkemy.ong.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findById(Long id);

    Page<NewsResponseDto> findAllProjectedBy(Pageable pageable);
}
