package com.videodasy.OLHS_backend.Service;

import java.util.List;

import com.videodasy.OLHS_backend.Domain.District;


public interface IDistrictService {
	
String create(final District district);
	
	String update(final District district);
	
	String delete(final District district);
	
	

	District findByUuid(String uuid);

	List<District> all();
	List<District> FindDistrictByProvince(long id);

}
