package de.rieckpil.courses.book.management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(BookController.class)
class BookControllerTest {

  @MockBean
  private BookManagementService bookManagementService;

  @Test
  void shouldStart() {}

}
