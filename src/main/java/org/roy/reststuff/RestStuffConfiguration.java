package org.roy.reststuff;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.validation.MaxSize;
import io.dropwizard.validation.MinSize;
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

  @NotEmpty
  @JsonProperty
  private String mdbHost;

  @JsonProperty
  private int mdbPort = 27017;

  public String getTemplate() {
    return template;
  }

  public String getDefaultName() {
    return defaultName;
  }

  public String getMdbHost() {
    return mdbHost;
  }

  public int getMdbPort() {
    return mdbPort;
  }
}
