package com.videodasy.OLHS_backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Dao.AppointmentDao;
import com.videodasy.OLHS_backend.Domain.Appointment;
import com.videodasy.OLHS_backend.Service.IAppointmentService;

@Service
public class AppointmentService implements IAppointmentService  {
	
	@Autowired
	private AppointmentDao appointmentDao;

	@Override
	public String create(Appointment appointment) {
		
		return appointmentDao.createProvince(appointment);
	}

	@Override
	public String update(Appointment appointment) {
		
		return appointmentDao.updateAppointment(appointment);
	}

	@Override
	public String delete(Appointment appointment) {
		
		return appointmentDao.deleteAppointment(appointment);
	}

	@Override
	public Appointment findByUuid(String uuid) {
		
		return appointmentDao.findByUuid(uuid);
	}

	@Override
	public List<Appointment> all() {
		
		return appointmentDao.allAppointments();
	}

	@Override
	public List<Appointment> FindByRegistrantId(long id) {
		
		return appointmentDao.FindByRegistrantId(id);
	}

}
