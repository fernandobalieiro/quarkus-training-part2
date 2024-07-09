package org.acme.postgres.panache;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll().list();
    }

    public Person findById(final Long id) {
        return personRepository.findById(id);
    }

    @Transactional
    public void create(final Person person) {
        personRepository.persist(person);
    }

    @Transactional
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

    @Transactional
    public boolean deleteById(final Long id) {
        return personRepository.deleteById(id);
    }
}
