package org.roy.reststuff;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class RestStuffApplication extends Application<RestStuffConfiguration> {
  public static void main(String[] args) throws Exception {
    new RestStuffApplication().run(args);
  }

  @Override
  public String getName() {
    return "rest-stuff";
  }

  @Override
  public void initialize(Bootstrap<RestStuffConfiguration> bootstrap) {
    bootstrap.addBundle(GuiceBundle.builder()
        .modules(new RestStuffModule(), new CacheWithChangeStreamModule())
        .enableAutoConfig(getClass().getPackage().getName())
        .build());
  }

  @Override
  public void run(RestStuffConfiguration conf, Environment env) {
  }

}
