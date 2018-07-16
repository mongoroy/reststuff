package org.roy.reststuff.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.roy.reststuff.dao.BookDao;
import org.roy.reststuff.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
  final Logger logger = LoggerFactory.getLogger(BookResource.class);

  final BookDao bookDao;

  @Inject
  public BookResource(final BookDao bookDao) {
    logger.info("Starting BookResource");
    this.bookDao = bookDao;
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Book getBook(@PathParam("id") String id) {
    logger.info("Looking up " + id);
    return bookDao.find(new ObjectId(id));
  }


}
