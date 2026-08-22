package com.tolmatchev.newsintelligence.mapper;

import com.tolmatchev.newsintelligence.dto.ItemDto;
import com.tolmatchev.newsintelligence.entity.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

    public News toEntity(ItemDto dto) {
        if (dto == null) {
            return null;
        }

        return News.builder()
                .title(dto.title())
                .link(dto.link())
                .publicationDate(dto.pubDate() != null ? 
                        dto.pubDate().toLocalDateTime() : null)
                .category(dto.categories() != null ? 
                        dto.categories().toString() : null)
                .build();
    }
}
