package org.roy.reststuff.dao;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bson.types.ObjectId;
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

  public Book find(ObjectId id) {
    final List<Book> books = new ArrayList<>();
    collection.find(eq("_id", id)).forEach(new Block<Book>() {
      @Override
      public void apply(final Book b) {
        logger.info("Found: " + b);
        books.add(b);
      }
    });

    return (books.size() > 0) ? books.get(0) : null;
  }


}
