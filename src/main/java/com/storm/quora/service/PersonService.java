package com.storm.quora.service;

import com.storm.quora.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    Page<Person> findAlPageable(Pageable pageable);
}
