package de.rieckpil.courses.book.management;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

  @MockBean
  private BookManagementService bookManagementService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldGetEmptyArrayWhenNoBooksExist() throws Exception {
    MvcResult mvcResult = this.mockMvc
      .perform(get("/api/books")
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.size()", Matchers.is(0)))
      .andDo(print()) // Output below
      .andReturn();

    /*

      MockHttpServletRequest:
            HTTP Method = GET
            Request URI = /api/books
             Parameters = {}
                Headers = [Accept:"application/json"]
                   Body = null
          Session Attrs = {}

      Handler:
                   Type = de.rieckpil.courses.book.management.BookController
                 Method = de.rieckpil.courses.book.management.BookController#getAvailableBooks()

      Async:
          Async started = false
           Async result = null

      Resolved Exception:
                   Type = null

      ModelAndView:
              View name = null
                   View = null
                  Model = null

      FlashMap:
             Attributes = null

      MockHttpServletResponse:
                 Status = 200
          Error message = null
                Headers = [Vary:"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", Content-Type:"application/json", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
           Content type = application/json
                   Body = []
          Forwarded URL = null
         Redirected URL = null
                Cookies = []

     */
  }

}
