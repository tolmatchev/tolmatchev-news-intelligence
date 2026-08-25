package com.tolmatchev.newsintelligence.service;

import com.tolmatchev.newsintelligence.dto.RssResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidationService {

    public boolean isTassRssValid(RssResponse data) {
        if (data == null || data.channel() == null || data.channel().items() == null) {
            log.info("No RSS items to process");
            return false;
        }
        return true;
    }
}
