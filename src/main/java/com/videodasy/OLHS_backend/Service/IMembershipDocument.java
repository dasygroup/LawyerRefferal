package com.videodasy.OLHS_backend.Service;

import java.util.List;


import com.videodasy.OLHS_backend.Domain.MemebershipDocument;

public interface IMembershipDocument {

String create(final MemebershipDocument document);
	
	String update(final MemebershipDocument document);
	
	String delete(final MemebershipDocument document);

	MemebershipDocument findByUuid(String uuid);

	List<MemebershipDocument> all();
	
	List<MemebershipDocument> FindByMembershipId(long id);
	
	public String deleteAllByMembership(long mid);


}
