package com.tolmatchev.newsintelligence.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tolmatchev.newsintelligence.dto.RssResponse;
import com.tolmatchev.newsintelligence.entity.News;
import com.tolmatchev.newsintelligence.mapper.NewsMapper;
import com.tolmatchev.newsintelligence.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RssService {
    private final RestClient restClient;
    private final XmlMapper xmlMapper;
    private final String rssUrl;
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public RssService(RestClient restClient, @Value("${rss.url}") String rssUrl,
                      NewsRepository newsRepository, NewsMapper newsMapper) {
        this.restClient = restClient;
        this.rssUrl = rssUrl;
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.xmlMapper = XmlMapper.builder().addModule(new JavaTimeModule())
                .build();
    }

    @Scheduled(fixedRate = 60000)
    public void scheduledFetchRss() {
        RssResponse data = fetchRss();
        log.info("Received RSS data: {}", data);
        saveNewNews(data);
    }

    public void saveNewNews(RssResponse data) {
        if (!isDataValid(data)) return;
        Set<String> existingLinks = getExistingLinks();
        List<News> savedNews = mapToSavedNews(data, existingLinks);
        newsRepository.saveAll(savedNews);
        log.info("Saved {} new news items", savedNews.size());
    }

    private List<News> mapToSavedNews(RssResponse data, Set<String> existingLinks) {
        return data.channel().items().stream()
                .filter(x -> x.link() != null && !x.link().isBlank() && !existingLinks.contains(x.link()))
                .map(newsMapper::toEntity)
                .toList();
    }

    private Set<String> getExistingLinks() {
        return newsRepository.findTop100ByOrderByIdDesc().stream()
                .map(News::getLink)
                .collect(Collectors.toSet());
    }

    private static boolean isDataValid(RssResponse data) {
        if (data == null || data.channel() == null || data.channel().items() == null) {
            log.info("No RSS items to process");
            return false;
        }
        return true;
    }

    public RssResponse fetchRss() {
        String xml = restClient.get()
                .uri(rssUrl)
                .header("Accept", "application/rss+xml")
                .header("Accept-Encoding", "gzip")
                .exchange((request, response) -> {
                    log.info("Status: {}", response.getStatusCode());
                    log.info("Content-Type: {}", response.getHeaders().getFirst("Content-Type"));

                    return new String(
                            response.getBody().readAllBytes(),
                            StandardCharsets.UTF_8
                    );
                });

        try {
            return xmlMapper.readValue(xml, RssResponse.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to parse TASS RSS", e);
        }

    }
}
