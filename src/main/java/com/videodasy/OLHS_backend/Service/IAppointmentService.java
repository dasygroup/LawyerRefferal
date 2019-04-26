package com.videodasy.OLHS_backend.Service;

import java.util.List;

import com.videodasy.OLHS_backend.Domain.Appointment;
import com.videodasy.OLHS_backend.Domain.District;

public interface IAppointmentService {
	
String create(final Appointment appointment);
	
	String update(final Appointment appointment);
	
	String delete(final Appointment appointment);
	
	

	Appointment findByUuid(String uuid);

	List<Appointment> all();
	List<Appointment> FindByRegistrantId(long id);

}
