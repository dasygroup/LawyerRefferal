package com.videodasy.OLHS_backend.Service;

import java.util.List;

import com.videodasy.OLHS_backend.Controller.RegistrantController;
import com.videodasy.OLHS_backend.Domain.Gender;
import com.videodasy.OLHS_backend.Domain.Registrant;
import com.videodasy.OLHS_backend.Domain.RegistrantCategory;

public interface IRegistrantService {

String create(final Registrant registrant);
	
	String update(final Registrant registrant);
	
	String delete(final Registrant Registrant);

	Registrant findByUuid(String uuid);
	
	Registrant findOne(long id);

	List<Registrant> all();
	Registrant buildRegistrant(RegistrantController.RegAdmin regAdmin);
	
	RegistrantCategory checkRegistrantCategory(String registrant);
	
	
	Registrant findByIdentity(String identity);
	
}
