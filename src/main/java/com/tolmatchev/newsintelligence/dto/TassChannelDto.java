package com.tolmatchev.newsintelligence.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

/** Data transfer object for a TASS RSS channel. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TassChannelDto(
    @JacksonXmlElementWrapper(useWrapping = false) @JacksonXmlProperty(localName = "item")
        List<TassItemDto> items) {}
