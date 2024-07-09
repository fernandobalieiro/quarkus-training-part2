package org.acme.mongodb.panache;

import java.net.URI;
import java.util.List;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/people")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class PersonResource {

    private final PersonService personService;

    public PersonResource(final PersonService personService) {
        this.personService = personService;
    }

    @GET
    public Uni<List<Person>> get() {
        return personService.findAll();
    }

    @GET
    @Path("{id}")
    public Uni<Response> getById(@PathParam("id") final String id) {
        return personService.findById(id)
                .onItem().transform(person -> person != null && person.getId() != null ? Response.ok(person) : Response.status(NOT_FOUND))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(final Person person) {
        return personService.create(person)
                .onItem().transform(e -> URI.create("/people/" + e.getId()))
                .onItem().transform(uri -> Response.created(uri).entity(person).build());
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(@PathParam("id") final String id, final Person person) {
        return personService.update(id, person)
                .onItem().transform(item -> person.getId() != null ? Response.ok(person) : Response.status(NOT_FOUND))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@PathParam("id") final String id) {
        return personService.deleteById(id)
                .onItem().transform(status -> Response.ok().build());
    }
}
