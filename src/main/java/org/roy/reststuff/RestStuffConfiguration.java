package org.roy.reststuff;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meltmedia.dropwizard.mongo.MongoConfiguration;
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
  @JsonProperty
  private String defaultName;

  @JsonProperty
  protected MongoConfiguration mongo;

  public String getTemplate() {
    return template;
  }

  public String getDefaultName() {
    return defaultName;
  }

  public MongoConfiguration getMongo() {
    return mongo;
  }
}
