package org.acme.postgres.panache;

import java.net.URI;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/people")
// FIXME: Missing configuration
public class PersonResource {

    private final PersonService personService;

    // TODO: Implement the method to get all people
    // TODO: Implement the method to get a person by id

    @POST
    public Response create(final Person person) {
        personService.create(person);
        final var uri = URI.create("/people/" + person.getId());

        return Response.created(uri).entity(person).build();
    }

    // FIXME: Missing annotations
    public Response update(final Long id, final Person person) {
        try {
            final var updatedPerson = personService.update(id, person);
            return Response.ok(updatedPerson).build();
        } catch (final EntityNotFoundException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    // FIXME: Missing annotations
    public Response delete(final Long id) {
        final var isDeleted = personService.deleteById(id);

        final var result = isDeleted ? Response.ok() : Response.status(NOT_FOUND);

        return result.build();
    }
}
