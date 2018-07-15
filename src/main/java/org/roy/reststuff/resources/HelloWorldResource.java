package org.roy.reststuff.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.roy.reststuff.api.Saying;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
  private final String template;
  private final String defaultName;
  private final AtomicLong counter;

  @Inject
  public HelloWorldResource(@Named("template") String template, @Named("defaultName") String defaultName) {
    this.template = template;
    this.defaultName = defaultName;
    this.counter = new AtomicLong();
  }

  @GET
  @Timed
  public Saying sayHello(@QueryParam("name") Optional<String> name) {
    final String value = String.format(template, name.orElse(defaultName));
    return new Saying(counter.incrementAndGet(), value);
  }

}
