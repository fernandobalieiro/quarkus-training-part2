package org.acme.postgres.panache;

import java.util.List;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @WithSession
    public Uni<List<Person>> findAll() {
        return personRepository.findAll().list();
    }

    public Uni<Person> findById(final Long id) {
        return personRepository.findById(id);
    }

    @WithTransaction
    public Uni<Person> create(final Person person) {
        return personRepository.persist(person);
    }

    @WithTransaction
    public Uni<Person> update(final Long id, final Person person) {
        return findById(id)
                .onItem().ifNotNull().transform(entity -> {
                    entity.setName(person.getName());
                    entity.setStatus(person.getStatus());
                    entity.setBirthDate(person.getBirthDate());
                    return entity;
                });
    }

    @WithTransaction
    public Uni<Boolean> deleteById(final Long id) {
        return personRepository.deleteById(id);
    }
}
