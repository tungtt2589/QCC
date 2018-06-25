package com.storm.quora.repository;

import com.storm.quora.domain.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

}
