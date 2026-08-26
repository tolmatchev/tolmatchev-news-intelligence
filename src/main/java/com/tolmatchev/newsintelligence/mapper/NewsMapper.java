package com.tolmatchev.newsintelligence.mapper;

import com.tolmatchev.newsintelligence.dto.TassItemDto;
import com.tolmatchev.newsintelligence.dto.TassRssResponse;
import com.tolmatchev.newsintelligence.entity.News;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

  private News toEntity(TassItemDto dto) {
    if (dto == null) {
      return null;
    }

    return News.builder()
        .title(dto.title())
        .link(dto.link())
        .site("tass.ru")
        .publicationDate(dto.pubDate() != null ? dto.pubDate().toLocalDateTime() : null)
        .category(dto.categories() != null ? dto.categories().toString() : null)
        .build();
  }

  public List<News> toNews(TassRssResponse data, Set<String> existingLinks) {
    return data.channel().items().stream()
        .filter(x -> x.link() != null && !x.link().isBlank() && !existingLinks.contains(x.link()))
        .map(this::toEntity)
        .toList();
  }
}
