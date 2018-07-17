package org.roy.reststuff.resources;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.util.Duration;
import javax.ws.rs.client.Client;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.roy.reststuff.TestApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookResourceTest {
  final Logger logger = LoggerFactory.getLogger(BookResourceTest.class);

  @ClassRule
  public static final DropwizardAppRule<Configuration> RULE =
      new DropwizardAppRule<>(TestApplication.class, ResourceHelpers.resourceFilePath("test-config.yml"));

  protected static Client client;

  @BeforeClass
  public static void setUp() {
    HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration();
    httpClientConfiguration.setTimeout(Duration.milliseconds(4000));

    HttpClientBuilder clientBuilder = new HttpClientBuilder(RULE.getEnvironment());
    clientBuilder.using(httpClientConfiguration);

    JerseyClientBuilder builder = new JerseyClientBuilder(RULE.getEnvironment());
    builder.setApacheHttpClientBuilder(clientBuilder);
    client = builder.build("test client");
  }

  @AfterClass
  public static void tearDown() {
    //JerseyGuiceUtils.reset();
  }

  @Test
  public void testBookFind() {

    // when
    final String messageBooks = client.target(
        String.format("http://localhost:%d/books", RULE.getLocalPort()))
        .request()
        .get(String.class);

    logger.info("Books: " + messageBooks);

    final JSONArray jsonBooks = getJSONArray(messageBooks);
    assertTrue(jsonBooks.length() > 0);

    final JSONObject book = jsonBooks.getJSONObject(0);

    // when
    final String message = client.target(
        String.format("http://localhost:%d/books/" + book.get("id"), RULE.getLocalPort()))
        .request()
        .get(String.class);

    final JSONObject json = getJSONObject(message);

    // then
    assertThat(json.getString("title")).isEqualTo(book.getString("title"));
  }

  static JSONArray getJSONArray(String message) {
    return new JSONArray(message);
  }

  static JSONObject getJSONObject(String message) {
    return new JSONObject(message);
  }
}
