package com.tolmatchev.newsintelligence.repository;

import com.tolmatchev.newsintelligence.entity.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("""
    select n.link
    from News n
    where n.site = :site
    order by n.id desc
    """)
    List<String> findLatestLinksBySite(
            @Param("site") String site,
            Pageable pageable
    );

    @Query("""
        select n.link
        from News n
        where n.link in :links
    """)
    Set<String> findExistingLinks(@Param("links") Collection<String> links);
}
