package de.rieckpil.courses.book.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(properties = {
  "spring.flyway.enabled=false", // Disable Flyway
  "spring.jpa.hibernate.ddl-auto=create-drop", // Helpful for in-memory databases
  "spring.datasource.driver-class-name=com.p6spy.engine.spy.P6SpyDriver", // P6Spy
  "spring.datasource.url=jdbc:p6spy:h2:mem:testing;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false" // P6Spy
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private ReviewRepository cut;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  void notNull() throws SQLException {

    assertNotNull(entityManager);
    assertNotNull(cut);
    assertNotNull(testEntityManager);
    assertNotNull(dataSource);

    System.out.println("Database Product Name: " + dataSource.getConnection().getMetaData().getDatabaseProductName()); // Database Product Name: H2

    Review review = new Review("Good review");
    review.setTitle("Review 101");
    review.setCreatedAt(LocalDateTime.now());
    review.setRating(5);
    review.setBook(null);
    review.setUser(null);

    //Review result = cut.save(review); // insert into reviews (id, book_id, content, created_at, rating, title, user_id) values (null, NULL, 'Good review', '2022-03-07T02:25:56.879-0700', 5, 'Review 101', NULL);
    Review result = testEntityManager.persistFlushFind(review); // javax.persistence.PersistenceException: org.hibernate.InstantiationException: No default constructor for entity: : de.rieckpil.courses.book.review.Review

    System.out.println("result: " + result); // result: Review{id=1, title='Review 101', content='Good review', rating=5, createdAt=2022-03-07T02:25:56.879116008, book=null, user=null}
    assertNotNull(result.getId());
  }

  @Test
  void transactionalSupportTest() {
  }
}
