package com.tolmatchev.newsintelligence.service;

import com.tolmatchev.newsintelligence.dto.TassRssResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for validating incoming RSS data. */
@Slf4j
@Service
public class ValidationService {

  /**
   * Validates that the TASS RSS response contains any items.
   *
   * @param data the response to validate.
   * @return true if the response is valid and contains items, false otherwise.
   */
  public boolean isTassRssValid(TassRssResponse data) {
    if (data == null || data.channel() == null || data.channel().items() == null) {
      log.info("No RSS items to process");
      return false;
    }
    return true;
  }
}
