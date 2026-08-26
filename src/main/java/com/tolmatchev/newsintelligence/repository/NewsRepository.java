package com.tolmatchev.newsintelligence.repository;

import com.tolmatchev.newsintelligence.entity.News;
import java.util.Collection;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

  @Query(
      """
        select n.link
        from News n
        where n.link in :links
    """)
  Set<String> findExistingLinks(@Param("links") Collection<String> links);
}
