package com.tolmatchev.newsintelligence.mapper;

import com.tolmatchev.newsintelligence.dto.TassChannelDto;
import com.tolmatchev.newsintelligence.dto.TassItemDto;
import com.tolmatchev.newsintelligence.dto.TassRssResponse;
import com.tolmatchev.newsintelligence.entity.News;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class NewsMapperTest {

    private final NewsMapper newsMapper = new NewsMapper();

    private TassItemDto createItem(String link, OffsetDateTime pubDate, List<String> categories) {
        return new TassItemDto("Title", link, "guid", pubDate, "desc", categories);
    }

    @Test
    void shouldIncludeValidItem() {
        TassItemDto item = createItem("http://test.ru", OffsetDateTime.now(), List.of("news"));
        List<News> result = newsMapper.toNews(new TassRssResponse(new TassChannelDto(List.of(item))), Collections.emptySet());
        assertThat(result).hasSize(1);
    }

    @Test
    void shouldExcludeNullLink() {
        TassItemDto item = createItem(null, OffsetDateTime.now(), List.of("news"));
        List<News> result = newsMapper.toNews(new TassRssResponse(new TassChannelDto(List.of(item))), Collections.emptySet());
        assertThat(result).isEmpty();
    }

    @Test
    void shouldExcludeBlankLink() {
        TassItemDto item = createItem(" ", OffsetDateTime.now(), List.of("news"));
        List<News> result = newsMapper.toNews(new TassRssResponse(new TassChannelDto(List.of(item))), Collections.emptySet());
        assertThat(result).isEmpty();
    }

    @Test
    void shouldExcludeExistingLink() {
        TassItemDto item = createItem("http://test.ru", OffsetDateTime.now(), List.of("news"));
        List<News> result = newsMapper.toNews(new TassRssResponse(new TassChannelDto(List.of(item))), Set.of("http://test.ru"));
        assertThat(result).isEmpty();
    }

    @Test
    void shouldHandleNullDate() {
        TassItemDto item = createItem("http://test.ru", null, List.of("news"));
        var result = newsMapper.toNews(new TassRssResponse(new TassChannelDto(List.of(item))), Collections.emptySet());
        assertThat(result.get(0).getPublicationDate()).isNull();
    }

    @Test
    void shouldHandleNullCategories() {
        TassItemDto item = createItem("http://test.ru", OffsetDateTime.now(), null);
        var result = newsMapper.toNews(new TassRssResponse(new TassChannelDto(List.of(item))), Collections.emptySet());
        assertThat(result.get(0).getCategory()).isNull();
    }
}
