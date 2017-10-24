package com.success.ndb.service;

import java.util.List;

import com.success.ndb.dto.PersonDTO;
import com.success.ndb.entities.Person;

public interface PersonService {

	List<PersonDTO> search(PersonDTO dto);

	PersonDTO addPerson(Person entity);

	PersonDTO updatePerson(Person entity);

	void deletePerson(Person entity);
}
