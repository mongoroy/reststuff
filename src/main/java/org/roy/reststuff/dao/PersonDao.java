package org.roy.reststuff.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

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
import org.roy.reststuff.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class PersonDao {
  final Logger logger = LoggerFactory.getLogger(PersonDao.class);

  private MongoCollection<Person> collection;

  @Inject
  public PersonDao(MongoClient mongoClient) {
    this.collection = mongoClient.getDatabase(Person.DB).getCollection(Person.COLLECTION, Person.class);
  }

  public List<Person> findAll() {
    final List<Person> list = new ArrayList<>();
    collection.find().forEach((Block<Person>) b -> {
      list.add(b);
    });

    return list;
  }

  public void insert(Person person) {
    collection.insertOne(person);
  }

  public void updateAccountLocked(ObjectId id, boolean isLocked) {
    collection.updateOne(
        eq("_id", id),
        set("accountLocked", isLocked));
  }

  @CacheWithChangeStream(database=Person.DB, collection=Person.COLLECTION)
  public Person find(ObjectId id) {
    try (MongoCursor<Person> cursor = collection.find(eq("_id", id)).iterator()) {
      if (cursor.hasNext()) {
        Person b = cursor.next();
        logger.info("Person: " + b);
        return b;
      }
    }
    return null;
  }
}
