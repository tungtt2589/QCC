package com.storm.quora.service.impl;

import com.storm.quora.domain.Person;
import com.storm.quora.repository.PersonRepository;
import com.storm.quora.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Page<Person> findAlPageable(Pageable pageable) {
        return personRepository.findAll(pageable);
    }
}
