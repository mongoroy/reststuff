package org.roy.reststuff.dao;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bson.types.ObjectId;
import org.roy.reststuff.annotations.CacheWithChangeStream;
import org.roy.reststuff.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BookDao {
  final Logger logger = LoggerFactory.getLogger(BookDao.class);

  private MongoCollection<Book> collection;

  @Inject
  public BookDao(MongoClient mongoClient) {
    this.collection = mongoClient.getDatabase(Book.DB).getCollection(Book.COLLECTION, Book.class);
  }

  public List<Book> findAll() {
    final List<Book> list = new ArrayList<>();
    collection.find().forEach((Block<Book>) b -> {
      logger.info("Book: " + b);
      list.add(b);
    });

    return list;
  }

  @CacheWithChangeStream
  public Book find(ObjectId id) {
    try (MongoCursor<Book> cursor = collection.find(eq("_id", id)).iterator()) {
      if (cursor.hasNext()) {
        Book b = cursor.next();
        logger.info("Book: " + b);
        return b;
      }
    }
    return null;
  }


}
