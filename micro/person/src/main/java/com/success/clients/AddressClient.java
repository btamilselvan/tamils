package com.success.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="address-service")
public interface AddressClient {
	
	@GetMapping(path="/{personId}")
	String getAddress(@PathVariable("personId") String personId);

}
