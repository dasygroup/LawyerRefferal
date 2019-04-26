package com.videodasy.OLHS_backend.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Domain.Lawyer;


public interface ILawyerService {
	
    String create(final Lawyer lawyer);
	
	String update(final Lawyer lawyer);
	
	String delete(final Lawyer lawyer);

	Lawyer findByUuid(String uuid);

	List<Lawyer> all();
	
	List<Lawyer> FindByDistrictId(long id);

}
