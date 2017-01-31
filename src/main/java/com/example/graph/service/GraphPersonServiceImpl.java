package com.example.graph.service;

import com.example.graph.domain.GraphPerson;
import com.example.graph.repository.GraphPersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraphPersonServiceImpl implements GraphPersonService {

  @Autowired
  private GraphPersonRepository graphPersonRepository;

  @Override
  public GraphPerson create(GraphPerson graphPerson) {
    return graphPersonRepository.save(graphPerson);
  }

  @Override
  public GraphPerson findOne(Long id) {
    return graphPersonRepository.findOne(id);
  }
}
