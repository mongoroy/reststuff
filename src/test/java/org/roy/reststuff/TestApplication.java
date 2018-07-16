package org.roy.reststuff;

import static com.hubspot.dropwizard.guice.GuiceBundle.newBuilder;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TestApplication extends Application<Configuration> {

  @Override
  public void initialize(final Bootstrap<Configuration> bootstrap) {
    final GuiceBundle<Configuration> jersey2GuiceBundle = newBuilder()
        .addModule(new TestModule())
        .enableAutoConfig(this.getClass().getPackage().getName())
        .build();
    bootstrap.addBundle(jersey2GuiceBundle);
  }

  @Override
  public void run(Configuration configuration, Environment environment) throws Exception {

  }
}
