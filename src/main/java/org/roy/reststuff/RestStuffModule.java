package org.roy.reststuff;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class RestStuffModule extends AbstractModule {

  @Override
  protected void configure() {

  }

  @Provides
  @Named("template")
  public String provideTemplate(RestStuffConfiguration configuration) {
    return configuration.getTemplate();
  }

  @Provides
  @Named("defaultName")
  public String provideDefaultName(RestStuffConfiguration configuration) {
    return configuration.getDefaultName();
  }

}
