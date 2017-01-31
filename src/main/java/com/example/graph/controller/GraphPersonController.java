package com.example.graph.controller;

import com.example.graph.domain.GraphPerson;
import com.example.graph.service.GraphPersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/graphPerson")
public class GraphPersonController {

  @Autowired
  private GraphPersonService graphPersonService;

  @RequestMapping(method = RequestMethod.POST)
  public GraphPerson createPerson(@RequestBody GraphPerson graphPerson) {
    return graphPersonService.create(graphPerson);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public GraphPerson getPerson(@PathVariable("id") Long id) {
    return graphPersonService.findOne(id);
  }
}
