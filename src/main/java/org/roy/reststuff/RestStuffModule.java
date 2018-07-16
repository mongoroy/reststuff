package org.roy.reststuff;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestStuffModule extends AbstractModule {
  final Logger logger = LoggerFactory.getLogger(RestStuffModule.class);

  @Inject
  private RestStuffConfiguration configuration;

  @Override
  protected void configure() {
    Map<String, String> map = new HashMap<>();
    map.put("defaultName", configuration.getDefaultName());
    map.put("template", configuration.getTemplate());
    Names.bindProperties(binder(), map);
  }

/*  @Provides
  @Named("defaultName")
  public String provideDefaultName(RestStuffConfiguration configuration) {
    logger.info("Getting defaultName: " + configuration.getDefaultName());
    return configuration.getDefaultName();
  }

  @Provides
  @Named("template")
  public String provideTemplate(RestStuffConfiguration configuration) {
    logger.info("Getting template: " + configuration.getTemplate());
    return configuration.getTemplate();
  }
*/
}
