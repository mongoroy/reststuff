package org.roy.reststuff;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.roy.reststuff.health.TemplateHealthCheck;
import org.roy.reststuff.resources.HelloWorldResource;

public class RestStuffApplication extends Application<RestStuffConfiguration> {
  private GuiceBundle<RestStuffConfiguration> guiceBundle;

  public static void main(String[] args) throws Exception {
    new RestStuffApplication().run(args);
  }

  @Override
  public String getName() {
    return "hello-world";
  }

  @Override
  public void initialize(Bootstrap<RestStuffConfiguration> bootstrap) {
    guiceBundle = GuiceBundle.<RestStuffConfiguration>newBuilder()
        .addModule(new RestStuffModule())
        .enableAutoConfig(getClass().getPackage().getName())
        .setConfigClass(RestStuffConfiguration.class)
        .build();
    bootstrap.addBundle(guiceBundle);
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
