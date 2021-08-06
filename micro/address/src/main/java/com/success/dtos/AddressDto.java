package com.success.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
	private Integer id;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private Integer postalCode;
}
