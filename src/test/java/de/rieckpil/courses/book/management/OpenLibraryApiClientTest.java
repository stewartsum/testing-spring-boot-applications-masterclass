package de.rieckpil.courses.book.management;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static reactor.netty.tcp.TcpClient.*;

class OpenLibraryApiClientTest {

  private MockWebServer mockWebServer;
  private OpenLibraryApiClient cut;

  private static final String ISBN = "9780596004651";

  private static String VALID_RESPONSE;

  @BeforeEach
  public void setup() throws IOException {

    TcpClient tcpClient = create()
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1_000)
      .doOnConnected(connection ->
        connection.addHandlerLast(new ReadTimeoutHandler(1))
          .addHandlerLast(new WriteTimeoutHandler(1)));

    this.mockWebServer = new MockWebServer();
    this.mockWebServer.start();

    this.cut = new OpenLibraryApiClient(
      WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
        .baseUrl(mockWebServer.url("/").toString())
        .build()
    );
  }

  @Test
  void notNull() {
    assertNotNull(cut);
    assertNotNull(mockWebServer);
  }

  @Test
  void shouldReturnBookWhenResultIsSuccess() throws InterruptedException {
  }

  @Test
  void shouldReturnBookWhenResultIsSuccessButLackingAllInformation() {
  }

  @Test
  void shouldPropagateExceptionWhenRemoteSystemIsDown() {
  }

  @Test
  void shouldRetryWhenRemoteSystemIsSlowOrFailing() {
  }
}
