package com.tolmatchev.newsintelligence.service;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.tolmatchev.newsintelligence.config.RestClientConfig;
import com.tolmatchev.newsintelligence.dto.TassChannelDto;
import com.tolmatchev.newsintelligence.dto.TassItemDto;
import com.tolmatchev.newsintelligence.dto.TassRssResponse;
import com.tolmatchev.newsintelligence.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {RssClient.class, RestClientConfig.class})
class RssClientTest {

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance().build();

    @MockitoBean
    private NewsRepository newsRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("rss.url", () -> wireMock.baseUrl() + "/rss/v2.xml");
    }

    @Autowired
    private RssClient rssClient;

    @Test
    void shouldFetchAndParseRssFeed() throws Exception {
        String xml = new ClassPathResource("rss/tass-rss.xml")
                .getContentAsString(StandardCharsets.UTF_8);

        wireMock.stubFor(get(urlEqualTo("/rss/v2.xml"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/rss+xml")
                        .withBody(xml)));

        assertThat(rssClient.fetchTassRss()).isEqualTo(expected());
    }

    private static TassRssResponse expected() {
        return new TassRssResponse(new TassChannelDto(List.of(
                new TassItemDto(
                        "\"Галатасарай\" разгромил \"Эрзурумспор\" в матче чемпионата Турции по футболу",
                        "https://tass.ru/sport/28033733",
                        "https://tass.ru/sport/28033733",
                        OffsetDateTime.parse("2026-08-21T20:23:19Z"),
                        "Встреча завершилась со счетом 4:0 в пользу действующих чемпионов страны",
                        List.of("Спорт", "Футбол", "Мировой футбол")),
                new TassItemDto(
                        "Зеленский назначил судей антикоррупционного суда ради финансирования от ЕС",
                        "https://tass.ru/mezhdunarodnaya-panorama/28033731",
                        "https://tass.ru/mezhdunarodnaya-panorama/28033731",
                        OffsetDateTime.parse("2026-08-21T20:19:43Z"),
                        "Это является одним из необходимых условий для получения Киевом около €300 млн",
                        List.of("В мире")),
                new TassItemDto(
                        "TikTok заплатит США $400 млн после достижения соглашения в суде",
                        "https://tass.ru/ekonomika/28033725",
                        "https://tass.ru/ekonomika/28033725",
                        OffsetDateTime.parse("2026-08-21T20:14:51Z"),
                        "Речь идет о процессе от 2014 года о нарушении закона о конфиденциальности детей в интернете",
                        List.of("Экономика и бизнес")),
                new TassItemDto(
                        "В аэропортах Сочи и Геленджика ввели ограничения",
                        "https://tass.ru/obschestvo/28033727",
                        "https://tass.ru/obschestvo/28033727",
                        OffsetDateTime.parse("2026-08-21T20:14:28Z"),
                        "Они необходимы для обеспечения безопасности полетов",
                        List.of("Общество"))
        )));
    }
}
