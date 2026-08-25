package com.tolmatchev.newsintelligence.mapper;

import com.tolmatchev.newsintelligence.dto.ItemDto;
import com.tolmatchev.newsintelligence.dto.RssResponse;
import com.tolmatchev.newsintelligence.entity.News;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class NewsMapper {

    private News toEntity(ItemDto dto) {
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

    public List<News> toNews(RssResponse data, Set<String> existingLinks) {
        return data.channel().items().stream()
                .filter(x -> x.link() != null && !x.link().isBlank() && !existingLinks.contains(x.link()))
                .map(this::toEntity)
                .toList();
    }
}
