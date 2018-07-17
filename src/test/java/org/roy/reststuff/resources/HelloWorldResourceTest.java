package org.roy.reststuff.resources;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.common.io.Resources;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.Configuration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.client.JerseyClientBuilder;
import java.io.File;
import javax.ws.rs.client.Client;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.roy.reststuff.TestApplication;

public class HelloWorldResourceTest {

  @ClassRule
  public static final DropwizardAppRule<Configuration> RULE =
      new DropwizardAppRule<>(TestApplication.class, ResourceHelpers.resourceFilePath("test-config.yml"));

  protected static Client client;

  @BeforeClass
  public static void setUp() {
    client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");
  }

  @AfterClass
  public static void tearDown() {
    JerseyGuiceUtils.reset();
  }

  @Test
  public void shouldGetHelloTestMessage() {

    // when
    final String message = client.target(
        String.format("http://localhost:%d/hello-world", RULE.getLocalPort()))
        .request()
        .get(String.class);

    // then
    assertThat(message).isEqualTo("{\"id\":1,\"content\":\"Hello, Stranger!\"}");
  }

}
