# reststuff
just a basic restapi setup with dropwizard, guice and mongodb

The mongodb needs to run on localhost:27017 and be a replica set. The tests are dependent on a `test.books` and `test.persons` existing.

Run the below in mongo shell
```
use test
db.books.insert( { title: "MongoDB for dummies", available: 2 } );
db.persons.insert( { name: "John Smith", accountLocked: false } );
```

to compile: `mvn package`

to run: `java -jar target/rest-stuff-1.0-SNAPSHOT.jar server rest-stuff.yml`

After running you can interact with the REST API via curl:

GET a list of Persons:
`curl http://localhost:8080/persons`

To GET a specific Person:
`curl http://localhost:8080/persons/[ID]`

Add a person:
`curl http://localhost:8080/persons/ -d "name=[name]"`

Update if a person is locked:
`curl http://localhost:8080/persons/[ID]/isLocked -d "isLocked=[true/false]"`
