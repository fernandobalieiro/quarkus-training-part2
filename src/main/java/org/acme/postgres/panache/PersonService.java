package org.acme.postgres.panache;

import jakarta.persistence.EntityNotFoundException;

// FIXME
public class PersonService {

    // FIXME: Missing
    private final PersonRepository personRepository;


    // TODO: Implement missing methods

    public Person update(final Long id, final Person person) {
        final var personToUpdate = findById(id);

        if (personToUpdate == null) {
            throw new EntityNotFoundException("Person with id " + id + " not found");
        }

        personToUpdate.setName(person.getName());
        personToUpdate.setStatus(person.getStatus());
        personToUpdate.setBirthDate(person.getBirthDate());

        personRepository.persist(personToUpdate);

        return personToUpdate;
    }
}
