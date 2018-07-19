# reststuff
just a basic restapi setup with dropwizard, guice and mongodb

The mongodb needs to run on localhost:27017 and be a replica set. The tests are dependent on a `test.books` and `test.persons` existing.

Run the below in mongo shell
```
use test
db.books.insert( { title: "MongoDB for dummies", available: 2 } );
db.persons.insert( { name: "John Smith", accountLocked: false } );
```

to compile run `mvn package`
to run run `java -jar target/rest-stuff-1.0-SNAPSHOT.jar server rest-stuff.yml`

