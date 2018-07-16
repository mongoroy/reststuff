package org.roy.reststuff.resources;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.common.io.Resources;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import java.io.File;
import javax.ws.rs.client.Client;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.roy.reststuff.TestApplication;

public class BookResourceTest {

  @ClassRule
  public static final DropwizardAppRule<Configuration> RULE =
      new DropwizardAppRule<>(TestApplication.class, resourceFilePath("test-config.yml"));

  protected static Client client;

  @BeforeClass
  public static void setUp() {
    client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");
  }

  @AfterClass
  public static void tearDown() {
    JerseyGuiceUtils.reset();
  }

  public static String resourceFilePath(String resourceClassPathLocation) {
    try {
      return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  //@Test
  public void testBookFind() {
    // when
    final String message = client.target(
        String.format("http://localhost:%d/book/5b4d11fe82a34fef43e8df4b", RULE.getLocalPort()))
        .request()
        .get(String.class);

    final JSONObject json = getJSONObject(message);

    // then
    assertThat(json.getString("title")).isEqualTo("the big cheese");
  }

  static JSONObject getJSONObject(String message) {
    return new JSONObject(message);
  }
}
