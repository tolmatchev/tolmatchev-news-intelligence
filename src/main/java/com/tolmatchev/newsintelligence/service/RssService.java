package com.tolmatchev.newsintelligence.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.tolmatchev.newsintelligence.dto.ChannelDto;

@Service
public class RssService {
    private final RestClient restClient;

    public RssService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public ChannelDto fetchRss() {
        return restClient.get()
                .uri("https://tass.ru/rss/v2.xml")
                .retrieve()
                .body(RssResponse.class)
                .channel();
    }

    private record RssResponse(ChannelDto channel) {}
}
