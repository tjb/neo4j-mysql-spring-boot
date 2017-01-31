package com.example.graph.repository;

import com.example.graph.domain.GraphPerson;

import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by tbobella on 1/31/17.
 */
public interface GraphPersonRepository extends GraphRepository<GraphPerson> {
}
