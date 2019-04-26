package com.videodasy.OLHS_backend.Service;

import java.util.List;

import com.videodasy.OLHS_backend.Domain.Complain;


public interface IComplainService {

	 String create(final Complain complain);
		
		String update(final Complain complain);
		
		String delete(final Complain complain);

		Complain findByUuid(String uuid);

		List<Complain> all();
		
		List<Complain> FindByAppointmentId(long id);
}
