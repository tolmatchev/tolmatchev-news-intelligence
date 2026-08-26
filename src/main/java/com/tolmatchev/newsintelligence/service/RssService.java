package com.tolmatchev.newsintelligence.service;

import com.tolmatchev.newsintelligence.dto.TassItemDto;
import com.tolmatchev.newsintelligence.dto.TassRssResponse;
import com.tolmatchev.newsintelligence.entity.News;
import com.tolmatchev.newsintelligence.mapper.NewsMapper;
import com.tolmatchev.newsintelligence.repository.NewsRepository;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RssService {
  private final RssClient rssClient;
  private final NewsRepository newsRepository;
  private final NewsMapper newsMapper;
  private final ValidationService validationService;

  public RssService(
      RssClient rssClient,
      NewsRepository newsRepository,
      NewsMapper newsMapper,
      ValidationService validationService) {
    this.rssClient = rssClient;
    this.newsRepository = newsRepository;
    this.newsMapper = newsMapper;
    this.validationService = validationService;
  }

  @Scheduled(fixedRate = 60000)
  public void scheduledFetchRss() {
    TassRssResponse tassRss = rssClient.fetchTassRss();
    log.info("Received RSS TASS: {}", tassRss);
    saveNewNews(tassRss);
  }

  public void saveNewNews(TassRssResponse data) {
    if (!validationService.isTassRssValid(data)) return;
    Set<String> existingLinks =
        newsRepository.findExistingLinks(
            data.channel().items().stream().map(TassItemDto::link).toList());
    List<News> savedNews = newsMapper.toNews(data, existingLinks);
    newsRepository.saveAll(savedNews);
    log.info("Saved {} new news items", savedNews.size());
  }
}
