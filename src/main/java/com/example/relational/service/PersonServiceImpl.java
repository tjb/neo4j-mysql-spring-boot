package com.example.relational.service;

import com.example.relational.domain.Person;
import com.example.relational.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public Person create(Person person) {
    return personRepository.save(person);
  }

  @Override
  public Person findOne(Long id) {
    return personRepository.findOne(id);
  }
}
