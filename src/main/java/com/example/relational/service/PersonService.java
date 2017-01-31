package com.example.relational.service;

import com.example.relational.domain.Person;


public interface PersonService {

  Person create(Person person);

  Person findOne(Long id);
}
