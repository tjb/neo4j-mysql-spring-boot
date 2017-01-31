package com.example.graph.service;

import com.example.graph.domain.GraphPerson;

public interface GraphPersonService {

  GraphPerson create(GraphPerson graphPerson);

  GraphPerson findOne(Long id);
}
