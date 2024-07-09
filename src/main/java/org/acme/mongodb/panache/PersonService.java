package org.acme.mongodb.panache;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll().list();
    }

    public Person findById(final String id) {
        return personRepository.findById(new ObjectId(id));
    }

    public Person create(final Person person) {
        personRepository.persist(person);
        return person;
    }

    public Person update(final String id, final Person person) {
        person.setId(new ObjectId(id));

        personRepository.update(person);

        return person;
    }

    public boolean deleteById(final String id) {
        return personRepository.deleteById(new ObjectId(id));
    }
}
