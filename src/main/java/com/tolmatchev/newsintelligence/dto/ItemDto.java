package com.tolmatchev.newsintelligence.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record ItemDto(
    String title,
    String link,
    String guid,
    OffsetDateTime pubDate,
    String description,
    List<String> categories
) {}
