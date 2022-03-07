package de.rieckpil.courses.book.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
//@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryNoInMemoryTest {

//  @Container
  static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12.3")
    .withDatabaseName("test")
    .withUsername("duke")
    .withPassword("s3cret")
    .withReuse(true);

  static {
    container.start();
  }

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Autowired
  private ReviewRepository cut;

  @Test
  @Sql(scripts = "/scripts/INIT_REVIEW_EACH_BOOK.sql")
  void shouldGetTwoReviewStatisticsWhenDatabaseContainsTwoBooksWithReview() {

    List<ReviewStatistic> reviewStatistics = cut.getReviewStatistics();
    assertEquals(3, cut.count());
    assertEquals(2, reviewStatistics.size());

    reviewStatistics.forEach(reviewStatistic -> {
      System.out.println("ReviewStatistic");
      System.out.println(reviewStatistic.getId());
      System.out.println(reviewStatistic.getAvg());
      System.out.println(reviewStatistic.getIsbn());
      System.out.println(reviewStatistic.getRatings());
      System.out.println("");
    });
    /*
        ReviewStatistic
        2
        3.00
        1234567891235
        2

        ReviewStatistic
        1
        5.00
        1234567891234
        1

     */

    assertEquals(2, reviewStatistics.get(0).getRatings());
    assertEquals(2, reviewStatistics.get(0).getId());
    assertEquals(new BigDecimal("3.00"), reviewStatistics.get(0).getAvg());
  }

  @Test
  void databaseShouldBeEmpty() { // Do not write this in your own test. Only here to show the database is empty after previous test runs
    assertEquals(0, cut.count());
  }
}
