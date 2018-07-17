package org.roy.reststuff;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class TestApplication extends Application<Configuration> {

  @Override
  public void initialize(final Bootstrap<Configuration> bootstrap) {
    bootstrap.addBundle(GuiceBundle.builder()
      .modules(new TestModule(), new CacheWithChangeStreamModule())
      .enableAutoConfig(getClass().getPackage().getName())
      .build());
  }

  @Override
  public void run(Configuration configuration, Environment environment) throws Exception {

  }
}
