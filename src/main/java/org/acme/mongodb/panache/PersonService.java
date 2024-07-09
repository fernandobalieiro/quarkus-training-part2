package org.acme.mongodb.panache;

import java.util.List;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Uni<List<Person>> findAll() {
        return personRepository.findAll().list();
    }

    public Uni<Person> findById(final String id) {
        return personRepository.findById(new ObjectId(id));
    }

    public Uni<Person> create(final Person person) {
        return personRepository.persist(person);
    }

    public Uni<Person> update(final String id, final Person person) {
        person.setId(new ObjectId(id));

        return personRepository.update(person);
    }

    public Uni<Boolean> deleteById(final String id) {
        return personRepository.deleteById(new ObjectId(id));
    }
}
