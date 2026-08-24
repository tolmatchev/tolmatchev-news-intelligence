package com.tolmatchev.newsintelligence.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.tolmatchev.newsintelligence.config.RestClientConfig;
import com.tolmatchev.newsintelligence.dto.RssResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {RssService.class, RestClientConfig.class})
class RssServiceIntegrationTest {

    private static WireMockServer wireMockServer;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();
        registry.add("rss.url", () -> wireMockServer.baseUrl() + "/rss/v2.xml");
    }

    @AfterAll
    static void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Autowired
    private RssService rssService;

    @Test
    void shouldFetchAndParseRssFeed() throws Exception {
        String xml = new ClassPathResource("rss/tass-rss.xml")
                .getContentAsString(StandardCharsets.UTF_8);

        wireMockServer.stubFor(get(urlEqualTo("/rss/v2.xml"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/rss+xml")
                        .withBody(xml)));

        RssResponse response = rssService.fetchRss();

        assertThat(response.channel()).isNotNull();
        assertThat(response.channel().items()).hasSize(4);

        var first = response.channel().items().get(0);
        assertThat(first.title()).isEqualTo("\"Галатасарай\" разгромил \"Эрзурумспор\" в матче чемпионата Турции по футболу");
        assertThat(first.link().trim()).isEqualTo("https://tass.ru/sport/28033733");
        assertThat(first.guid().trim()).isEqualTo("https://tass.ru/sport/28033733");
        assertThat(first.description()).isEqualTo("Встреча завершилась со счетом 4:0 в пользу действующих чемпионов страны");
        assertThat(first.pubDate()).isEqualTo(OffsetDateTime.parse("2026-08-21T23:23:19+03:00"));
        assertThat(first.categories()).containsExactly("Спорт", "Футбол", "Мировой футбол");
    }
}
