package com.example.graph.domain;

import org.neo4j.ogm.annotation.GraphId;

import java.io.Serializable;

public class GraphPerson implements Serializable {

  private static final long serialVersionUID = 3052327829901249416L;

  @GraphId
  private Long id;

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }
}
