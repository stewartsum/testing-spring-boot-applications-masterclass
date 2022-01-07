package de.rieckpil.courses.book.review;

import java.util.logging.Level;

import de.rieckpil.courses.AbstractWebTest;
import de.rieckpil.courses.book.management.Book;
import de.rieckpil.courses.book.management.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;

@DisabledIfSystemProperty(named = "os.arch", matches = "aarch64", disabledReason = "Selenium Docker image doesn't support ARM64 (yet), see Selenium Docker image doesn't support ARM64 (yet), see https://github.com/rieckpil/testing-spring-boot-applications-masterclass/issues/31")
class ReviewCreationWT extends AbstractWebTest {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  private static final LoggingPreferences LOG_PREFERENCES;
  private static final ChromeOptions CHROME_OPTIONS;

  static {
    LOG_PREFERENCES = new LoggingPreferences();
    LOG_PREFERENCES.enable(LogType.BROWSER, Level.ALL);

    CHROME_OPTIONS = new ChromeOptions();
    CHROME_OPTIONS.addArguments("--no-sandbox");
    CHROME_OPTIONS.addArguments("--disable-dev-shm-usage");

    CHROME_OPTIONS.setCapability("goog:loggingPrefs", LOG_PREFERENCES);
  }

  @Container
  static BrowserWebDriverContainer<?> webDriverContainer = new BrowserWebDriverContainer<>()
    .withCapabilities(CHROME_OPTIONS);

  private static final String ISBN = "9780321751041";

  @Test
  void shouldCreateReviewAndDisplayItInReviewList() {
  }

  private void createBook() {
    Book book = new Book();
    book.setPublisher("Duke Inc.");
    book.setIsbn(ISBN);
    book.setPages(42L);
    book.setTitle("Joyful testing with Spring Boot");
    book.setDescription("Writing unit and integration tests for Spring Boot applications");
    book.setAuthor("rieckpil");
    book.setThumbnailUrl("https://rieckpil.de/wp-content/uploads/2020/08/tsbam_introduction_thumbnail-585x329.png.webp");
    book.setGenre("Software Development");

    this.bookRepository.save(book);
  }
}
