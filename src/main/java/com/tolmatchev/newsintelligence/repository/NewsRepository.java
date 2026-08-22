package com.tolmatchev.newsintelligence.repository;

import com.tolmatchev.newsintelligence.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsByLink(String link);
}
