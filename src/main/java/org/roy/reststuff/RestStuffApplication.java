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
    /* guice bundle auto config should find resources
    final HelloWorldResource resource = new HelloWorldResource(conf.getTemplate(), conf.getDefaultName());
    env.jersey().register(resource);
    */

    /* guice bundle auto config should find InjectableHealthCheck classes
    final TemplateHealthCheck healthCheck = new TemplateHealthCheck(conf.getTemplate());
    env.healthChecks().register("template", healthCheck);
    env.jersey().register(resource);
    */
  }

}
