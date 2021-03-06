package com.success.ndb.service;

import java.util.List;

import com.success.ndb.entities.Country;

public interface CountryService {

	List<Country> getAllCountries();
	void importCountries();
	List<Country> getAllCountries(String filter);
	Country findOne(String code);
}
