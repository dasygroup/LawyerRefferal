package com.videodasy.OLHS_backend.Service;

import java.util.List;

import com.videodasy.OLHS_backend.Domain.Province;






public interface IProvinceService {
  

	String create(final Province province);
	
	String update(final Province province);
	
	String delete(final Province province);
	
	

	Province findByUuid(String uuid);

	List<Province> all();
}
