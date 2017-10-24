package com.success.ndb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.success.ndb.assemblers.PersonAssembler;
import com.success.ndb.dto.PersonDTO;
import com.success.ndb.service.PersonService;

@RestController
@RequestMapping("/rest/person")
@CrossOrigin
public class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping("/save")
	public PersonDTO save(@RequestBody PersonDTO dto) {
		return personService.addPerson(PersonAssembler.assemble(dto));
	}
}
