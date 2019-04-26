package com.videodasy.OLHS_backend.ServiceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Controller.RegistrantController;
import com.videodasy.OLHS_backend.Dao.RegistrantDao;

import com.videodasy.OLHS_backend.Domain.Registrant;
import com.videodasy.OLHS_backend.Domain.RegistrantCategory;
import com.videodasy.OLHS_backend.Service.IRegistrantService;
@Service
public class RegistrantService implements IRegistrantService {
	
	@Autowired
	private RegistrantDao registrantDao;
	

	@Override
	public String create(Registrant registrant) {
		// TODO Auto-generated method stub
		return registrantDao.createRegistrant(registrant);
	}

	@Override
	public String update(Registrant registrant) {
		// TODO Auto-generated method stub
		return registrantDao.updateRegistrant(registrant);
	}

	@Override
	public String delete(Registrant registrant) {
		// TODO Auto-generated method stub
		return registrantDao.deleteRegistrant(registrant);
	}

	@Override
	public Registrant findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return registrantDao.findByUuid(uuid);
	}

	@Override
	public List<Registrant> all() {
		// TODO Auto-generated method stub
		return registrantDao.allRegistrants();
	}

	@Override
	public Registrant buildRegistrant(RegistrantController.RegAdmin regAdmin){
		Registrant registrant=new Registrant();
		try {
			
			
			registrant.setDoneAt(new Timestamp(new Date().getTime()));
			registrant.setLastUpdatedAt(regAdmin.getLastUpdatedAt());
			registrant.setLastUpdatedBy(regAdmin.getLastUpdatedBy());
			registrant.setDeletedStatus(regAdmin.isDeletedStatus());
			registrant.setRegistrantCategory(checkRegistrantCategory(regAdmin.getRegistrantCategory()));
			registrant.setEmail(regAdmin.getEmail());
			registrant.setNames(regAdmin.getNames());
			registrant.setPhone(regAdmin.getPhone());
			registrant.setMartalStatus(regAdmin.getMartalStatus());
			registrant.setGender(regAdmin.getGender());
			registrant.setIdentity(regAdmin.getNationalId());
			
			
		}catch (Exception ex){
			System.out.println("RegistantService (formRegistrant()) "+ex.getMessage());
		}
		return registrant;
	}
	


	@Override
	public RegistrantCategory checkRegistrantCategory(String registrant){
		for (RegistrantCategory re : RegistrantCategory.values()) {
			if (re.name().equalsIgnoreCase(registrant))
				return re;
		}
		return null;
	}

	@Override
	public Registrant findByIdentity(String identity) {
		// TODO Auto-generated method stub
		return registrantDao.findByIdentity(identity);
	}

	@Override
	public Registrant findOne(long id) {
		// TODO Auto-generated method stub
		return registrantDao.findOne(id);
	}



	
	

}
