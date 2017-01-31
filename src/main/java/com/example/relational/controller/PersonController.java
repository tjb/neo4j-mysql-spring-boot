package com.example.relational.controller;

import com.example.relational.domain.Person;
import com.example.relational.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/person")
public class PersonController {

  @Autowired
  private PersonService personService;

  @RequestMapping(method = RequestMethod.POST)
  public Person createPerson(@RequestBody Person person) {
    return personService.create(person);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Person getPerson(@PathVariable("id") Long id) {
    return personService.findOne(id);
  }

}
