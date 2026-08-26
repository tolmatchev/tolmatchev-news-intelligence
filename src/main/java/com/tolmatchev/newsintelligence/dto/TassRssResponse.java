package com.tolmatchev.newsintelligence.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/** Response object for TASS RSS feed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TassRssResponse(@JacksonXmlProperty(localName = "channel") TassChannelDto channel) {}
