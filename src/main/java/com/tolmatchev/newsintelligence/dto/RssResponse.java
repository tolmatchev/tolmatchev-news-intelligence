package com.tolmatchev.newsintelligence.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RssResponse(
        @JacksonXmlProperty(localName = "channel")
        ChannelDto channel
) {}
