package com.videodasy.OLHS_backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Dao.DistrictDao;
import com.videodasy.OLHS_backend.Domain.District;
import com.videodasy.OLHS_backend.Service.IDistrictService;

@Service
public class DistrictService implements IDistrictService {
	
	@Autowired
	private DistrictDao districtDao;

	@Override
	public String create(District district) {
		
		return districtDao.createProvince(district);
	}

	@Override
	public String update(District district) {
		
		return districtDao.updateDistrict(district);
	}

	@Override
	public String delete(District district) {
		
		return districtDao.deleteDistrict(district);
	}

	@Override
	public District findByUuid(String uuid) {
		
		return districtDao.findByUuid(uuid);
	}

	@Override
	public List<District> all() {
		
		return districtDao.allDistricts();
	}

	@Override
	public List<District> FindDistrictByProvince(long id) {
		
		return districtDao.FindDistrictByProvince(id);
	}

}
