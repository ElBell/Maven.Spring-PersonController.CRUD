package io.zipcoder.crudapp.controller;


import io.zipcoder.crudapp.entity.Person;
import io.zipcoder.crudapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        return new ResponseEntity<>(personRepository.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPersonList() {
        return new ResponseEntity<>((List<Person>) personRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        person.setId(id);
        if (personRepository.findOne(id) != null) {
            personRepository.delete(id);
            return new ResponseEntity<>(personRepository.save(person), HttpStatus.OK);
        }
        return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED);
    }

    @DeleteMapping("/people/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable int id) {
        personRepository.delete(id);
    }
}
