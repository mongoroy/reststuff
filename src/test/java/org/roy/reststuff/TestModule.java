package org.roy.reststuff;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.HashMap;
import java.util.Map;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class TestModule extends AbstractModule {

  private MongoClient mongoClient;

  @Override
  protected void configure() {
    Map<String, String> map = new HashMap<>();
    map.put("defaultName", "Stranger");
    map.put("template", "Hello, %s!");
    Names.bindProperties(binder(), map);
  }


  @Provides
  public MongoClient provideMongoClient(RestStuffConfiguration configuration) {
    if (mongoClient == null) {
      CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
          fromProviders(PojoCodecProvider.builder().automatic(true).build()));
      MongoClientSettings settings = MongoClientSettings.builder()
          .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
          .codecRegistry(pojoCodecRegistry)
          .build();
      mongoClient = MongoClients.create(settings);
    }
    return mongoClient;
  }

}
