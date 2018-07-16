package org.roy.reststuff;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.util.HashMap;
import java.util.Map;

public class TestModule extends AbstractModule {

  @Override
  protected void configure() {
    Map<String, String> map = new HashMap<>();
    map.put("defaultName", "Stranger");
    map.put("template", "Hello, %s!");
    Names.bindProperties(binder(), map);
  }

}
