package org.roy.reststuff.cache;

import static java.util.Arrays.asList;

import com.google.common.cache.Cache;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import com.mongodb.client.model.changestream.OperationType;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class CacheWatcher implements Runnable {
  final Logger logger = LoggerFactory.getLogger(CacheWatcher.class);

  private final MongoClient mongoClient;
  private final String database;
  private final String collection;
  private final Class pojoClass;
  private final Cache<DocumentIdentifierKey, Object> cache;

  public void run() {

    logger.info("Starting watcher: {}.{}", database, collection);
    MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection, pojoClass);
    mongoCollection.watch(
        asList(
            Aggregates.match(
                Filters.in("operationType",
                    asList("update", "replace", "delete")))))
        .fullDocument(FullDocument.UPDATE_LOOKUP)
        .forEach((Block<ChangeStreamDocument>) b -> {
      ObjectId id = b.getDocumentKey().get("_id").asObjectId().getValue();
      Object object = b.getFullDocument();
      logger.info("Detected change operation: {}, id: {}, doc: {}", b.getOperationType(), id, object);
      if (b.getOperationType() == OperationType.DELETE) {
        cache.invalidate(new DocumentIdentifierKey(id));
      }
      else {
        cache.put(new DocumentIdentifierKey(id), object);
      }
    });

  }

}
