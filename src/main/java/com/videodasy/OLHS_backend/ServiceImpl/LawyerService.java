package com.videodasy.OLHS_backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Dao.LawyerDao;
import com.videodasy.OLHS_backend.Domain.Lawyer;
import com.videodasy.OLHS_backend.Service.ILawyerService;
@Service
public class LawyerService implements ILawyerService {

	@Autowired
	private LawyerDao lawyerDao;
	@Override
	public String create(Lawyer lawyer) {
		// TODO Auto-generated method stub
		return lawyerDao.createLawyer(lawyer);
	}

	@Override
	public String update(Lawyer lawyer) {
		// TODO Auto-generated method stub
		return lawyerDao.updateMembershipDocument(lawyer);
	}

	@Override
	public String delete(Lawyer lawyer) {
		// TODO Auto-generated method stub
		return lawyerDao.deleteLawyer(lawyer);
	}

	@Override
	public Lawyer findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return lawyerDao.findByUuid(uuid);
	}

	@Override
	public List<Lawyer> all() {
		// TODO Auto-generated method stub
		return lawyerDao.allLawyers();
	}

	@Override
	public List<Lawyer> FindByDistrictId(long id) {
		// TODO Auto-generated method stub
		return lawyerDao.FindByDistrictId(id);
	}

}
