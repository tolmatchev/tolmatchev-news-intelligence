package com.tolmatchev.newsintelligence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "news")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String site;

  @Column(nullable = false)
  private String title;

  private String category;

  @Column(nullable = false)
  private LocalDateTime publicationDate;

  @Column(nullable = false, length = 2048)
  private String link;
}
