package com.tolmatchev.newsintelligence.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.time.OffsetDateTime;
import java.util.List;

/** Data transfer object for a TASS RSS item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TassItemDto(
    @JacksonXmlProperty(localName = "title") String title,
    @JacksonXmlProperty(localName = "link") String link,
    @JacksonXmlProperty(localName = "guid") String guid,
    @JacksonXmlProperty(localName = "pubDate")
        @JsonFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss Z", locale = "en")
        OffsetDateTime pubDate,
    @JacksonXmlProperty(localName = "description") String description,
    @JacksonXmlElementWrapper(useWrapping = false) @JacksonXmlProperty(localName = "category")
        List<String> categories) {}
