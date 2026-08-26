package com.tolmatchev.newsintelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/** Main application class for the News Intelligence system. */
@EnableScheduling
@SpringBootApplication
public class NewsIntelligenceApplication {

  /**
   * Main method to start the Spring Boot application.
   *
   * @param args command line arguments.
   */
  public static void main(final String[] args) {
    SpringApplication.run(NewsIntelligenceApplication.class, args);
  }
}
