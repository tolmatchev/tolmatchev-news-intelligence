package com.tolmatchev.newsintelligence.service;

import com.tolmatchev.newsintelligence.dto.TassRssResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationServiceTest {

    private final ValidationService validationService = new ValidationService();

    @Test
    void shouldReturnTrueForValidResponse() {
        TassRssResponse data = new TassRssResponse(new com.tolmatchev.newsintelligence.dto.TassChannelDto(List.of()));
        boolean isValid = validationService.isTassRssValid(data);
        assertThat(isValid).isTrue();
    }

    @Test
    void shouldReturnFalseForNullResponse() {
        boolean isValid = validationService.isTassRssValid(null);
        assertThat(isValid).isFalse();
    }

    @Test
    void shouldReturnFalseForNullChannel() {
        TassRssResponse data = new TassRssResponse(null);
        boolean isValid = validationService.isTassRssValid(data);
        assertThat(isValid).isFalse();
    }

    @Test
    void shouldReturnFalseForNullItems() {
        TassRssResponse data = new TassRssResponse(new com.tolmatchev.newsintelligence.dto.TassChannelDto(null));
        boolean isValid = validationService.isTassRssValid(data);
        assertThat(isValid).isFalse();
    }
}
