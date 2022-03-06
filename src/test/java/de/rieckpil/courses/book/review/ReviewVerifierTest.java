package de.rieckpil.courses.book.review;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static de.rieckpil.courses.book.review.RandomReviewParameterResolverExtension.RandomReview;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(RandomReviewParameterResolverExtension.class)
class ReviewVerifierTest {

  private ReviewVerifier reviewVerifier;

  @BeforeEach
  public void setup() {
    reviewVerifier = new ReviewVerifier();
  }

  @Test
  void shouldFailWhenReviewContainsSwearWord() {
    String review = "This book is shit";
    System.out.println("Testing a review");

    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect swear word");
  }

  @Test
  @DisplayName("Should fail when review contains \"lorem ipsum\"")
  void testLoremIpsum() {

    String review = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
      " labore et dolore magna aliqua. Aliquet sagittis id consectetur purus ut faucibus pulvinar elementum.";


    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect lorem ipsum");
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/badReview.csv")
  void shouldFailWhenReviewIsOfBadQuality(String review) {
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect bad review");
  }

  @RepeatedTest(5)
  void shouldFailWhenRandomReviewQualityIsBad(@RandomReview String review) {
    System.out.println(review);
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect random bad review");
  }

  @Test
  void shouldPassWhenReviewIsGood() {
    String review = "I can totally recommend this book " +
      "who is interested in learning how to write Java code!";

    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertTrue(result, "ReviewVerifier did not pass a good review");
  }

  @Test
  void shouldPassWhenReviewIsGoodHamcrest() {
    String review = "I can totally recommend this book " +
      "who is interested in learning how to write Java code!";

    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    // assertTrue(result, "ReviewVerifier did not pass a good review"); // JUnit 5

    MatcherAssert.assertThat("ReviewVerifier did not pass a good review", result, Matchers.equalTo(true));
    MatcherAssert.assertThat("Lorem ipsum", Matchers.endsWith("ipsum"));
    MatcherAssert.assertThat(List.of(1, 2, 3, 4, 5), Matchers.hasSize(5));
    MatcherAssert.assertThat(List.of(1, 2, 3, 4, 5), Matchers.anyOf(Matchers.hasSize(5), Matchers.emptyIterable()));
  }

  @Test
  void shouldPassWhenReviewIsGoodAssertJ() {
    String review = "I can totally recommend this book " +
      "who is interested in learning how to write Java code!";

    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    // assertTrue(result, "ReviewVerifier did not pass a good review"); // JUnit 5

    Assertions.assertThat(result)
      .withFailMessage("ReviewVerifier did not pass a good review")
      .isEqualTo(true)
      .isTrue();

    Assertions.assertThat(List.of(1, 2, 3, 4, 5)).hasSizeBetween(1, 10);
    Assertions.assertThat(List.of(1, 2, 3, 4, 5)).contains(3).isNotEmpty();
  }
}
