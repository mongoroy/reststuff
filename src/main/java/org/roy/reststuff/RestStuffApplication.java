package org.roy.reststuff;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.roy.reststuff.health.TemplateHealthCheck;
import org.roy.reststuff.resources.HelloWorldResource;

public class RestStuffApplication extends Application<RestStuffConfiguration> {
  public static void main(String[] args) throws Exception {
    new RestStuffApplication().run(args);
  }

  @Override
  public String getName() {
    return "hello-world";
  }

  @Override
  public void initialize(Bootstrap<RestStuffConfiguration> bootstrap) {

  }

  @Override
  public void run(RestStuffConfiguration conf, Environment env) {
    final HelloWorldResource resource = new HelloWorldResource(conf.getTemplate(), conf.getDefaultName());
    env.jersey().register(resource);

    final TemplateHealthCheck healthCheck = new TemplateHealthCheck(conf.getTemplate());
    env.healthChecks().register("template", healthCheck);
    env.jersey().register(resource);

  }

}
