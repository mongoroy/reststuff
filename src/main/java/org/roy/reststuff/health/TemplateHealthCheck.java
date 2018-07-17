package org.roy.reststuff.health;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import ru.vyarus.dropwizard.guice.module.installer.feature.health.NamedHealthCheck;

@Singleton
public class TemplateHealthCheck extends NamedHealthCheck {
  private final String template;

  @Inject
  public TemplateHealthCheck(@Named("template") String template) {
    this.template = template;
  }

  @Override
  protected Result check() throws Exception {
    final String saying = String.format(template, "TEST");
    if (!saying.contains("TEST")) {
      return Result.unhealthy("template doesn't include a name");
    }
    return Result.healthy();
  }

  @Override
  public String getName() {
    return "template";
  }

}
