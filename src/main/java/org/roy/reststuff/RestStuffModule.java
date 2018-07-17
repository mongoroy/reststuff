package org.roy.reststuff;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Arrays;
import java.util.List;
import javax.inject.Named;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.roy.reststuff.annotations.CacheWithChangeStream;
import org.roy.reststuff.interceptors.CacheWithChangeStreamInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestStuffModule extends AbstractModule {
  final Logger logger = LoggerFactory.getLogger(RestStuffModule.class);

  @Inject
  private RestStuffConfiguration configuration;

  private MongoClient mongoClient;

  @Override
  protected void configure() {
    /*
    Map<String, String> map = new HashMap<>();
    map.put("defaultName", configuration.getDefaultName());
    map.put("template", configuration.getTemplate());
    Names.bindProperties(binder(), map);
    */
  }

  @Provides
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

  @Provides
  public MongoClient provideMongoClient(RestStuffConfiguration configuration) {
    if (mongoClient == null) {
      CodecRegistry pojoCodecRegistry =
          fromRegistries(
              MongoClientSettings.getDefaultCodecRegistry(),
              fromProviders(
                  PojoCodecProvider.builder()
                      .automatic(true)
                      .conventions(Arrays.asList(
                          Conventions.ANNOTATION_CONVENTION,
                          Conventions.CLASS_AND_PROPERTY_CONVENTION,
                          Conventions.SET_PRIVATE_FIELDS_CONVENTION
                      ))
                      .build()));
      MongoClientSettings settings = MongoClientSettings.builder()
          .applyConnectionString(new ConnectionString("mongodb://" + configuration.getMdbHost() + ":" + configuration.getMdbPort()))
          .codecRegistry(pojoCodecRegistry)
          .build();
      mongoClient = MongoClients.create(settings);
    }
    return mongoClient;
  }

}
