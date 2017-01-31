package com.example.relational.repository;

import com.example.relational.domain.Person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>  {
}
