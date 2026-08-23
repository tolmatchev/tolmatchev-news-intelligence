package com.tolmatchev.newsintelligence.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemDto(

        @JacksonXmlProperty(localName = "title")
        String title,

        @JacksonXmlProperty(localName = "link")
        String link,

        @JacksonXmlProperty(localName = "guid")
        String guid,

        @JacksonXmlProperty(localName = "pubDate")
        String pubDate,

        @JacksonXmlProperty(localName = "description")
        String description,

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "category")
        List<String> categories
) {}
