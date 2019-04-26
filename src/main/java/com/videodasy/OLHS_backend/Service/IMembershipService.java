package com.videodasy.OLHS_backend.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.videodasy.OLHS_backend.Domain.Membership;

public interface IMembershipService {
	
String create(final Membership membership);
	
	String update(final Membership membership);
	
	String delete(final Membership membership);
	
	

	Membership findByUuid(String uuid);

	List<Membership> all();
	
	List<Membership> FindByRegistrantId(long id);
  
	public Object upLoad(List<MultipartFile> files, String uuid);
}
