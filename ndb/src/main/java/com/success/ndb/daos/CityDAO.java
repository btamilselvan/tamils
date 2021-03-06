package com.success.ndb.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.success.ndb.entities.City;
@Transactional
@Repository
public interface CityDAO extends CrudRepository<City, Integer>{

	@Query("select c from city c where LOWER(c.name)=:cityName and c.district.id=:districtId")
	List<City> searchByCityNameAndDistrictId(@Param("cityName") String cityName, @Param("districtId") int districtId);
	
	@Query("select c from city c where c.district.id=:districtId")
	List<City> searchByDistrictId(@Param("districtId") int districtId);
}
