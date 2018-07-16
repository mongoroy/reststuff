package org.roy.reststuff;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestStuffConfiguration extends Configuration {
  final Logger logger = LoggerFactory.getLogger(RestStuffConfiguration.class);

  @NotEmpty
  @JsonProperty
  private String template;

  @NotEmpty
  private String defaultName;

  public String getTemplate() {
    return template;
  }

  @JsonProperty
  public String getDefaultName() {
    return defaultName;
  }

  @JsonProperty
  public void setDefaultName(String name) {
    logger.info("Default name: " + name);
    this.defaultName = name;
  }

}
