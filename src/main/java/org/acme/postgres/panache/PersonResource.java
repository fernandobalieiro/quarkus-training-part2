package org.acme.postgres.panache;

import java.net.URI;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/people")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class PersonResource {

    private final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);

    private final PersonService personService;

    public PersonResource(final PersonService personService) {
        this.personService = personService;
    }

    @GET
    public List<Person> get() {
        LOGGER.info("Get all people");
        return personService.findAll();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") final Long id) {
        final var person = personService.findById(id);
        final var result = person != null && person.getId() != null ? Response.ok(person) : Response.status(NOT_FOUND);

        return result.build();
    }

    @POST
    public Response create(final Person person) {
        personService.create(person);
        final var uri = URI.create("/people/" + person.getId());

        return Response.created(uri).entity(person).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") final Long id, final Person person) {
        try {
            final var updatedPerson = personService.update(id, person);
            return Response.ok(updatedPerson).build();
        } catch (final EntityNotFoundException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") final Long id) {
        final var isDeleted = personService.deleteById(id);

        final var result = isDeleted ? Response.ok() : Response.status(NOT_FOUND);

        return result.build();
    }
}
