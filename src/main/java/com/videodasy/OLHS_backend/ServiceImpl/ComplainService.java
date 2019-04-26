package com.videodasy.OLHS_backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Dao.ComplainDao;
import com.videodasy.OLHS_backend.Domain.Complain;
import com.videodasy.OLHS_backend.Service.IComplainService;
@Service
public class ComplainService implements IComplainService  {
	
	@Autowired
	private ComplainDao complainDao;

	@Override
	public String create(Complain complain) {
		// TODO Auto-generated method stub
		return complainDao.createComplain(complain);
	}

	@Override
	public String update(Complain complain) {
		// TODO Auto-generated method stub
		return complainDao.updateComplain(complain);
	}

	@Override
	public String delete(Complain complain) {
		// TODO Auto-generated method stub
		return complainDao.deleteComplain(complain);
	}

	@Override
	public Complain findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return complainDao.findByUuid(uuid);
	}

	@Override
	public List<Complain> all() {
		// TODO Auto-generated method stub
		return complainDao.allComplains();
	}

	@Override
	public List<Complain> FindByAppointmentId(long id) {
		// TODO Auto-generated method stub
		return complainDao.FindByAppointmentId(id);
	}

}
