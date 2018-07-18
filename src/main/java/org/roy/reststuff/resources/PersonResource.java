package org.roy.reststuff.resources;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bson.types.ObjectId;
import org.roy.reststuff.dao.PersonDao;
import org.roy.reststuff.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
  final Logger logger = LoggerFactory.getLogger(PersonResource.class);

  final PersonDao personDao;

  @Inject
  public PersonResource(PersonDao personDao) {
    this.personDao = personDao;
  }


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Person> getPersons() {
    return personDao.findAll();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson(@PathParam("id") String id) {
    logger.info("Looking up " + id);
    return personDao.find(new ObjectId(id));
  }
}
